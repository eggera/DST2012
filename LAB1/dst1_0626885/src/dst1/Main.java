package dst1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import dst1.model.*;

import dst1.model.PersistenceUtil;

public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
		dst01();
		dst02a();
		dst02b();
		dst02c();
		dst03();
		dst04a();
		dst04b();
		dst04c();
		dst04d();
		dst05a();
		dst05b();
		dst05c();
	}

	public static void dst01() {
	
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		UserDAO 		userDAO 		= new UserDAO		(entityManager);
		GridDAO 		gridDAO 		= new GridDAO		(entityManager);
		MembershipDAO 	membershipDAO 	= new MembershipDAO	(entityManager);
		AdminDAO 		adminDAO 		= new AdminDAO		(entityManager);
		ClusterDAO 		clusterDAO 		= new ClusterDAO	(entityManager);
		ComputerDAO 	computerDAO 	= new ComputerDAO	(entityManager);
		JobDAO 			jobDAO 			= new JobDAO		(entityManager);
		ExecutionDAO 	executionDAO 	= new ExecutionDAO	(entityManager);
		EnvironmentDAO 	environmentDAO 	= new EnvironmentDAO(entityManager);
		
		entityManager.getTransaction().begin();
		
		
		
		User user1 = new User("Herbert","Franz", new Address("street1","city1","2000"), 
								"herbi", Service.getMD5Hash("herb"));
		
		User user2 = new User("Dennis","Fennis", new Address("street2","city2","4000"), 
								"den", Service.getMD5Hash("denfen"));
		
		User user3 = new User("Pepp","Druml", new Address("street3","city3","6000"), 
								"pepp", Service.getMD5Hash("pdruml"));

		User user4 = new User("Manz","Fanz", new Address("street4","city4","8000"), 
								"manz", Service.getMD5Hash("mfanz"));
		
		Job job1 = new Job();
		Job job2 = new Job();
		Job job3 = new Job();
		
		Job job4 = new Job();
		Job job5 = new Job();
		Job job6 = new Job();
		
		List<String> params = new ArrayList<String>();
		params.add("param1");
		params.add("param2");
		params.add("param3");
		
		Environment environment1 = new Environment("workflow1", params);
		Environment environment2 = new Environment("workflow2", params);
		Environment environment3 = new Environment("workflow3", params);
		
		job1.setEnvironment(environment1);
		job2.setEnvironment(environment2);
		job3.setEnvironment(environment3);
		
		job4.setEnvironment(environment1);
		job5.setEnvironment(environment2);
		job6.setEnvironment(environment3);

		
		user1.addJob(job1);
		user1.addJob(job2);
		user2.addJob(job3);
		
		user3.addJob(job4);
		user4.addJob(job5);
		user4.addJob(job6);
			
		
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
		
		userDAO.saveUser(user1);
		userDAO.saveUser(user2);
		userDAO.saveUser(user3);
		userDAO.saveUser(user4);
		
		
		Grid grid1 = new Grid("grid1", "G1", new BigDecimal(0.11));
		Grid grid2 = new Grid("grid2", "G2", new BigDecimal(0.22));
		Grid grid3 = new Grid("grid3", "G3", new BigDecimal(0.33));
		
		gridDAO.saveGrid(grid1);
		gridDAO.saveGrid(grid2);
		gridDAO.saveGrid(grid3);
		

		Membership membership1 = new Membership(grid1, user1, 
										new Date(System.currentTimeMillis()), 
										new Double(1.1));
		
		Membership membership2 = new Membership(grid1, user2, 
										new Date(System.currentTimeMillis()), 
										new Double(2.2));
		
		Membership membership3 = new Membership(grid2, user3, 
										new Date(System.currentTimeMillis()), 
										new Double(3.3));
		
		Membership membership4 = new Membership(grid2, user4, 
										new Date(System.currentTimeMillis()), 
										new Double(4.4));
		
		Membership membership5 = new Membership(grid3, user2, 
										new Date(System.currentTimeMillis()), 
										new Double(5.5));
		
		Membership membership6 = new Membership(grid3, user4, 
										new Date(System.currentTimeMillis()), 
										new Double(6.6));
		
		membershipDAO.saveMembership(membership1);
		membershipDAO.saveMembership(membership2);
		membershipDAO.saveMembership(membership3);
		membershipDAO.saveMembership(membership4);
		membershipDAO.saveMembership(membership5);
		membershipDAO.saveMembership(membership6);
		
		
		Admin admin1 = new Admin("Huaba", "Suda", new Address("street1","city1","1111"));
		Admin admin2 = new Admin("Stephan", "Ertl", new Address("street2","city2","2222"));
		Admin admin3 = new Admin("Sepp", "Depp", new Address("street3","city3","3333"));
		Admin admin4 = new Admin("Gert", "Erd", new Address("street4","city4","4444"));
		
		adminDAO.saveAdmin(admin1);
		adminDAO.saveAdmin(admin2);
		adminDAO.saveAdmin(admin3);
		adminDAO.saveAdmin(admin4);
		
		
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
		admin2.addCluster(cluster3);
		admin2.addCluster(cluster4);
		admin3.addCluster(cluster5);
		admin3.addCluster(cluster6);
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
		
		
		//  cluster 8 --> cluster 2
		//  cluster 8 --> cluster 4
		//  cluster 8 --> cluster 7
		//  cluster 1 --> cluster 2
		//  cluster 1 --> cluster 3
		//  cluster 2 --> cluster 4
		//  cluster 2 --> cluster 6
		//  cluster 3 --> cluster 4
		//  cluster 3 --> cluster 5
		//  cluster 4 --> cluster 6
		//  cluster 4 --> cluster 7
		//  cluster 5 --> cluster 6
		//  cluster 5 --> cluster 7
		
		
		cluster1.addSubCluster(cluster2);
		cluster1.addSubCluster(cluster3);
		cluster2.addSuperCluster(cluster1);
		cluster3.addSuperCluster(cluster1);
		
		cluster2.addSubCluster(cluster4);
		cluster2.addSubCluster(cluster6);
		cluster4.addSuperCluster(cluster2);
		cluster6.addSuperCluster(cluster2);
		
		cluster3.addSubCluster(cluster4);
		cluster3.addSubCluster(cluster5);
		cluster4.addSuperCluster(cluster3);
		cluster5.addSuperCluster(cluster3);
		
		cluster4.addSubCluster(cluster6);
		cluster4.addSubCluster(cluster7);
		cluster6.addSuperCluster(cluster4);
		cluster7.addSuperCluster(cluster4);
		
		cluster5.addSubCluster(cluster6);
		cluster5.addSubCluster(cluster7);
		cluster6.addSuperCluster(cluster5);
		cluster7.addSuperCluster(cluster5);
		
		cluster8.addSubCluster(cluster2);
		cluster8.addSubCluster(cluster4);
		cluster8.addSubCluster(cluster7);
		cluster2.addSuperCluster(cluster8);
		cluster4.addSuperCluster(cluster8);
		cluster7.addSuperCluster(cluster8);
		
		clusterDAO.saveCluster(cluster1);
		clusterDAO.saveCluster(cluster2);
		clusterDAO.saveCluster(cluster3);
		clusterDAO.saveCluster(cluster4);
		clusterDAO.saveCluster(cluster5);
		clusterDAO.saveCluster(cluster6);
		clusterDAO.saveCluster(cluster7);
		clusterDAO.saveCluster(cluster8);
	
				
		Computer computer1 = new Computer("Computer1", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer2 = new Computer("Computer2", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*2),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer3 = new Computer("Computer3", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*3),
			  				new Date(System.currentTimeMillis()));
				
		Computer computer4 = new Computer("Computer4", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*4),
			  				new Date(System.currentTimeMillis()));
		
		Computer computer5 = new Computer("Computer5", 1, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60*5),
			  				new Date(System.currentTimeMillis()));
	
		Computer computer6 = new Computer("Computer6", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*6),
							new Date(System.currentTimeMillis()));
			
		Computer computer7 = new Computer("Computer7", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*7),
							new Date(System.currentTimeMillis()));
			
		Computer computer8 = new Computer("Computer8", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*8),
							new Date(System.currentTimeMillis()));
		
		Computer computer9 = new Computer("Computer9", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*9),
							new Date(System.currentTimeMillis()));
		
		Computer computer10 = new Computer("Computer10", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*10),
							new Date(System.currentTimeMillis()));
		
		Computer computer11 = new Computer("Computer11", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*11),
							new Date(System.currentTimeMillis()));
		
		Computer computer12 = new Computer("Computer12", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*12),
							new Date(System.currentTimeMillis()));
		
		Computer computer13 = new Computer("Computer13", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*13),
							new Date(System.currentTimeMillis()));
		
		Computer computer14 = new Computer("Computer14", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*14),
							new Date(System.currentTimeMillis()));
					
		Computer computer15 = new Computer("Computer15", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*15),
							new Date(System.currentTimeMillis()));
		
		Computer computer16 = new Computer("Computer16", 1, "G1C2", 
							new Date(System.currentTimeMillis() - 1000*60*60*16),
							new Date(System.currentTimeMillis()));
				
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
		
		cluster1.addComputer(computer1);
		cluster1.addComputer(computer2);
		cluster1.addComputer(computer3);
		cluster1.addComputer(computer4);
		cluster2.addComputer(computer5);
		cluster2.addComputer(computer6);
		cluster3.addComputer(computer7);
		cluster3.addComputer(computer8);
		cluster4.addComputer(computer9);
		cluster4.addComputer(computer10);
		cluster5.addComputer(computer11);
		cluster6.addComputer(computer12);
		cluster6.addComputer(computer13);
		cluster7.addComputer(computer14);
		cluster8.addComputer(computer15);
		cluster8.addComputer(computer16);
		
		
