package dst2.ejb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst1.model.Execution.JobStatus;
import dst1.model.Grid;
import dst1.model.Job;
import dst1.model.Membership;
import dst2.ejb.exception.NoOpenBillsException;

@Stateless
public class GeneralManagementBean implements GeneralManagement {

	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB private PriceManagement priceManagement;

	@Override
	public void setPrice(Integer numberOfHistoricalJobs, BigDecimal price) {
		
		priceManagement.setPrice(numberOfHistoricalJobs, price);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTotalBillFor(String username) throws NoOpenBillsException {
		
		JobStatus status = JobStatus.FINISHED;
		
		// gets all unpaid finished jobs and related grids of this user 
		Query query1 = entityManager.createQuery("select j, g" +
						 		"	from User u " +
								"				join u.jobList j 		join j.execution e" +
						 		" 				join e.computerList c 	join c.cluster cl" +
								"				join cl.grid g" +
								"	where u.username = :username and j.isPaid = false and " +
								"			e.status = :status ");
		
		// gets all memberships and related grids for this user
		Query query2 = entityManager.createQuery("select m, g" +
								"	from User u " +
								"				join u.membershipList m join m.grid g" +
								"	where u.username = :username", Grid.class);
		
		// get all paid (historical) jobs
		Query query3 = entityManager.createQuery("select j " +
								"	from Job j " +
								"	where j.isPaid = true", Job.class);
		
		query1.setParameter("username", username).setParameter("status", status);
		query2.setParameter("username", username);
		
		Map<Long, Double> discounts = new HashMap<Long, Double>();
		
		List<Object> unpaidJobs			 = query1.getResultList();
		List<Object> membershipAndGrids	 = query2.getResultList();
		List<Job>	 historicalJobs		 = query3.getResultList();
		
		
		if( unpaidJobs.isEmpty() )
			throw new NoOpenBillsException("No open bills for user "+username);
			
		
		System.out.println(" BILL RESULT  ================= ");
		

		BigDecimal executionCosts = BigDecimal.valueOf(0);
		
		System.out.println("  Memberships : ");
		
		for(Object arr : membershipAndGrids) {
			
			Object[] objArray = (Object[])arr;
			
			Membership membership 	= Membership.class.cast(objArray[0]);
			Grid grid				= Grid.class.cast(objArray[1]);
			
			discounts.put(grid.getGridId(), membership.getDiscount());
			
			System.out.println("discount = "+discounts.get(grid.getGridId()));
			
			System.out.println(Membership.class.cast(objArray[0]));
			System.out.println(Grid.class.cast(objArray[1]));
		}
		
		int nrOfComputers = 0;
		
		for(Object arr : unpaidJobs) {
			
			Object[] objArray = (Object[])arr;
			
			Job job 	= Job.class.cast(objArray[0]);
			Grid grid	= Grid.class.cast(objArray[1]);
			
			nrOfComputers += job.getExecution().getComputerList().size();
			
			BigDecimal discount = BigDecimal.valueOf(100);
			
			if( discounts.containsKey(grid.getGridId()) )
						discount = BigDecimal.valueOf(100)
						   			.subtract( BigDecimal.valueOf( discounts.get(grid.getGridId()) ) );
			
			
			BigDecimal result = computeFee( job, grid )
						.multiply( discount )
						.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			executionCosts = executionCosts.add( result );
			
		}
		
		BigDecimal schedulingCosts = priceManagement.getFee( historicalJobs.size() );
		
		System.out.println("Scheduling costs = "+schedulingCosts.toString());
		
		
		BigDecimal setupCosts = BigDecimal.valueOf(0);
		
		if(unpaidJobs.size() != 0)
			setupCosts = schedulingCosts
						.multiply( BigDecimal.valueOf(unpaidJobs.size()) );
		
		BigDecimal totalCosts = setupCosts.add(executionCosts);
		
		BigDecimal pricePerJob = BigDecimal.valueOf(0);
		
		if(unpaidJobs.size() != 0)
			pricePerJob = totalCosts.divide(
										BigDecimal.valueOf(unpaidJobs.size()), 
										2, 	// scale = number of decimals after decimalpoint
										RoundingMode.HALF_UP
									);
		
		for(Object arr : unpaidJobs) {
				
				Object[] objArray = (Object[])arr;
				
				Job job = Job.class.cast(objArray[0]);
				
				job = entityManager.find(Job.class, job.getJobId());
				job.setPaid(true);
		}
		
		BigDecimal computersPerJob = BigDecimal.valueOf(0);
		
		if(unpaidJobs.size() != 0)
			computersPerJob = BigDecimal.valueOf(nrOfComputers)
										.divide(BigDecimal.valueOf(unpaidJobs.size()), 1, RoundingMode.HALF_EVEN);
		
		return createBill(username, totalCosts, pricePerJob, setupCosts, executionCosts, computersPerJob);
		
	} 
	
	
	private BigDecimal computeFee(Job job, Grid grid) {
		
//		System.out.println("=== JOB EXECUTION TIME ===");
//		System.out.println(job.getExecutionTime());
		
		BigDecimal fee = (BigDecimal.valueOf(job.getExecutionTime())
					.divide(BigDecimal.valueOf(60*1000), 2, RoundingMode.HALF_UP)
					.multiply(grid.getCostsPerCPUMinute())
				);
		
//		System.out.println("=== JOB FEE ===");
//		System.out.println("fee = "+fee+", costsPerCPUMinute = "+grid.getCostsPerCPUMinute());
		
		return fee;
	
	}
	
	
	private String createBill(String username, BigDecimal totalCosts, BigDecimal pricePerJob, 
										BigDecimal setupCosts, BigDecimal executionCosts, BigDecimal computersPerJob) {
		
		return " BILL for User "+username		+": \n\n"+
				" Total price 				: "+totalCosts		+"\n"+
				" Price per Job 				: "+pricePerJob		+"\n"+
				" Setup costs				: "+setupCosts		+"\n"+
				" Execution costs				: "+executionCosts	+"\n"+
				" Computers used per Job 	: "+computersPerJob;
		
	}
	
}
