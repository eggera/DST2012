package dst2.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst1.model.Computer;
import dst1.model.Execution;
import dst1.model.Job;
import dst1.model.User;

import dst1.model.Service;
import dst2.exception.JobAssignmentException;
import dst2.exception.LoginFailedException;
import dst2.helper.TemporaryJob;


@Stateful
public class JobManagementBean implements JobManagement {

	// state
	
	private String loggedInUser;
	private List<TemporaryJob> temporaryJobs = new ArrayList<TemporaryJob>();
	
	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public void addJobToList(Long gridId, int numCPUs, String workflow, List<String> params) throws JobAssignmentException {
		
		List<Computer> freeComputers = getFreeComputers(gridId);
		
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
		
		temporaryJobs.add(job);

	}
	
	
	@Override
	public void submitJobList() {
		
	}
	
	
	@Override
	public void removeJobsFromGrid(Long gridId) {
		
		List<TemporaryJob> deleteJobs = new ArrayList<TemporaryJob>();
		for(TemporaryJob tempJob : temporaryJobs) {
			if(tempJob.getGridId().equals(gridId))
				deleteJobs.add(tempJob);
		}
		
		temporaryJobs.removeAll(deleteJobs);
	}
	
	
	@Override
	public Map<Long,Integer> getAmountOfJobsPerGrid() {
		
		Map<Long,Integer> jobMap = new HashMap<Long,Integer>();
		
		for(TemporaryJob tempJob : temporaryJobs) {
			if(jobMap.containsKey(tempJob.getGridId()))
				jobMap.put(tempJob.getGridId(), jobMap.get(tempJob.getGridId()));
			else
				jobMap.put(tempJob.getGridId(), 1);
			
		}
		
		return jobMap;
	}
	
	
	@Override
	public void login(String username, String password) throws LoginFailedException {
		byte[] pwHash = Service.getMD5Hash(password);
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:pwHash", User.class);
		query.setParameter("username", username);
		query.setParameter("pwHash", pwHash);
		
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)query.getResultList();
		
		if(users.size() == 1) {
			loggedInUser = username;
			System.out.println("Successfully logged in");
		}
		else {
			loggedInUser = null;
			throw new LoginFailedException("User/Password combination is not valid");
		}
	}
	
	
	private List<Computer> getFreeComputers(Long gridId) {
		
		List<Computer> computerList = entityManager.createQuery(
				"SELECT distinct c FROM Computer c left join fetch c.executionList " +
				"join c.cluster cl join cl.grid g " +
							"WHERE g.gridId=:gridId", Computer.class)
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
		
		for(TemporaryJob tempJob : temporaryJobs)
			freeComputers.removeAll(tempJob.getAssignedComputers());
				
		return freeComputers;
	}
}
