package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.*;

public class Test2B {

	/**
	 * Test code for assignment 2B
	 */
	@SuppressWarnings("unchecked")
	public static void test() {
//		------------------------------  ASSIGNMENT 2B  ----------------------------------------
		
		// ---------------------------- TESTING CODE -----------------------------------
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2B  --------------\n\n");
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		entityManager.getTransaction().begin();
		
		System.out.println("Get all vienna computers");
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
