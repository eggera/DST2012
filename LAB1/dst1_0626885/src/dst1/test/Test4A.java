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
		
		user.setUsername("username");
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

		System.out.println("commit Transaction");
		entityManager.getTransaction().commit();
		
		// after clearing the persistence context of this EntityManager
		// all its managed entities become detached
		System.out.println("\nclear persistence context");
		entityManager.clear();
		System.out.println("User");
		System.out.println("--> persistent state: Detached");
		System.out.println("Job");
		System.out.println("--> persistent state: Detached");
		
		// the detached job is merged and a new managed object is retrieved (jobMerged)
		// which also contains a reference to a newly managed user (eager fetch)
		System.out.println("\nMerge Job");
		Job jobMerged = entityManager.merge(job);
		
		System.out.println("Job");
		System.out.println("--> persistent state: Managed");
		System.out.println("User");
		System.out.println("--> persistent state: Managed");
		
		System.out.println("Job managed: "+entityManager.contains(jobMerged));
		System.out.println("User managed: "+entityManager.contains(jobMerged.getUser()));
		
		// the entityManager keeps track of all changes of its managed entities (transparent update)
		// on a transaction commit all managed entities are synchronized with the database
		jobMerged.setPaid(true);
		
		entityManager.getTransaction().begin();
		
//		// Performance check with many entities being persisted
//		int nrOfUsers = 1000;
//		System.out.println("\nPersisting "+nrOfUsers+" Users");
//		Address address = new Address("str","cty","zip");
//		for(int i = 0; i < nrOfUsers; i++) {
//			entityManager.persist(new User("F","L",address,"user"+i,Service.getMD5Hash("user"+i)));
//			if(i % 1000  ==  0) {
//				entityManager.getTransaction().commit();
//				entityManager.getTransaction().begin();
//			}
//		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		User usr3 = entityManager.find(User.class, 3L);
		
		System.out.println("\nFind user without active transaction");
		System.out.println("User with id 3: \n"+usr3);
		System.out.println("User managed: "+entityManager.contains(usr3));
		
		System.out.println("\nRemove User with id 3");
		entityManager.remove(usr3);
		System.out.println("User managed: "+entityManager.contains(usr3));
		
		entityManager.close();
	}
}
