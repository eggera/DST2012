package dst2.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import dst1.model.Address;
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
			utx.commit();
//			System.out.println("after commit : "+ utx.getStatus());
		}
		catch (Exception e) {
			System.err.println("Exception in test method: "+e.getMessage());
		}
	}
	
	
	@Override
	public void addJobToList(Long gridId, int numCPUs, String workflow, List<String> params) throws JobAssignmentException {
		
		List<Computer> freeComputers = new ArrayList<Computer>();
		
		try {
			utx.begin();
		
		
			freeComputers = getFreeComputers(gridId);
		
			utx.commit();
		} 
		catch (Exception e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Exception in getFreeComputers: "+e.getMessage());
		}
		
		
		System.out.println("freeComputers: ");
		for(Computer computer : freeComputers) {
			System.out.println(computer);
		}
		
		// clear the list of freeComputers from already assigned computers
		
		for( Long gId : temporaryJobs.keySet() )
			for( TemporaryJob tempJob : temporaryJobs.get(gId) )
				freeComputers.removeAll(tempJob.getAssignedComputers());
		
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
		
		if( temporaryJobs.get(gridId) == null ) {
			List<TemporaryJob> jobList = new ArrayList<TemporaryJob>();
			jobList.add(job);
			temporaryJobs.put(gridId, jobList);
		}
		else {
			temporaryJobs.get(gridId).add(job);
		}

	}
	
	
	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void submitJobList() throws JobAssignmentException, NotLoggedInException {
		
		if( loggedInUser == null )
			throw new NotLoggedInException("User must be logged in when submitting a job list");
		
		try {
			
//			try {
//				System.out.println("utx status = "+utx.getStatus());
//				if( utx.getStatus() != Status.STATUS_ACTIVE )
//					utx.begin();
//			} catch (Exception e) {
//				System.err.println("Tx: Transaction exception in addJobToList: "+e.getMessage());
//			}
			
			utx.begin();
			
//			Job job = entityManager.find(Job.class, 2L);
//			entityManager.remove(job);
			
			System.out.println("transaction: "+utx.getStatus());
		
			for( Long gridId : temporaryJobs.keySet() ) {
				
				List<Computer> freeComputers = getFreeComputers(gridId);
				
				for( TemporaryJob tempJob : temporaryJobs.get(gridId) ) {
					
					if( freeComputers.containsAll(tempJob.getAssignedComputers()) ) {
						
						storeJob( tempJob );
						
					} else {
						
						System.out.println("ROLLBACK !!!!!!!!!!!!!");
						if( utx.getStatus() == Status.STATUS_ACTIVE )
							utx.rollback();
						
						throw new JobAssignmentException("The final assignment of a job to grid "+gridId+" failed " +
													"due to a lack of free computers");
					}
						
				}		
				
			}
			System.out.println("Transaction: before commit : "+utx.getStatus());
			utx.commit();
			System.out.println("Transaction: on commit : "+utx.getStatus());
			
		} catch (NotSupportedException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Tx: NotSupportedException in submitJobList: "+e.getMessage());
		} catch (SystemException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("System Exception in submitJobList: "+e.getMessage());
		} catch (SecurityException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("SecurityException in login: "+e.getMessage());
		} catch (IllegalStateException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("IllegalStateException in login: "+e.getMessage());
		} catch (RollbackException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("RollbackException in login: "+e.getMessage());
		} catch (HeuristicMixedException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("HeuristicMixedException in login: "+e.getMessage());
		} catch (HeuristicRollbackException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("HeuristicRollbackException in login: "+e.getMessage());
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
			
			utx.commit();
		} catch (NotSupportedException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("Tx: NotSupportedException in login: "+e.getMessage());
		} catch (SystemException e) {
			
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("System Exception in login: "+e.getMessage());
		} catch (SecurityException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("SecurityException in login: "+e.getMessage());
		} catch (IllegalStateException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("IllegalStateException in login: "+e.getMessage());
		} catch (RollbackException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("RollbackException in login: "+e.getMessage());
		} catch (HeuristicMixedException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("HeuristicMixedException in login: "+e.getMessage());
		} catch (HeuristicRollbackException e) {
			try {
				utx.rollback();
			} catch (Exception ex) {
				System.err.println("Tx: Rollback exception, "+ex.getMessage());
			}
			
			System.err.println("HeuristicRollbackException in login: "+e.getMessage());
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
		
//		entityManager.find(arg0, arg1, arg2, arg3)
		

		
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
		
//		User user = new User("User","UserLast", new Address("street0","city0","2222"), 
//				"usr", Service.getMD5Hash("usr"), "0000", "1111");
		
		User user = entityManager.find(User.class, loggedInUser.getId());
		
		job.setEnvironment(environment);
		job.setExecution(execution);
		job.setUser(user);
		
		user.addJob(job);
		
		System.out.println("STORING !!!!!!!!!!!!!");
		
//		System.out.println("logged in user = "+loggedInUser.toExtendedString());
		
		entityManager.persist(job);
//		entityManager.persist(execution);
	}
	
}
