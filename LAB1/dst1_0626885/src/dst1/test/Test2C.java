package dst1.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.Job;
import dst1.model.PersistenceUtil;
import dst1.model.Service;
import dst1.query.CriteriaQueries;

public class Test2C {

	/**
	 * Test code for assignment 2C
	 */
	public static void test() {

		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2C  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		
		CriteriaQueries criteriaQueries = new CriteriaQueries(entityManager);
		List<Job> jobList = criteriaQueries.findJobsByUsernameAndWorkflow("usr1", "workflow2");
		
		System.out.println("results: "+jobList.size());
		
		for(Job j : jobList)
			System.out.println(j.toExtendedString());
				
		if(jobList.size() == 0)
			System.out.println("No results");
				
		entityManager.getTransaction().commit();
//		entityManager.close();
		
		
//		----------------------------- SECOND PART -------------------------------------
		
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 04, 10);
		
		Date date = Service.getReferenceDate();

		Date start = null;
		Date end = null;
		
		start = new Date(date.getTime() - 1000*60*60*4);
//		end = new Date(date.getTime() - 1000*60*60*3);
		
		System.out.println("\nFind jobs by status and date: ");
		System.out.println("Date1: "+(start == null ? "not specified" : start));
		System.out.println("Date2: "+(end == null ? "not specified" : end));
		System.out.println();
		
		List<Job> results = criteriaQueries.findJobsByStatusAndDate(start, end);
		
		Iterator<Job> iter = results.listIterator();
		
		System.out.println("results: "+results.size());
		
		while(iter.hasNext())
			System.out.println(iter.next().toExtendedString());		

	}
}
