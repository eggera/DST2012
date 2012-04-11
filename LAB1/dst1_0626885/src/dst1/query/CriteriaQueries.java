package dst1.query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import dst1.model.Environment;
import dst1.model.Execution;
import dst1.model.Job;
import dst1.model.User;
import dst1.model.Execution.JobStatus;


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
	public List<Job> findJobsByUsernameAndWorkflow(String username, String workflow) {
		
		boolean userSpecified = true;
		boolean workflowSpecified = true;
		
		if(username == null  ||  username.equals(""))
			userSpecified = false;
		
		if(workflow == null  ||  workflow.equals(""))
			workflowSpecified = false;
		
		String info = "Find all jobs with";
		if(userSpecified)
			info += "\nusername: "+username;
		
		if(workflowSpecified)
			info += "\nworkflow: "+workflow;
		
		System.out.println(info+"\n");
		
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
		
		if(userSpecified  &&  workflowSpecified) {
			cq.where(cb.and(
						cb.equal(
								user.get(User_.getSingularAttribute("username", String.class)), 
								username),
						cb.equal(
								environment.get(Environment_.getSingularAttribute("workflow", String.class)), 
								workflow)
						));
		} else if(userSpecified) {
			cq.where(cb.equal(
							user.get(User_.getSingularAttribute("username", String.class)), 
							username
					 ));
		} else if(workflowSpecified) {
			cq.where(cb.equal(
							environment.get(Environment_.getSingularAttribute("workflow", String.class)), 
							workflow)
					 );
		}
		
		TypedQuery<Job> q = entityManager.createQuery(cq);
		List<Job> jobList = q.getResultList();
		
		return jobList;
	}
	
	/**
	 * Find all finished jobs with a specified start and end date
	 * @param start the start date of the job
	 * @param end the end date of the job
	 * @return a list of jobs adhering to above conditions
	 */
	@SuppressWarnings("unchecked")
	public List<Job> findJobsByStatusAndDate(Date start, Date end) {
		System.out.println("\nFind jobs by status and date: ");
		System.out.println("Date1: "+(start == null ? "not specified" : start));
		System.out.println("Date2: "+(end == null ? "not specified" : end));
		System.out.println();
		
		Session session = (Session) entityManager.getDelegate();
		
//		SessionFactory sessionFactory = new Configuration()
//												.configure("/META-INF/hibernate.cfg.xml")
//												.buildSessionFactory();
//		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Job.class);
		
		Job exampleJob = new Job();
		Execution exampleExecution = new Execution();
		
		exampleExecution.setStart(start);
		exampleExecution.setEnd	 (end);
		exampleExecution.setStatus(JobStatus.FINISHED);
		
		exampleJob.setExecution(exampleExecution);
//		exampleJob.setJobId(1L);
		
		Example jobExample = Example.create(exampleJob);
		jobExample.excludeProperty("isPaid");
		
//		Example execExample = Example.create(exampleExecution);
//		execExample.excludeZeroes();
//		criteria.setMaxResults(3);
				
		List<Job> results = criteria.add(jobExample)
									.createCriteria("execution")
										.add( Example.create(exampleJob.getExecution()) )
									.list();
		
//		session.beginTransaction();
//		session.save(new Grid("newGrid","location",new BigDecimal(10)));
//		session.getTransaction().commit();
		session.close();
		
		return results;
	}
	
}
