package dst1.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import dst1.model.*;

@SuppressWarnings("unchecked")
public class JPQLQueries {

	private EntityManager entityManager;
	
	public JPQLQueries(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets the entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the only EntityManager for this object
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
//	==========================  ASSIGNMENT 2A  =====================================
	
	/**
	 * Gets all users with a membership assigned to a given grid and with a minimum
	 * of minJobs jobs assigned to this grid
	 * @param gridName the gridName of the grid
	 * @param minJobs minimum number of jobs by a user to the given grid
	 * @return a list of users adhering to the above conditions
	 */
	public List<User> getActiveUsersWithMinJobs(String gridName, Long minJobs) {
		return entityManager.createNamedQuery("activeUsersWithXJobs")
				  .setParameter("gname", gridName)
				  .setParameter("minJobs", minJobs)
				  .getResultList();
	}
	
	/**
	 * Gets the users who assigned the biggest number of jobs
	 * @return a user list of users having assigned the greatest nr of jobs
	 */
	public List<User> getMostActiveUsers() {
		return entityManager.createNamedQuery("mostActiveUsers")
							.getResultList();
	}
	
//	==========================  ASSIGNMENT 2B  =====================================
	
	/**
	 * Get the total usage for each computer in a list
	 * @return a list of a wrapper class for a computer and its usage
	 */
	public List<ComputerUsage> getComputerUsage() {
		List<Computer> computers = entityManager.createNamedQuery("getViennaComputers").getResultList();
		List<Long> executionTimes = new ArrayList<Long>();
		System.out.println("computers : "+ computers.size());
		List<ComputerUsage> computerUsageList = new ArrayList<ComputerUsage>();
		
		for(Computer computer : computers) {
			Long usage = 0L;
			for(Execution execution : computer.getExecutionList()) {
				usage += execution.getEnd().getTime() - execution.getStart().getTime();
			}
			executionTimes.add(usage);
			computerUsageList.add(new ComputerUsage(computer, usage));
		}
		
		return computerUsageList;
	}
	
}
