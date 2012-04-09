package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.*;
import dst1.query.ComputerUsage;
import dst1.query.JPQLQueries;

public class Test2B {

	/**
	 * Test code for assignment 2B
	 */
	public static void test() {

		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2B  --------------\n\n");
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		entityManager.getTransaction().begin();
		
		System.out.println("Get all computers in vienna and their execution times:");
		JPQLQueries jpqlQueries = new JPQLQueries(entityManager);
		
		List<ComputerUsage> computerUsageList = jpqlQueries.getComputerUsage();
		
		for(ComputerUsage computerUsage : computerUsageList) {
			System.out.println(computerUsage.getComputer());
			Long totalUsage = computerUsage.getTotalUsage() / 1000;
//			DateFormat df = new SimpleDateFormat("H:mm:ss");
//			df.setTimeZone(df.getTimeZone().getDefault());
//			String totalUsage = df.format(new Date(1000000));
			System.out.println(" --> total Usage = "+totalUsage+" seconds");
//							   TimeUnit.MILLISECONDS.toHours(millis)+" hours, " +
//							   TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.+" minutes, " +
//							   TimeUnit.MILLISECONDS.toSeconds(millis)+" seconds");
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