//	------------  COMPUTER AND EXECUTION  -------------
		
		execution1.addComputer(computer1);
		execution1.addComputer(computer2);
		execution1.addComputer(computer3);
		execution1.addComputer(computer4);
		execution1.addComputer(computer5);
		execution1.addComputer(computer6);
		execution1.addComputer(computer7);
		execution1.addComputer(computer8);
		
		computer1.addExecution(execution1);
		computer2.addExecution(execution1);
		computer3.addExecution(execution1);
		computer4.addExecution(execution1);
		computer5.addExecution(execution1);
		computer6.addExecution(execution1);
		computer7.addExecution(execution1);
		computer8.addExecution(execution1);
		
		execution2.addComputer(computer9);
		execution2.addComputer(computer10);
		execution2.addComputer(computer11);
		execution2.addComputer(computer12);
		
		computer9.addExecution(execution2);
		computer10.addExecution(execution2);
		computer11.addExecution(execution2);
		computer12.addExecution(execution2);
		
		execution3.addComputer(computer13);
		execution3.addComputer(computer14);
		
		computer13.addExecution(execution3);
		computer14.addExecution(execution3);
		
		execution4.addComputer(computer15);
		execution4.addComputer(computer16);
		
		computer15.addExecution(execution4);
		computer16.addExecution(execution4);
		
		execution5.addComputer(computer5);
		execution5.addComputer(computer6);
		execution5.addComputer(computer7);
		execution5.addComputer(computer8);
		
		computer5.addExecution(execution5);
		computer6.addExecution(execution5);
		computer7.addExecution(execution5);
		computer8.addExecution(execution5);
		
		execution6.addComputer(computer13);
		execution6.addComputer(computer14);
		execution6.addComputer(computer15);
		execution6.addComputer(computer16);
		
		computer13.addExecution(execution6);
		computer14.addExecution(execution6);
		computer15.addExecution(execution6);
		computer16.addExecution(execution6);
		
