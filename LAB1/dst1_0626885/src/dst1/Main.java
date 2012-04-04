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
		
		UserDAO userDAO = new UserDAO(entityManager);
		GridDAO gridDAO = new GridDAO(entityManager);
		MembershipDAO membershipDAO = new MembershipDAO(entityManager);
		AdminDAO adminDAO = new AdminDAO(entityManager);
		ClusterDAO clusterDAO = new ClusterDAO(entityManager);
		ComputerDAO computerDAO = new ComputerDAO(entityManager);
		
		EnvironmentDAO environmentDAO = new EnvironmentDAO(entityManager);
		ExecutionDAO executionDAO = new ExecutionDAO(entityManager);
		
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
		
		job1.setUser(user1);
		job2.setUser(user1);
		job3.setUser(user2);
		
		job4.setUser(user3);
		job5.setUser(user4);
		job6.setUser(user4);
			
		
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
		
		execution1.setJob(job1);
		execution2.setJob(job2);
		execution3.setJob(job3);
		execution4.setJob(job4);
		execution5.setJob(job5);
		execution6.setJob(job6);
		
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
		
		cluster1.setAdmin(admin1);
		cluster2.setAdmin(admin1);
		cluster3.setAdmin(admin2);
		cluster4.setAdmin(admin2);
		cluster5.setAdmin(admin3);
		cluster6.setAdmin(admin3);
		cluster7.setAdmin(admin4);
		cluster8.setAdmin(admin4);
		
		admin1.addCluster(cluster1);
		admin1.addCluster(cluster2);
		admin2.addCluster(cluster3);
		admin2.addCluster(cluster4);
		admin3.addCluster(cluster5);
		admin3.addCluster(cluster6);
		admin4.addCluster(cluster7);
		admin4.addCluster(cluster8);
		
		cluster1.setGrid(grid1);
		cluster2.setGrid(grid1);
		cluster3.setGrid(grid1);
		cluster4.setGrid(grid2);
		cluster5.setGrid(grid2);
		cluster6.setGrid(grid3);
		cluster7.setGrid(grid3);
		cluster8.setGrid(grid3);
		
		grid1.addCluster(cluster1);
		grid1.addCluster(cluster2);
		grid1.addCluster(cluster3);
		grid2.addCluster(cluster4);
		grid2.addCluster(cluster5);
		grid3.addCluster(cluster6);
		grid3.addCluster(cluster7);
		grid3.addCluster(cluster8);
		
		
		//  cluster 8 --> cluster 1
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
		
		cluster8.addSubCluster(cluster2);
		cluster8.addSubCluster(cluster4);
		cluster8.addSubCluster(cluster7);
		cluster2.addSuperCluster(cluster8);
		cluster4.addSuperCluster(cluster8);
		cluster7.addSuperCluster(cluster8);
		
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
		
		clusterDAO.saveCluster(cluster1);
		clusterDAO.saveCluster(cluster2);
		clusterDAO.saveCluster(cluster3);
		clusterDAO.saveCluster(cluster4);
		clusterDAO.saveCluster(cluster5);
		clusterDAO.saveCluster(cluster6);
		clusterDAO.saveCluster(cluster7);
		clusterDAO.saveCluster(cluster8);
		
		System.out.println("SUPER CLUSTER SIZE (cluster4) = " + cluster4.getSuperClusters().size());
		
		for(Cluster cluster : cluster4.getSuperClusters())
			System.out.println("super cluster id = "+cluster.getClusterId());
			
				
		Computer computer = new Computer("Comp1", 10, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60),
			  				new Date(System.currentTimeMillis()));
				
//		entityManager.persist(computer);
		computerDAO.saveComputer(computer);
		
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
		// ------------------ DELETE ENTITIES ---------------------------
		
		entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
//		adminDAO.setEntityManager(entityManager);
//		adminDAO.removeAdmin(1L);
//		
//		userDAO.setEntityManager(entityManager);
//		userDAO.removeUser(3L);
//		userDAO.removeUser(4L);
//		
//		gridDAO.setEntityManager(entityManager);
//		gridDAO.removeGrid(1L);
//		
//		clusterDAO.setEntityManager(entityManager);
//		clusterDAO.removeCluster(4L);
//		
//		executionDAO.setEntityManager(entityManager);
//		executionDAO.removeExecution(4L);
//
//		environmentDAO.setEntityManager(entityManager);
//		environmentDAO.removeEnvironment(2L);
				
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
/*		UserDAOTest userDAOTest = new UserDAOTest();
		AdminDAOTest adminDAOTest = new AdminDAOTest();
		userDAOTest.saveUserTest();
		userDAOTest.removeUserTest();
		adminDAOTest.saveAdminTest();
		adminDAOTest.removeAdminTest();
		userDAOTest.freeResources();
		adminDAOTest.freeResources();*/
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
