package dst1.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.Computer;
import dst1.model.PersistenceUtil;

public class Test4B {

	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 4B  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Computer computer3 = entityManager.find(Computer.class, 3L);
		Computer computer7 = entityManager.find(Computer.class, 7L);
		Computer computer10 = entityManager.find(Computer.class, 10L);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Updating computer3 ...");
		System.out.println("Updating computer7 ...");
		System.out.println("Updating computer10 ...");
		
		computer3.setName("computer003");
		computer7.setCpus(8);
		computer10.setLocation("AUT-SBG@5040");	
		
		entityManager.getTransaction().commit();
		
		System.out.println(computer3.toExtendedString());
		System.out.println(computer7.toExtendedString());
		System.out.println(computer10.toExtendedString());
		
		entityManager.close();
	}
}
