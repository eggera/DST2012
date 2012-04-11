package dst1.test;

import javax.persistence.*;

import dst1.model.*;
import dst1.model.PersistenceUtil;

public class Test4A {

	
	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 4A  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		System.out.println("Creating Job ...");
		Job job = new Job();
		System.out.println("--> persistent state: New");
		System.out.println("Creating User ...");
		User user = new User();
		System.out.println("--> persistent state: New");
		// Job and User: persistence state = New (pure Java objects)
		
		user.setUsername("username5");
		job.setUser(user);

		System.out.println("\nbegin Transaction");
		entityManager.getTransaction().begin();

		System.out.println("Persist Job");
		entityManager.persist(job);
		
		System.out.println("Job");
		System.out.println("--> persistent state: Managed");
		System.out.println("User");
		System.out.println("--> persistent state: Managed");
		
		// due to a cascade-persist in Job with respect to its related User
		// the user gets managed as soon as one of its jobs get managed

		System.out.println("\ncommit Transaction");
		entityManager.getTransaction().commit();
		
		System.out.println("clear persistence context");
		entityManager.clear();
		System.out.println("User");
		System.out.println("--> persistent state: Detached");
		System.out.println("Job");
		System.out.println("--> persistent state: Detached");
		
		System.out.println("close EntityManager");
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		
//		System.out.println("job managed: "+entityManager.contains(job));
//		System.out.println("user managed: "+entityManager.contains(user));
		
//		// user becomes managed and is assigned a unique id
//		// this is required by the job entity which needs a valid reference to the user object
//		System.out.println("--> persistent state: Managed");

		
		entityManager.close();
	}
}
