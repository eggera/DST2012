package dst1.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.listener.DefaultListener;
import dst1.model.PersistenceUtil;

public class Test4C {

	public static void test() {
			
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 4C  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		System.out.println("Load-Operations		: "+DefaultListener.getLoadOperations());
		System.out.println("Update-Operations		: "+DefaultListener.getUpdateOperations());
		System.out.println("Remove-Operations		: "+DefaultListener.getRemoveOperations());
		System.out.println("\nPersist-Operations		: "+DefaultListener.getPersistOperations());
		System.out.println("Overall time to persist	: "+DefaultListener.getTotalPersistTime()+"ms");
		System.out.println("Average time to persist	: "+DefaultListener.getAveragePersistTime()+"ms");
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
}