//		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
		// ------------------ DELETE ENTITIES ---------------------------
		
		entityManager = entityManagerFactory.createEntityManager();
		
		adminDAO		.setEntityManager(entityManager);
		clusterDAO		.setEntityManager(entityManager);
		computerDAO		.setEntityManager(entityManager);
		environmentDAO	.setEntityManager(entityManager);
		executionDAO	.setEntityManager(entityManager);
		gridDAO			.setEntityManager(entityManager);
		jobDAO			.setEntityManager(entityManager);
		membershipDAO	.setEntityManager(entityManager);
		userDAO			.setEntityManager(entityManager);
		
		entityManager.getTransaction().begin();
		
		
		adminDAO.removeAdmin(1L);
		
		userDAO.removeUser(3L);
		userDAO.removeUser(4L);
		
		User user = userDAO.findUser(1L);
		System.out.println("User 1 jobs: ");
		System.out.println(user.getJobList());
		
		System.out.println("Removing job 1");
		jobDAO.removeJob(1L);
		System.out.println("User 1 jobs: ");
		System.out.println(user.getJobList());
		
		System.out.println("Removing grid 1");
		
		gridDAO.removeGrid(1L);
		
		System.out.println("Execution 3 computer list:");
		
//		System.out.println("Execution:");
//		System.out.println(executionDAO.find(1L).getComputerList());
		System.out.println(executionDAO.find(3L).getComputerList());
//		
//		System.out.println("Removing computer 4,5,9 and 10 ..");
//		
		System.out.println("Removing computer 13 .. ");
//		computerDAO.removeComputer(4L);
//		computerDAO.removeComputer(5L);
//		computerDAO.removeComputer(9L);
//		computerDAO.removeComputer(10L);
		computerDAO.removeComputer(13L);
//		
		System.out.println("Execution 3 computer list:");
//		System.out.println("Execution:");
//		System.out.println(executionDAO.find(1L).getComputerList());
		System.out.println(executionDAO.find(3L).getComputerList());
		
		System.out.println("cluster1 computers : \n"+clusterDAO.findCluster(1L).getComputers());
		System.out.println("cluster7 computers : \n"+clusterDAO.findCluster(7L).getComputers());
		
		System.out.println("------------  Remove cluster  -------------");
		
		System.out.println("Removing cluster 4");
		clusterDAO.removeCluster(4L);
		
		System.out.println("admin 2 and grid 2 should now have only one entry: ");
		System.out.println("admin2 clusters: "+adminDAO.findAdmin(2L).getClusterList());
		
		System.out.println("grid2 clusters: "+gridDAO.findGrid(2L).getClusterList());
		
		System.out.println("Removing execution 1 ..");
		executionDAO.removeExecution(1L);
		
		System.out.println("Removing environment 2 ..");
		environmentDAO.removeEnvironment(2L);
				
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
		PersistenceUtil.freeResources();
	}

	public static void dst02a() {

	}

	public static void dst02b() {

	}

	public static void dst02c() {

	}

	public static void dst03() {

	}

	public static void dst04a() {

	}

	public static void dst04b() {

	}

	public static void dst04c() {

	}

	public static void dst04d() {

	}

        public static void dst05a() {

        }

        public static void dst05b() {

        }

        public static void dst05c() {

        }
}
