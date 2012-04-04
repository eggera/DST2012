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
		AdminDAO adminDAO = new AdminDAO(entityManager);
		EnvironmentDAO environmentDAO = new EnvironmentDAO(entityManager);
		
		entityManager.getTransaction().begin();
		
		
		
		User user1 = new User("Herbert","Franz", new Address("street1","city1","4000"), 
								"herbi", Service.getMD5Hash("herb"));
		
		User user2 = new User("Dennis","Fennis", new Address("street2","city2","6000"), 
				"den", Service.getMD5Hash("denfen"));
		
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
		user1.addJob(job3);
		
		user2.addJob(job4);
		user2.addJob(job5);
		user2.addJob(job6);
			
		
		Execution execution1 = new Execution(
						new Date(System.currentTimeMillis()),
						new Date(System.currentTimeMillis() + 1000*60*60),
						Execution.JobStatus.RUNNING);
		
		Execution execution2 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60),
						new Date(System.currentTimeMillis() + 1000*60*60*2),
						Execution.JobStatus.RUNNING);
		
		Execution execution3 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*2),
						new Date(System.currentTimeMillis() + 1000*60*60*3),
						Execution.JobStatus.RUNNING);
		
		Execution execution4 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*3),
						new Date(System.currentTimeMillis() + 1000*60*60*4),
						Execution.JobStatus.RUNNING);
		
		Execution execution5 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*4),
						new Date(System.currentTimeMillis() + 1000*60*60*5),
						Execution.JobStatus.RUNNING);
		
		Execution execution6 = new Execution(
						new Date(System.currentTimeMillis() + 1000*60*60*5),
						new Date(System.currentTimeMillis() + 1000*60*60*6),
						Execution.JobStatus.RUNNING);
		
		job1.setExecution(execution1);
		job2.setExecution(execution2);
		job3.setExecution(execution3);
		job4.setExecution(execution4);
		job5.setExecution(execution5);
		job6.setExecution(execution6);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		
		List<User> users = entityManager.createQuery(" from User", User.class)
									.getResultList();
		System.out.println("user : \n"+users);
		
		
		Grid grid1 = new Grid("grid1", "G1", new BigDecimal(0.11));
		Grid grid2 = new Grid("grid2", "G2", new BigDecimal(0.22));
		entityManager.persist(grid1);
		entityManager.persist(grid2);

		Membership membership = new Membership(grid1, user1, 
										new Date(System.currentTimeMillis()), 
										new Double(5.5));
		
		Membership membership2 = new Membership(grid1, user2, 
										new Date(System.currentTimeMillis()), 
										new Double(6.2));
		
		Membership membership3 = new Membership(grid2, user1, 
										new Date(System.currentTimeMillis()), 
										new Double(4.4));
		
		entityManager.persist(membership);
		entityManager.persist(membership2);
		entityManager.persist(membership3);
		
