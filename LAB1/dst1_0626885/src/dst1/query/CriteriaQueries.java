package dst1.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import dst1.model.Environment;
import dst1.model.Job;
import dst1.model.User;

public class CriteriaQueries {

	private EntityManager entityManager;

	public CriteriaQueries(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets the entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the only EntityManager for this class
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
//  ========================  ASSIGNMENT 2C  ============================
	
	/**
	 * Find all jobs related to a given username and workflow
	 * @param username the username of the job
	 * @param workflow the workflow of the job
	 * @return a list of users adhering the above conditions
	 */
	public List<Job> findJobs(String username, String workflow) {
		
		System.out.println("Find jobs created by user: "+username+" with the workflow: "+workflow);
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		Metamodel m = entityManager.getMetamodel();
		EntityType<Job> Job_ = m.entity(Job.class);
		EntityType<User> User_ = m.entity(User.class);
		EntityType<Environment> Environment_ = m.entity(Environment.class);
	
		CriteriaQuery<Job> cq = cb.createQuery(Job.class);
		Root<Job> job = cq.from(Job.class);
		
		Join<Job, User> user = job.join(Job_.getSingularAttribute("user", User.class));
		Join<Job, Environment> environment = job.join(Job_.getSingularAttribute("environment", Environment.class));
		
	//	Join<Environment, String> envList = environment.join(Environment_.getList("params", String.class));
	//	cq.multiselect(job.get(Job_.getSingularAttribute("jobId",Long.class)));

		
		cq.select(job);
		cq.where(cb.and(
					cb.equal(
							user.get(User_.getSingularAttribute("username", String.class)), 
							username),
					cb.equal(
							environment.get(Environment_.getSingularAttribute("workflow", String.class)), 
							workflow)
					));
		
		TypedQuery<Job> q = entityManager.createQuery(cq);
		List<Job> jobList = q.getResultList();
		
		return jobList;
	}
	
}
