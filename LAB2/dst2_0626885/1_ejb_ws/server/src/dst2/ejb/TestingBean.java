package dst2.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.Address;
import dst1.model.Admin;
import dst1.model.AdminDAO;
import dst1.model.Cluster;
import dst1.model.ClusterDAO;
import dst1.model.Computer;
import dst1.model.ComputerDAO;
import dst1.model.Environment;
import dst1.model.Execution;
import dst1.model.Grid;
import dst1.model.GridDAO;
import dst1.model.Job;
import dst1.model.Membership;
import dst1.model.MembershipDAO;
import dst1.model.User;
import dst1.model.UserDAO;
import dst1.service.Service;


@Stateless
public class TestingBean implements Testing {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void saveEntities() {
		
		
		UserDAO 		userDAO 		= new UserDAO		(entityManager);
		GridDAO 		gridDAO 		= new GridDAO		(entityManager);
		MembershipDAO 	membershipDAO 	= new MembershipDAO	(entityManager);
		AdminDAO 		adminDAO 		= new AdminDAO		(entityManager);
		ClusterDAO 		clusterDAO 		= new ClusterDAO	(entityManager);
		ComputerDAO 	computerDAO 	= new ComputerDAO	(entityManager);
		
		
		System.out.println("Creating users ... ");
		
		User user1 = new User("User1","User1Last", new Address("street1","city1","1000"), 
								"usr1", Service.getMD5Hash("usr1"), "1234", "1000");
		
		User user2 = new User("User2","User2Last", new Address("street2","city2","2000"), 
								"usr2", Service.getMD5Hash("usr2"), "2345", "2000");
		
		System.out.println("Creating jobs ... ");
		
		Job job1 = new Job();
		
		System.out.println("Creating environments ... ");
		
		List<String> params1 = new ArrayList<String>();
		params1.add("param1");
		params1.add("param2");
		params1.add("param3");
		
		
		Environment environment1 = new Environment("workflow1", params1);
		
		job1.setEnvironment(environment1);

		
		user1.addJob(job1);
			
		System.out.println("Creating executions ... ");
		
		Date date = Service.getReferenceDate();

		Execution execution1 = new Execution( new Date(date.getTime() - 1000*60*30), null, Execution.JobStatus.RUNNING );
		
		job1.setExecution(execution1);
		
		System.out.println("Saving   users ... ");
		
		userDAO.saveUser(user1);
		userDAO.saveUser(user2);
		
		System.out.println("Creating grids ... ");
		
		Grid grid1 = new Grid("grid1", "G1", new BigDecimal(11));
		Grid grid2 = new Grid("grid2", "G2", new BigDecimal(22));
		
		System.out.println("Saving   grids ... ");
		
		gridDAO.saveGrid(grid1);
		gridDAO.saveGrid(grid2);
		
		System.out.println("Creating memberships ... ");

		date = Service.getReferenceDate();

		Membership membership1 = new Membership(grid1, user1, 
										new Date(date.getTime()), 
										new Double(1.1));
		
		Membership membership2 = new Membership(grid1, user2, 
										new Date(date.getTime()), 
										new Double(2.2));
		
		Membership membership3 = new Membership(grid2, user2, 
										new Date(date.getTime()), 
										new Double(3.3));
		
		
		System.out.println("Saving   memberships ... ");
		
		membershipDAO.saveMembership(membership1);
		membershipDAO.saveMembership(membership2);
		membershipDAO.saveMembership(membership3);
		
		
		System.out.println("Creating admins ... ");
		
		Admin admin1 = new Admin("Admin1", "Admin1Last", new Address("street11","city11","1111"));
		
		System.out.println("Saving   admins ... ");
		
		adminDAO.saveAdmin(admin1);
		
		
		System.out.println("Creating clusters ... ");
		
		date = Service.getReferenceDate();
		
		Cluster cluster1 = new Cluster("cluster1", 
										new Date(date.getTime() - 1000*60*60), 
										new Date(date.getTime() + 1000*60*60));
		
		Cluster cluster2 = new Cluster("cluster2", 
										new Date(date.getTime() - 1000*60*60*2), 
										new Date(date.getTime() + 1000*60*60*2));
		
		Cluster cluster3 = new Cluster("cluster3", 
										new Date(date.getTime() - 1000*60*60*3), 
										new Date(date.getTime() + 1000*60*60*3));

		
		admin1.addCluster(cluster1);
		admin1.addCluster(cluster2);
		admin1.addCluster(cluster3);
	
		
		grid1.addCluster(cluster1);
		grid1.addCluster(cluster2);
		
		grid2.addCluster(cluster3);
		
		
		System.out.println("Saving   clusters ... ");
		
		clusterDAO.saveCluster(cluster1);
		clusterDAO.saveCluster(cluster2);
		clusterDAO.saveCluster(cluster3);
	
		System.out.println("Creating computers ... ");
		
		date = Service.getReferenceDate();

			
		Computer computer1 = new Computer("Computer1", 4, "AUT-VIE@1010", 
		  				new Date(date.getTime() - 1000*60*60*2),
		  				new Date(date.getTime() - 1000*60*30));
			
		Computer computer2 = new Computer("Computer2", 6, "AUT-VIE@1200", 
		  				new Date(date.getTime() - 1000*60*60*4),
		  				new Date(date.getTime() - 1000*60*30));
		
		Computer computer3 = new Computer("Computer3", 8, "AUT-VIE@1010", 
						new Date(date.getTime() - 1000*60*60*6),
						new Date(date.getTime() - 1000*60*30));
		
		Computer computer4 = new Computer("Computer4", 6, "AUT-KTN@9500", 
						new Date(date.getTime() - 1000*60*60*8),
						new Date(date.getTime() - 1000*60*30));

		Computer computer5 = new Computer("Computer5", 8, "AUT-KTN@9500", 
						new Date(date.getTime() - 1000*60*60*14),
						new Date(date.getTime() - 1000*60*30));
		
		Computer computer6 = new Computer("Computer6", 4, "AUT-SBG@5020", 
						new Date(date.getTime() - 1000*60*60*5),
						new Date(date.getTime() - 1000*60*30));
		
		Computer computer7 = new Computer("Computer7", 8, "AUT-SBG@5020", 
						new Date(date.getTime() - 1000*60*60*7),
						new Date(date.getTime() - 1000*60*30));
		
		Computer computer8 = new Computer("Computer8", 6, "AUT-SBG@5020", 
						new Date(date.getTime() - 1000*60*60*3),
						new Date(date.getTime() - 1000*60*30));
				
		
		System.out.println("Saving   computers ... ");
		
		
		cluster1.addComputer(computer1);
		cluster1.addComputer(computer2);
		cluster1.addComputer(computer3);
		cluster2.addComputer(computer4);
		cluster2.addComputer(computer5);
		cluster3.addComputer(computer6);
		cluster3.addComputer(computer7);
		cluster3.addComputer(computer8);
		
		computerDAO.saveComputer(computer1);
		computerDAO.saveComputer(computer2);
		computerDAO.saveComputer(computer3);
		computerDAO.saveComputer(computer4);
		computerDAO.saveComputer(computer5);
		computerDAO.saveComputer(computer6);
		computerDAO.saveComputer(computer7);
		computerDAO.saveComputer(computer8);
		
		
//	------------  COMPUTER AND EXECUTION  -------------

		execution1.addComputer(computer1);
		
		computer1.addExecution(execution1);
		
	}
	
}
