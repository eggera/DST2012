package dst1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import dst1.model.Address;
import dst1.model.Admin;
import dst1.model.AdminDAO;
import dst1.model.AdminDAOTest;
import dst1.model.Cluster;
import dst1.model.Computer;
import dst1.model.Environment;
import dst1.model.Execution;
import dst1.model.Grid;
import dst1.model.Job;
import dst1.model.Membership;
import dst1.model.PersistenceUtil;
import dst1.model.Service;
import dst1.model.User;
import dst1.model.UserDAO;
import dst1.model.UserDAOTest;

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
		
//		entityManager.persist(job1);
//		entityManager.persist(job2);
//		entityManager.persist(job3);
//		
//		List<Job> jobs = entityManager.createQuery(" from Job", Job.class).getResultList();
//		
//		System.out.println("jobs : "+jobs);
		
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
		
		Admin admin1 = new Admin("Huaba", "Sepp", new Address("street1","city4","9203"));
		entityManager.persist(admin1);
		
		Cluster cluster1 = new Cluster("cluster1", 
										new Date(System.currentTimeMillis()), 
										new Date(System.currentTimeMillis() + 1000*60*60));
		
		Cluster cluster2 = new Cluster("cluster2", 
										new Date(System.currentTimeMillis() - 1000*60*60), 
										new Date(System.currentTimeMillis()));
		
		cluster1.setAdmin(admin1);
		cluster2.setAdmin(admin1);
		admin1.addCluster(cluster1);
		admin1.addCluster(cluster2);
		
		cluster1.setGrid(grid1);
		cluster2.setGrid(grid2);
		grid1.addCluster(cluster1);
		grid2.addCluster(cluster2);
		
		entityManager.persist(cluster1);
		entityManager.persist(cluster2);
		
//		entityManager.remove(cluster1);
		
//		List<Cluster> clusterList = admin1.getClusterList();
//		for(Cluster cluster : clusterList)
//			cluster.setAdmin(null);
//		
//		entityManager.remove(admin1);
//		entityManager.remove(grid1);
				
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
		
//		User user_ = entityManager.find(User.class, 1L);
//		entityManager.remove(user_);
//		
		Grid grid_ = entityManager.find(Grid.class, 1L);
		List<Cluster> clusterList = grid_.getClusterList();
		for(Cluster cluster : clusterList) 
			cluster.setGrid(null);
		entityManager.remove(grid_);
		
//		Execution execution = entityManager.find(Execution.class, 1L);
//		entityManager.remove(execution);
//		
//		Environment environment_ = entityManager.find(Environment.class, 1L);
//		entityManager.remove(environment_);
//		
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