//		userDAO.saveUser();
//		adminDAO.saveAdmin();
		
		Admin admin1 = new Admin("Huaba", "Suda", new Address("street1","city1","1111"));
		Admin admin2 = new Admin("Stephan", "Ertl", new Address("street2","city2","2222"));
		Admin admin3 = new Admin("Sepp", "Depp", new Address("street3","city3","3333"));
		Admin admin4 = new Admin("Gert", "Erd", new Address("street4","city4","4444"));
		entityManager.persist(admin1);
		entityManager.persist(admin2);
		entityManager.persist(admin3);
		entityManager.persist(admin4);
		
		Cluster cluster1 = new Cluster("cluster1", 
										new Date(System.currentTimeMillis()), 
										new Date(System.currentTimeMillis()));
		
		Cluster cluster2 = new Cluster("cluster2", 
										new Date(System.currentTimeMillis() - 1000*60*60), 
										new Date(System.currentTimeMillis() + 1000*60*60));
		
		Cluster cluster3 = new Cluster("cluster3", 
										new Date(System.currentTimeMillis() - 1000*60*60*2), 
										new Date(System.currentTimeMillis() + 1000*60*60*2));

		Cluster cluster4 = new Cluster("cluster4", 
										new Date(System.currentTimeMillis() - 1000*60*60*3), 
										new Date(System.currentTimeMillis() + 1000*60*60*3));
		
		Cluster cluster5 = new Cluster("cluster5", 
										new Date(System.currentTimeMillis() - 1000*60*60*4), 
										new Date(System.currentTimeMillis() + 1000*60*60*4));
		
		Cluster cluster6 = new Cluster("cluster6", 
										new Date(System.currentTimeMillis() - 1000*60*60*5), 
										new Date(System.currentTimeMillis() + 1000*60*60*5));
		
		cluster1.setAdmin(admin1);
		cluster2.setAdmin(admin1);
		cluster3.setAdmin(admin2);
		cluster4.setAdmin(admin2);
		cluster5.setAdmin(admin3);
		cluster6.setAdmin(admin4);
		
		admin1.addCluster(cluster1);
		admin1.addCluster(cluster2);
		admin2.addCluster(cluster3);
		admin2.addCluster(cluster4);
		admin3.addCluster(cluster5);
		admin4.addCluster(cluster6);
		
		cluster1.setGrid(grid1);
		cluster2.setGrid(grid1);
		cluster3.setGrid(grid1);
		cluster4.setGrid(grid2);
		cluster5.setGrid(grid2);
		cluster6.setGrid(grid2);
		
		grid1.addCluster(cluster1);
		grid1.addCluster(cluster2);
		grid1.addCluster(cluster3);
		grid2.addCluster(cluster4);
		grid2.addCluster(cluster5);
		grid2.addCluster(cluster6);
		
		
		//  cluster 1 --> cluster 2, cluster3
		//  cluster 2 --> cluster 4
		//  cluster 3 --> cluster 5
		//  cluster 4 --> cluster 5
		//  cluster 4 --> cluster 6
		//  cluster 5 --> cluster 6
		
		cluster1.addSubCluster(cluster2);
		cluster1.addSubCluster(cluster3);
		cluster2.addSuperCluster(cluster1);
		cluster3.addSuperCluster(cluster1);
		
		cluster2.addSubCluster(cluster4);
		cluster4.addSuperCluster(cluster2);
		
		cluster3.addSubCluster(cluster5);
		cluster5.addSuperCluster(cluster3);
		
		cluster4.addSubCluster(cluster5);
		cluster4.addSubCluster(cluster6);
		cluster5.addSuperCluster(cluster4);
		cluster6.addSuperCluster(cluster4);
		
		cluster5.addSubCluster(cluster6);
		cluster6.addSuperCluster(cluster5);
		
		entityManager.persist(cluster1);
		entityManager.persist(cluster2);
		entityManager.persist(cluster3);
		entityManager.persist(cluster4);
		entityManager.persist(cluster5);
		entityManager.persist(cluster6);
		
		System.out.println("SUPER CLUSTER SIZE = " + cluster5.getSuperCluster().size());
		
		for(Cluster cluster : cluster5.getSuperCluster())
			System.out.println("super cluster id = "+cluster.getClusterId());
		
//		for(Cluster cluster : cluster4.getSubCluster())
//			cluster.removeSuperCluster(cluster4);
//				
//		entityManager.remove(cluster1);
//				
//				
//				
//				
//				for(Cluster cluster : cluster2.getSuperCluster())
//					System.out.println("super cluster id = "+cluster.getClusterId());
				
		Computer comp = new Computer("Comp1", 10, "G1C2", 
			  				new Date(System.currentTimeMillis() - 1000*60*60),
			  				new Date(System.currentTimeMillis()));
		entityManager.persist(comp);
		
		List<Computer> result = entityManager.createQuery( 
									"from Computer", Computer.class )
									.getResultList();
		
		System.out.println("Computer : \n"+result.get(0));
		
		entityManager.flush();
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
		// ------------------ DELETE ENTITIES ---------------------------
		
		entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
//		Admin admin_ = entityManager.find(Admin.class, 1L);
//		List<Cluster> adminClusterList = admin_.getClusterList();
//		for(Cluster cluster : adminClusterList) 
//			cluster.setAdmin(null);
//		entityManager.remove(admin_);
//		
//		User user_ = entityManager.find(User.class, 1L);
//		entityManager.remove(user_);
//		
//		Grid grid_ = entityManager.find(Grid.class, 1L);
//		List<Cluster> gridClusterList = grid_.getClusterList();
//		for(Cluster cluster : gridClusterList) 
//			cluster.setGrid(null);
//		entityManager.remove(grid_);
		
//		Execution execution = entityManager.find(Execution.class, 1L);
//		entityManager.remove(execution);
//		
		Environment environment_ = entityManager.find(Environment.class, 1L);
		environmentDAO.setEntityManager(entityManager);
		environmentDAO.removeEnvironment(environment_);
		
		
		
//		Job job = entityManager.find(Job.class, 1L);
//		entityManager.remove(job);
		
//		entityManager.persist(execution);
//		
//		List<Execution> ex_result = entityManager.createQuery(
//										"from Execution", Execution.class )
//										.getResultList();
//		
//		System.out.println("Execution : \n"+ex_result.get(0));
		
//		List<String> params = new ArrayList<String>();
//		params.add("1st");
//		params.add("2nd");
//		params.add("3rd");
//		Environment environment = new Environment("workflow", params);
//		
//		System.out.println("Environment : \n"+environment);
//		
//		entityManager.persist(environment);
//		
//		entityManager.getTransaction().commit();
//		
//		entityManager.getTransaction().begin();
//		
//		List<Environment> env_result = entityManager.createQuery(
//											"from Environment", Environment.class )
//											.getResultList();
//		
//		System.out.println("Environment : \n"+env_result.get(0));
		
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
