package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.*;
import dst1.query.JPQLQueries;

public class Test2A {

	
	public static void insertEntities() {
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		UserDAO 		userDAO 		= new UserDAO		(entityManager);
		GridDAO 		gridDAO 		= new GridDAO		(entityManager);
		MembershipDAO 	membershipDAO 	= new MembershipDAO	(entityManager);
		AdminDAO 		adminDAO 		= new AdminDAO		(entityManager);
//		PersonDAO		personDAO		= new PersonDAO		(entityManager);
		ClusterDAO 		clusterDAO 		= new ClusterDAO	(entityManager);
		ComputerDAO 	computerDAO 	= new ComputerDAO	(entityManager);
//		JobDAO 			jobDAO 			= new JobDAO		(entityManager);
//		ExecutionDAO 	executionDAO 	= new ExecutionDAO	(entityManager);
//		EnvironmentDAO 	environmentDAO 	= new EnvironmentDAO(entityManager);
		
		entityManager.getTransaction().begin();
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2A  --------------\n\n");
		System.out.println("Creating users ... ");
		
		User user1 = new User("User1","User1Last", new Address("street1","city1","1000"), 
								"usr1", Service.getMD5Hash("usr1"), "1234", "1000");
		
		User user2 = new User("User2","User2Last", new Address("street2","city2","2000"), 
								"usr2", Service.getMD5Hash("usr2"), "2345", "2000");
		
		User user3 = new User("User3","User3Last", new Address("street3","city3","3000"), 
								"usr3", Service.getMD5Hash("usr3"), "3456", "3000");

		User user4 = new User("User4","User4Last", new Address("street4","city4","4000"), 
								"usr4", Service.getMD5Hash("usr4"), "4567", "4000");
		
		System.out.println("Creating jobs ... ");
		
		Job job1 = new Job();
		Job job2 = new Job();
		Job job3 = new Job();
		
		Job job4 = new Job();
		Job job5 = new Job();
		Job job6 = new Job();
		
		System.out.println("Creating environments ... ");
		
		List<String> params1 = new ArrayList<String>();
		params1.add("param1");
		params1.add("param2");
		params1.add("param3");
		
		List<String> params2 = new ArrayList<String>();
		params2.add("param4");
		
		List<String> params3 = new ArrayList<String>();
		params3.add("param5");
		params3.add("param6");
		
		Environment environment1 = new Environment("workflow1", params1);
		Environment environment2 = new Environment("workflow2", params1);
		Environment environment3 = new Environment("workflow3", params1);
		Environment environment4 = new Environment("workflow4", params2);
		Environment environment5 = new Environment("workflow5", params3);
		Environment environment6 = new Environment("workflow6", params3);
		
		job1.setEnvironment(environment1);
		job2.setEnvironment(environment2);
		job3.setEnvironment(environment3);
		
		job4.setEnvironment(environment4);
		job5.setEnvironment(environment5);
		job6.setEnvironment(environment6);

		
		user1.addJob(job1);
		user1.addJob(job2);
		user2.addJob(job3);
		
		user3.addJob(job4);
		user4.addJob(job5);
		user4.addJob(job6);
			
		System.out.println("Creating executions ... ");
		
		Execution execution1 = new Execution(
						new Date(System.currentTimeMillis()),
						new Date(System.currentTimeMillis() + 1000*60*60),
						Execution.JobStatus.RUNNING);
		
		Execution execution2 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60),
						new Date(System.currentTimeMillis() + 1000*60*60*2),
						Execution.JobStatus.SCHEDULED);
		
		Execution execution3 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*2),
						new Date(System.currentTimeMillis() + 1000*60*60*3),
						Execution.JobStatus.SCHEDULED);
		
		Execution execution4 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*3),
						new Date(System.currentTimeMillis() + 1000*60*60*4),
						Execution.JobStatus.SCHEDULED);
		
		Execution execution5 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*4),
						new Date(System.currentTimeMillis() + 1000*60*60*5),
						Execution.JobStatus.SCHEDULED);
		
		Execution execution6 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*5),
						new Date(System.currentTimeMillis() + 1000*60*60*6),
						Execution.JobStatus.SCHEDULED);
		
		job1.setExecution(execution1);
		job2.setExecution(execution2);
		job3.setExecution(execution3);
		job4.setExecution(execution4);
		job5.setExecution(execution5);
		job6.setExecution(execution6);
		
		System.out.println("Saving   users ... ");
		
		userDAO.saveUser(user1);
		userDAO.saveUser(user2);
		userDAO.saveUser(user3);
		userDAO.saveUser(user4);
		
		System.out.println("Creating grids ... ");
		
		Grid grid1 = new Grid("grid1", "G1", new BigDecimal(0.11));
		Grid grid2 = new Grid("grid2", "G2", new BigDecimal(0.22));
		Grid grid3 = new Grid("grid3", "G3", new BigDecimal(0.33));
		
		System.out.println("Saving   grids ... ");
		
		gridDAO.saveGrid(grid1);
		gridDAO.saveGrid(grid2);
		gridDAO.saveGrid(grid3);
		
		System.out.println("Creating memberships ... ");

		Membership membership1 = new Membership(grid1, user1, 
										new Date(System.currentTimeMillis()), 
										new Double(1.1));
		
		Membership membership2 = new Membership(grid1, user4, 
										new Date(System.currentTimeMillis()), 
										new Double(2.2));
		
		Membership membership3 = new Membership(grid2, user2, 
										new Date(System.currentTimeMillis()), 
										new Double(3.3));
		
		Membership membership4 = new Membership(grid2, user3, 
										new Date(System.currentTimeMillis()), 
										new Double(4.4));
		
		Membership membership5 = new Membership(grid3, user2, 
										new Date(System.currentTimeMillis()), 
										new Double(5.5));
		
		Membership membership6 = new Membership(grid3, user4, 
										new Date(System.currentTimeMillis()), 
										new Double(6.6));
		
		System.out.println("Saving   memberships ... ");
		
		membershipDAO.saveMembership(membership1);
		membershipDAO.saveMembership(membership2);
		membershipDAO.saveMembership(membership3);
		membershipDAO.saveMembership(membership4);
		membershipDAO.saveMembership(membership5);
		membershipDAO.saveMembership(membership6);
		
		System.out.println("Creating admins ... ");
		
		Admin admin1 = new Admin("Admin1", "Admin1Last", new Address("street11","city11","1111"));
		Admin admin2 = new Admin("Admin2", "Admin2Last", new Address("street22","city22","2222"));
		Admin admin3 = new Admin("Admin3", "Admin3Last", new Address("street33","city33","3333"));
		Admin admin4 = new Admin("Admin4", "Admin4Last", new Address("street44","city44","4444"));
		
		System.out.println("Saving   admins ... ");
		
		adminDAO.saveAdmin(admin1);
		adminDAO.saveAdmin(admin2);
		adminDAO.saveAdmin(admin3);
		adminDAO.saveAdmin(admin4);
		
		System.out.println("Creating clusters ... ");
		
		Cluster cluster1 = new Cluster("cluster1", 
										new Date(System.currentTimeMillis() - 1000*60*60), 
										new Date(System.currentTimeMillis() + 1000*60*60));
		
		Cluster cluster2 = new Cluster("cluster2", 
										new Date(System.currentTimeMillis() - 1000*60*60*2), 
										new Date(System.currentTimeMillis() + 1000*60*60*2));
		
		Cluster cluster3 = new Cluster("cluster3", 
										new Date(System.currentTimeMillis() - 1000*60*60*3), 
										new Date(System.currentTimeMillis() + 1000*60*60*3));

		Cluster cluster4 = new Cluster("cluster4", 
										new Date(System.currentTimeMillis() - 1000*60*60*4), 
										new Date(System.currentTimeMillis() + 1000*60*60*4));
		
		Cluster cluster5 = new Cluster("cluster5", 
										new Date(System.currentTimeMillis() - 1000*60*60*5), 
										new Date(System.currentTimeMillis() + 1000*60*60*5));
		
		Cluster cluster6 = new Cluster("cluster6", 
										new Date(System.currentTimeMillis() - 1000*60*60*6), 
										new Date(System.currentTimeMillis() + 1000*60*60*6));
		
		Cluster cluster7 = new Cluster("cluster7", 
										new Date(System.currentTimeMillis() - 1000*60*60*7), 
										new Date(System.currentTimeMillis() + 1000*60*60*7));
		
		Cluster cluster8 = new Cluster("cluster8", 
										new Date(System.currentTimeMillis() - 1000*60*60*8), 
										new Date(System.currentTimeMillis() + 1000*60*60*8));
		
		
		admin1.addCluster(cluster1);
		admin1.addCluster(cluster2);
		admin1.addCluster(cluster3);
		admin2.addCluster(cluster4);
		admin2.addCluster(cluster5);
		admin4.addCluster(cluster6);
		admin4.addCluster(cluster7);
		admin4.addCluster(cluster8);
	
		
		grid1.addCluster(cluster1);
		grid1.addCluster(cluster2);
		grid1.addCluster(cluster3);
		grid2.addCluster(cluster4);
		grid2.addCluster(cluster5);
		grid3.addCluster(cluster6);
		grid3.addCluster(cluster7);
		grid3.addCluster(cluster8);
		
		//  grid    1 --> cluster 1
		//  cluster 1 --> cluster 2
		//  cluster 1 --> cluster 3
		//  grid    2 --> cluster 4
		//  grid    2 --> cluster 5
		//  grid    3 --> cluster 6
		//  grid    3 --> cluster 7
		//  cluster 6 --> cluster 8
		//  cluster 7 --> cluster 8
		
		
		cluster1.addSubCluster(cluster2);
		cluster1.addSubCluster(cluster3);
		cluster2.addSuperCluster(cluster1);
		cluster3.addSuperCluster(cluster1);
		
		cluster6.addSubCluster(cluster8);
		cluster7.addSubCluster(cluster8);
		cluster8.addSuperCluster(cluster6);
		cluster8.addSuperCluster(cluster7);

		
		System.out.println("Saving   clusters ... ");
		
		clusterDAO.saveCluster(cluster1);
		clusterDAO.saveCluster(cluster2);
		clusterDAO.saveCluster(cluster3);
		clusterDAO.saveCluster(cluster4);
		clusterDAO.saveCluster(cluster5);
		clusterDAO.saveCluster(cluster6);
		clusterDAO.saveCluster(cluster7);
		clusterDAO.saveCluster(cluster8);
	
		System.out.println("Creating computers ... ");
		
		Computer computer1 = new Computer("Computer1", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer2 = new Computer("Computer2", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*2),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer3 = new Computer("Computer3", 2, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*3),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer4 = new Computer("Computer4", 2, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*4),
			  				new Date(System.currentTimeMillis()));
		
		Computer computer5 = new Computer("Computer5", 4, "G1C3", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*5),
			  				new Date(System.currentTimeMillis()));
	
		Computer computer6 = new Computer("Computer6", 4, "G1C3", 
							new Date(System.currentTimeMillis() - 1000*60*60*6),
							new Date(System.currentTimeMillis()));
			
		Computer computer7 = new Computer("Computer7", 2, "G2C4", 
							new Date(System.currentTimeMillis() - 1000*60*60*7),
							new Date(System.currentTimeMillis()));
			
		Computer computer8 = new Computer("Computer8", 2, "G2C4", 
							new Date(System.currentTimeMillis() - 1000*60*60*8),
							new Date(System.currentTimeMillis()));
		
		Computer computer9 = new Computer("Computer9", 1, "G2C4", 
							new Date(System.currentTimeMillis() - 1000*60*60*9),
							new Date(System.currentTimeMillis()));
		
		Computer computer10 = new Computer("Computer10", 1, "G2C4", 
							new Date(System.currentTimeMillis() - 1000*60*60*10),
							new Date(System.currentTimeMillis()));
		
		Computer computer11 = new Computer("Computer11", 2, "G2C5", 
							new Date(System.currentTimeMillis() - 1000*60*60*11),
							new Date(System.currentTimeMillis()));
		
		Computer computer12 = new Computer("Computer12", 2, "G2C5", 
							new Date(System.currentTimeMillis() - 1000*60*60*12),
							new Date(System.currentTimeMillis()));
		
		Computer computer13 = new Computer("Computer13", 4, "G3C8", 
							new Date(System.currentTimeMillis() - 1000*60*60*13),
							new Date(System.currentTimeMillis()));
		
		Computer computer14 = new Computer("Computer14", 4, "G3C8", 
							new Date(System.currentTimeMillis() - 1000*60*60*14),
							new Date(System.currentTimeMillis()));
					
		Computer computer15 = new Computer("Computer15", 2, "G3C8", 
							new Date(System.currentTimeMillis() - 1000*60*60*15),
							new Date(System.currentTimeMillis()));
		
		Computer computer16 = new Computer("Computer16", 2, "G3C8", 
							new Date(System.currentTimeMillis() - 1000*60*60*16),
							new Date(System.currentTimeMillis()));
				
		
		System.out.println("Saving   computers ... ");
		
		computerDAO.saveComputer(computer1);
		computerDAO.saveComputer(computer2);
		computerDAO.saveComputer(computer3);
		computerDAO.saveComputer(computer4);
		computerDAO.saveComputer(computer5);
		computerDAO.saveComputer(computer6);
		computerDAO.saveComputer(computer7);
		computerDAO.saveComputer(computer8);
		computerDAO.saveComputer(computer9);
		computerDAO.saveComputer(computer10);
		computerDAO.saveComputer(computer11);
		computerDAO.saveComputer(computer12);
		computerDAO.saveComputer(computer13);
		computerDAO.saveComputer(computer14);
		computerDAO.saveComputer(computer15);
		computerDAO.saveComputer(computer16);
		
		cluster2.addComputer(computer1);
		cluster2.addComputer(computer2);
		cluster2.addComputer(computer3);
		cluster2.addComputer(computer4);
		cluster3.addComputer(computer5);
		cluster3.addComputer(computer6);
		cluster4.addComputer(computer7);
		cluster4.addComputer(computer8);
		cluster4.addComputer(computer9);
		cluster4.addComputer(computer10);
		cluster5.addComputer(computer11);
		cluster5.addComputer(computer12);
		cluster8.addComputer(computer13);
		cluster8.addComputer(computer14);
		cluster8.addComputer(computer15);
		cluster8.addComputer(computer16);
		
		
