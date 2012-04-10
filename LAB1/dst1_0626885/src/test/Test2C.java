package test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.Execution;
import dst1.model.Execution.JobStatus;
import dst1.model.Job;
import dst1.model.PersistenceUtil;
import dst1.model.User;
import dst1.query.CriteriaQueries;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

public class Test2C {

	/**
	 * Test code for assignment 2C
	 */
	@SuppressWarnings("unchecked")
	public static void test() {

		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2C  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		
		CriteriaQueries criteriaQueries = new CriteriaQueries(entityManager);
		List<Job> jobList = criteriaQueries.findJobs("usr1", "workflow2");
		
		for(Job j : jobList)
			System.out.println(j.toExtendedString());
				
		if(jobList.size() == 0)
			System.out.println("No results");
				
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
//		----------------------------- SECOND PART -------------------------------------
		
		
		System.out.println("\n Hibernate Queries: \n");
		
		SessionFactory sessionFactory = new Configuration()
												.configure("/META-INF/hibernate.cfg.xml")
												.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Job.class);
		
		Job exampleJob = new Job();
		Execution exampleExecution = new Execution();
		exampleExecution.setStatus(JobStatus.FINISHED);
		exampleJob.setExecution(exampleExecution);
//		exampleJob.setJobId(1L);
		
		Example example = Example.create(exampleJob);
		example.excludeProperty("isPaid");
		
		List<Job> results = criteria.add(example)
									.createCriteria("execution")
										.add( Example.create(exampleJob.getExecution()) )
									.list();
//		criteria.setMaxResults(3);
		
		System.out.println("results: "+results.size());
		
		Iterator<Job> iter = results.listIterator();
		
		while(iter.hasNext())
			System.out.println(iter.next());
		
//		
//		session.beginTransaction();
//		session.save(new Grid("newGrid","location",new BigDecimal(10)));
//		session.getTransaction().commit();
		session.close();
	}
}
