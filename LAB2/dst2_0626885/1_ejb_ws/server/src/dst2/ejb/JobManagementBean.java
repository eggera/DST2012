package dst2.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import dst1.model.Computer;
import dst1.model.Environment;
import dst1.model.Execution;
import dst1.model.Job;
import dst1.model.User;

import dst1.service.Service;
import dst2.ejb.exception.JobAssignmentException;
import dst2.ejb.exception.LoginFailedException;
import dst2.ejb.exception.NotLoggedInException;
import dst2.ejb.helper.TemporaryJob;


@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class JobManagementBean implements JobManagement {

	// state
	
	private User loggedInUser;
	private Map<Long, List<TemporaryJob>> temporaryJobs = new HashMap<Long,List<TemporaryJob>>();
	
	
	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction utx;
	
	
	@Override
	public void test() {
		
		try {
			if( ! (utx.getStatus() == Status.STATUS_ACTIVE) )
				utx.begin();
			
			Job job = entityManager.find(Job.class, 2L);
			System.out.println("Job = "+job);
			
			List<Computer> freeComp = getFreeComputers(6L);
			System.out.println("free computers size = "+freeComp.size());
			
			
			List<String> params = new ArrayList<String>();
			params.add("p1");
			params.add("p2");
			System.out.println("storing job ... ");
			storeJob(new TemporaryJob(6L, 6, "workflow__", params));//
			
//			System.out.println("Creating new user ... ");
//			User user3 = new User("User3","User3Last", new Address("street3","city3","3000"), 
//					"usr3", Service.getMD5Hash("usr3"), "3456", "3000");
//			entityManager.persist(user3);
			
//			System.out.println("before commit : "+utx.getStatus());
			if( utx.getStatus() == Status.STATUS_ACTIVE )
				utx.commit();
//			System.out.println("after commit : "+ utx.getStatus());
		}
		catch (Exception e) {
			System.err.println("Exception in test method: "+e.getMessage());
		}
	}
	
	
	@Override
	public void addJobToList(Long gridId, int numCPUs, String workflow, List<String> params) throws JobAssignmentException {
		
		try {
			
			// begin Transaction
			
			if( ! (utx.getStatus() == Status.STATUS_ACTIVE) )
				utx.begin();
			
		
			List<Computer> freeComputers = getFreeComputers(gridId);
			
			System.out.println("freeComputers: ");
			for(Computer computer : freeComputers) {
				System.out.println(computer);
			}
			
			// clear the list of freeComputers from already assigned computers
			
			for( Long gId : temporaryJobs.keySet() )
				for( TemporaryJob tempJob : temporaryJobs.get(gId) )
//					freeComputers.removeAll(tempJob.getAssignedComputers());
					intersectComputerLists(freeComputers, tempJob.getAssignedComputers());
			
			// check if this job can be scheduled right now (there are enough free computers/CPUs)
			
			int freeCPUs = 0;
			for(Computer computer : freeComputers) {
				freeCPUs += computer.getCpus();
			}
			
			if(freeCPUs < numCPUs) {
				throw new JobAssignmentException("The assignment of a job to grid "+gridId+" failed " +
														"due to a lack of free computers");
			}
			
			TemporaryJob job = new TemporaryJob(gridId, numCPUs, workflow, params);
			
			int countCPUs = 0;
			// assign computers until the condition is satisfied
			for(Computer computer : freeComputers) {
				job.addAssignedComputer(computer);
				
				countCPUs += computer.getCpus();
				if(countCPUs >= numCPUs) {
					break;
				}
				
			}
			
			if( ! temporaryJobs.containsKey(gridId) ) {
				List<TemporaryJob> jobList = new ArrayList<TemporaryJob>();
				jobList.add(job);
				temporaryJobs.put(gridId, jobList);
			}
			else {
				temporaryJobs.get(gridId).add(job);
			}
		
			// commit Transaction
			
			utx.commit();
		} 
		catch (Exception e) {
			
			if(JobAssignmentException.class.isInstance(e)) {
				throw new JobAssignmentException(e.getMessage());
			}
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Exception in getFreeComputers: "+e.getMessage());
		}

	}
	
	
	@Override
	@Remove(retainIfException=true)
	public void submitJobList() throws JobAssignmentException, NotLoggedInException {
		
		if( loggedInUser == null )
			throw new NotLoggedInException("User must be logged in when submitting a job list");
		
		try {
			
			// begin Transaction
			if( ! (utx.getStatus() == Status.STATUS_ACTIVE) )
				utx.begin();

			for( Long gId : temporaryJobs.keySet() )
				System.out.println(" all temp grid Ids : "+gId);
		
			for( Long gridId : temporaryJobs.keySet() ) {
				
				List<Computer> freeComputers = getFreeComputers(gridId);
				
				System.out.println("free vs. assigned computers: ");
				for(Computer computer : freeComputers) 
					System.out.println(computer);
						
				System.out.println("assigned computers: ");
				for(TemporaryJob tempJob : temporaryJobs.get(gridId)) {
					for(Computer computer : tempJob.getAssignedComputers())
						System.out.println(computer);
				}
				
				for( TemporaryJob tempJob : temporaryJobs.get(gridId) ) {
					
					for( Computer computer : tempJob.getAssignedComputers() ) {
						entityManager.find(Computer.class, computer.getComputerId(), LockModeType.PESSIMISTIC_WRITE);
					}
						
					
					if( enoughFreeComputers(freeComputers, tempJob.getAssignedComputers()) ) {
						
						storeJob( tempJob );
						
					} else {
						
						if( utx.getStatus() == Status.STATUS_ACTIVE )
							utx.rollback();
						
						throw new JobAssignmentException("The final assignment of a job to grid "+gridId+" failed " +
													"due to a lack of free computers");
					}
						
				}		
				
			}
			if( utx.getStatus() == Status.STATUS_ACTIVE )
				utx.commit();
			
		} catch (NotSupportedException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Tx: NotSupportedException in submitJobList: "+e.getMessage());
		} 
		catch (SystemException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("System Exception in submitJobList: "+e.getMessage());
		} 
		catch (Exception e) {
			if( JobAssignmentException.class.isInstance(e) )
				throw new JobAssignmentException(e.getMessage());
			if( NotLoggedInException.class.isInstance(e) )
				throw new NotLoggedInException(e.getMessage());
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Exception in login: "+e.getMessage());
		}
		
	}
	
	
	@Override
	public void removeJobsFromGrid(Long gridId) {
		
		temporaryJobs.get(gridId).clear();
	}
	
	
	@Override
	public Map<Long,Integer> getAmountOfJobsPerGrid() {
		
		Map<Long,Integer> jobMap = new HashMap<Long,Integer>();
		
		for( Long gridId : temporaryJobs.keySet() ) {
			
			jobMap.put( gridId, temporaryJobs.get(gridId).size() );
		}
		
		return jobMap;
	}
	
	
	@Override
	public void login(String username, String password) throws LoginFailedException {
		byte[] pwHash = Service.getMD5Hash(password);
		
		try {
			if( ! (utx.getStatus() == Status.STATUS_ACTIVE) )
				utx.begin();
			
			Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:pwHash", User.class);
			query.setParameter("username", username);
			query.setParameter("pwHash", pwHash);
			
			@SuppressWarnings("unchecked")
			List<User> users = (List<User>)query.getResultList();
			
			if(users.size() == 1) {
				loggedInUser = users.get(0);
				System.out.println("Successfully logged in");
			}
			else {
				loggedInUser = null;
				throw new LoginFailedException("User/Password combination is not valid");
			}
			
			if( utx.getStatus() == Status.STATUS_ACTIVE )
				utx.commit();
			
		} catch (NotSupportedException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Tx: NotSupportedException in login: "+e.getMessage());
		} 
		catch (SystemException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("System Exception in login: "+e.getMessage());
		} 
		catch (Exception e) {
			if( LoginFailedException.class.isInstance(e) )
				throw new LoginFailedException(e.getMessage());
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Exception in login: "+e.getMessage());
		}
	}
	
	
	/**
	 * Gets all computers that currently have no scheduled/running executions (execution end field is null)
	 * @param gridId the gridId of the grid you want to query the free computers
	 * @return a list of free computers in a given grid
	 */
	private List<Computer> getFreeComputers(Long gridId) {
		

		List<Computer> computerList = entityManager.createQuery(
				"SELECT distinct c FROM Computer c left join fetch c.executionList " +
				"join c.cluster cl join cl.grid g " +
							"WHERE g.gridId=:gridId " +
							"ORDER BY c.computerId", Computer.class)
						.setParameter("gridId", gridId)
						.getResultList();

		List<Computer> freeComputers = new ArrayList<Computer>();
		
		for(Computer computer : computerList) {
		
			boolean free = true;
			for(Execution execution : computer.getExecutionList()) {
				if(execution.getEnd() == null)
					free = false;
			}
			if(free) 
				freeComputers.add(computer);
			
		}
		
		return freeComputers;
	}
	
	
	/**
	 * Stores a job from the temporary job list to the database
	 * @param tJob the temporary job to store
	 */
	private void storeJob(TemporaryJob tJob) {
		
		Job job = new Job();
		
		Date date = Service.getReferenceDate();
		
		Environment environment = new Environment( tJob.getWorkflow(), tJob.getParams() );
		Execution execution = new Execution( new Date(date.getTime()), null, Execution.JobStatus.SCHEDULED );
		
		for( Computer c : tJob.getAssignedComputers() ) {
			
			Computer computer = entityManager.find(Computer.class, c.getComputerId());
			execution.addComputer(computer);
			computer.addExecution(execution);
		}
			
		
		User user = entityManager.find(User.class, loggedInUser.getId());
		
		job.setEnvironment(environment);
		job.setExecution(execution);
		job.setUser(user);
		
		user.addJob(job);
		
		entityManager.persist(job);
	}
	
	
	// helper function
	// compares free and assigned computers, if all assigned Computers are still free
	
	private boolean enoughFreeComputers(List<Computer> freeComputers, List<Computer> assignedComputers) {
		
		for(Computer assigned : assignedComputers) {
			
			boolean isFree = false;
			for(Computer free : freeComputers) 
				if(assigned.getComputerId().equals(free.getComputerId())) {
					isFree = true;
					break;
				}
				
			if(!isFree)
				return false;
		}
		
		return true;
	}
	
	// helper function
	// removes all elements from freeComputers that are identical to assignedComputers
	
	private void intersectComputerLists(List<Computer> freeComputers, List<Computer> assignedComputers) {
		
		List<Computer> deletes = new ArrayList<Computer>();
		
		for(Computer assigned : assignedComputers) {
			
			for(Computer free : freeComputers) 
				if(assigned.getComputerId().equals(free.getComputerId())) {
					deletes.add(free);
					break;
				}
				
		}
		
		freeComputers.removeAll(deletes);
	}
	
}