//	------------  COMPUTER AND EXECUTION  -------------
		
		// job 1 -> 6 cpus		Computer 1,2,3,4		(G1C2)		cpus 1,1,2,2
		// job 2 -> 8 cpus		Computer 5,6			(G1C3)		cpus 4,4
		// job 3 -> 4 cpus		Computer 11,12			(G2C5)		cpus 2,2
		// job 4 -> 4 cpus		Computer 7,8			(G2C4)		cpus 2,2
		// job 5 -> 10 cpus		Computer 1,2,5,6		(G1C1)		cpus 1,1,4,4
		// job 6 -> 12 cpus		Computer 13,14,15,16	(G3C8)		cpus 4,4,2,2
		
		execution1.addComputer(computer1);
		execution1.addComputer(computer2);
		execution1.addComputer(computer3);
		execution1.addComputer(computer4);
		
		execution2.addComputer(computer5);
		execution2.addComputer(computer6);
		
		execution3.addComputer(computer11);
		execution3.addComputer(computer12);
		
		execution4.addComputer(computer7);
		execution4.addComputer(computer8);
		
		execution5.addComputer(computer1);
		execution5.addComputer(computer2);
		execution5.addComputer(computer5);
		execution5.addComputer(computer6);
		
		execution6.addComputer(computer13);
		execution6.addComputer(computer14);
		execution6.addComputer(computer15);
		execution6.addComputer(computer16);
		
		
		computer1.addExecution(execution1);
		computer2.addExecution(execution1);
		computer3.addExecution(execution1);
		computer4.addExecution(execution1);
		
		computer5.addExecution(execution2);
		computer6.addExecution(execution2);
		
		computer11.addExecution(execution3);
		computer12.addExecution(execution3);
		
		computer7.addExecution(execution4);
		computer8.addExecution(execution4);
		
		computer1.addExecution(execution5);
		computer2.addExecution(execution5);
		computer5.addExecution(execution5);
		computer6.addExecution(execution5);
		
		computer13.addExecution(execution6);
		computer14.addExecution(execution6);
		computer15.addExecution(execution6);
		computer16.addExecution(execution6);
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void test() {
//		------------------------------  ASSIGNMENT 2A  ----------------------------------------
		
		// ---------------------------- TESTING CODE -----------------------------------
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		UserDAO 		userDAO 		= new UserDAO		(entityManager);
		GridDAO 		gridDAO 		= new GridDAO		(entityManager);
		MembershipDAO 	membershipDAO 	= new MembershipDAO	(entityManager);
		AdminDAO 		adminDAO 		= new AdminDAO		(entityManager);
		PersonDAO		personDAO		= new PersonDAO		(entityManager);
		ClusterDAO 		clusterDAO 		= new ClusterDAO	(entityManager);
		ComputerDAO 	computerDAO 	= new ComputerDAO	(entityManager);
		JobDAO 			jobDAO 			= new JobDAO		(entityManager);
		ExecutionDAO 	executionDAO 	= new ExecutionDAO	(entityManager);
//		EnvironmentDAO 	environmentDAO 	= new EnvironmentDAO(entityManager);
		
		
		entityManager.getTransaction().begin();
		
//		List<Execution> allExecutions = entityManager.createNamedQuery("findExecutions").getResultList();
//		for(Execution execution : allExecutions) 
//			System.out.println(execution);
//		
//		List<User> allUsers = entityManager.createNamedQuery("findAllUsers").getResultList();
//		for(User user : allUsers)
//			System.out.println(user);
				
		System.out.println("\n ACTUAL TEST \n");
				
		JPQLQueries jpqlQueries = new JPQLQueries(entityManager);
				
//		List<User> usersAndGrids = entityManager.createNamedQuery("findActiveUsersForGrid")
//												.setParameter("gname", "grid1")
//												.getResultList();
//		for(User user : usersAndGrids)
//			System.out.println(user);
				
		System.out.println("Users with membership to grid1 and at least 2 jobs assigned to it:");
				
		List<User> activeUsers = jpqlQueries.getActiveUsersWithMinJobs("grid1", 2L);
				
		for(User user : activeUsers)
			System.out.println(user);
				
				
		System.out.println("\nGet most active users:");
				
		List<User> userList = jpqlQueries.getMostActiveUsers();
		
		for(User user : userList)
			System.out.println(user);
				
//		List<Object> usersAndJobs = entityManager.createNamedQuery("activeUsersWithXJobs")
//					 						  .setParameter("gname", "grid1")
//					 						  .getResultList();
//		
//
//		System.out.println("Users and Jobs:");
//		
//		Object[] users1 = (Object[])usersAndJobs.get(0);
//		Object[] users2 = (Object[])usersAndJobs.get(1);
//
//		for(Object user : users1) 
//			System.out.println(user);
//		
//		for(Object user : users2) 
//			System.out.println(user);
				
				
//		List<Job> jobs = entityManager.createNamedQuery("jobsOfActiveUsers")
//											  .setParameter("gname", "grid1")
//											  .getResultList();
//				
//		for(Job job : jobs)
//			System.out.println(job);
				
//		Long countUsers = (Long)entityManager.createNamedQuery("activeUsersWithXJobs")
//				  .setParameter("gname", "grid1")
//				  .setParameter("cgname", "grid1")
//				  .getSingleResult();
//
//		System.out.println(countUsers);
		
		
				
//		System.out.println("\n---------------  GRIDS AND ADMINS  --------------");
//				
//		System.out.println("\nRetrieving memberships");
//		List<Membership> memberships = membershipDAO.getAllMemberships();
//		for(Membership membership : memberships)
//			System.out.println(membership.toExtendedString());
//				
//		System.out.println("\nRetrieving clusters");
//		List<Cluster> clusters = clusterDAO.getAllClusters();
//		for(Cluster cluster : clusters) 
//			System.out.println(cluster.toExtendedString());
//
//		
//		System.out.println("\n---------------  CLUSTERS AND COMPUTERS  --------------");
//				
//		System.out.println("\nRetrieving Computers");
//		List<Computer> computers = computerDAO.getComputersFromTo(7L, 10L);
//		for(Computer computer : computers) 
//			System.out.println(computer.toExtendedString());
//				
//				
//		System.out.println("\nRetrieving Computers");
//		computers = computerDAO.getComputersFromTo(7L, 10L);
//		for(Computer computer : computers) 
//			System.out.println(computer.toExtendedString());
//				
//		System.out.println("\nRetrieving Cluster Relations");
//				
//		clusters = clusterDAO.getAllClusters();
//		for(Cluster cluster : clusters) 
//			System.out.println(
//					clusterDAO.getAllChildrenString(
//							cluster.getClusterId()
//					));
//		
//		
//		System.out.println("\n---------------  EXECUTION AND COMPUTERS  --------------");
//				
//		System.out.println("\nRetrieving all Executions");
//		List<Execution> executionList = executionDAO.getAllExecutions();
//		System.out.println("executionId   -> computerIds");
//		for(Execution execution : executionList) 
//			System.out.println(
//					executionDAO.getComputerListAsString(
//							execution.getExecutionId()
//					));
//				
//		System.out.println("\nRemoving Computers with id = 13 and id = 14");
//		computerDAO.removeComputer(13L);
//		computerDAO.removeComputer(14L);
//				
//		System.out.println("\nRetrieving all Executions");
//		executionList = executionDAO.getAllExecutions();
//		System.out.println("executionId   -> computerIds");
//		for(Execution execution : executionList) 
//			System.out.println(
//					executionDAO.getComputerListAsString(
//							execution.getExecutionId()
//					));
//		
//		System.out.println("\nRemoving Execution with id = 6");
//		executionDAO.removeExecution(6L);
//		
//		System.out.println("\nRetrieving all Executions");
//		executionList = executionDAO.getAllExecutions();
//		System.out.println("executionId   -> computerIds");
//		for(Execution execution : executionList) 
//			System.out.println(
//					executionDAO.getComputerListAsString(
//							execution.getExecutionId()
//					));
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
