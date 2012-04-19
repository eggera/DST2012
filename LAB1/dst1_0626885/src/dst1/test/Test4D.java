package dst1.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.interceptor.SQLInterceptor;
import dst1.model.PersistenceUtil;
import dst1.query.ComputerUsage;
import dst1.query.JPQLQueries;

public class Test4D {

	/**
	 * Evaluate if the task 2B was implemented efficiently (number of select statements)
	 */
	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 4D  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
	
		SQLInterceptor.resetSelectCount();
		
//		System.out.println("Executing efficient query of task 2b:");
		JPQLQueries jpqlQueries = new JPQLQueries(entityManager);
		jpqlQueries.getComputerUsage();
		
		SQLInterceptor.printSelectCount();
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
