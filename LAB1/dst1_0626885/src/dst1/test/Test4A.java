package dst1.test;

import java.util.ArrayList;
import java.util.List;

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
		
		List<String> params = new ArrayList<String>();
		params.add("param1");
		params.add("param2");
		
		Environment environment = new Environment("workflow", params);
		job.setEnvironment(environment);
		
		Execution execution = new Execution();
		job.setExecution(execution);
		
		user.setUsername("username");
		job.setUser(user);

		entityManager.getTransaction().begin();
		
		System.out.println("\nPersist Job");
		entityManager.persist(job);
		
		System.out.println("Job");
		System.out.println("--> persistent state: Managed");
		System.out.println("User");
		System.out.println("--> persistent state: Managed");
		
		// due to a cascade-persist in Job with respect to its related User
		// the user gets managed as soon as one of its jobs get managed

		entityManager.getTransaction().commit();
		
		// after clearing the persistence context of this EntityManager
		// all its managed entities become detached
		System.out.println("\nclear persistence context");
		entityManager.clear();
		System.out.println("Job");
		System.out.println("--> persistent state: Detached");
		System.out.println("User");
		System.out.println("--> persistent state: Detached");
		
		// the detached job is merged and a new managed object is retrieved (jobMerged)
		// which also contains a reference to a newly managed user (eager fetch)
		System.out.println("\nMerge Job");
		Job jobMerged = entityManager.merge(job);
		
		System.out.println("Job");
		System.out.println("--> persistent state: Managed");
		System.out.println("User");
		System.out.println("--> persistent state: Managed");
		
//		System.out.println("Job managed: "+entityManager.contains(jobMerged));
//		System.out.println("User managed: "+entityManager.contains(jobMerged.getUser()));
		
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
		Job job3 = entityManager.find(Job.class, 3L);
		
//		System.out.println("\nFind job without active transaction");
//		System.out.println("Job with id 3: \n"+job3);
//		System.out.println("Job managed: "+entityManager.contains(job3));
		
		System.out.println("\nRemove Job");
		entityManager.remove(job3);
		
		System.out.println("Job");
		System.out.println("--> persistent state: Removed");
		System.out.println("User");
		System.out.println("--> persistent state: Removed");
		
		entityManager.close();
	}
}
