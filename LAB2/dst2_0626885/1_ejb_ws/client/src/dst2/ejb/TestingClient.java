package dst2.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dst2.ejb.exception.JobAssignmentException;
import dst2.ejb.exception.LoginFailedException;
import dst2.ejb.exception.NoOpenBillsException;
import dst2.ejb.exception.NotLoggedInException;

public class TestingClient {

	// deps
	
	private Testing testingBean;
	private PriceManagement priceManagementBean;
	private JobManagement jobManagementBean;
	private GeneralManagement generalManagementBean;

	
	private Context ctx;
	
	
	public TestingClient() {
		
		System.out.println("TestingClient constructor");
		try {
			ctx = new InitialContext();
			testingBean = (Testing) ctx.lookup("java:global/dst2_1/TestingBean");
			priceManagementBean = (PriceManagement) ctx.lookup("java:global/dst2_1/PriceManagementBean");
			generalManagementBean = (GeneralManagement) ctx.lookup("java:global/dst2_1/GeneralManagementBean");
//			jobManagementBean = (JobManagement) ctx.lookup("java:global/dst2_1/JobManagementBean");
			getJobManagementBean();
		} catch (NamingException e) {
			System.err.println("Creating initial context exception: "+e.getMessage());
		}
	}

	
	public void saveEntities() {
		System.out.println("Saving entities ...");
		testingBean.saveEntities();
	}
	
	
	public void getJobManagementBean() {
		try {
			jobManagementBean = JobManagement.class.cast(ctx.lookup("java:global/dst2_1/JobManagementBean"));
		} catch (NamingException e) {
			System.err.println("Creating initial context exception: "+e.getMessage());
		}
	}
	
	public void setPrices() {
		priceManagementBean.setPrice( 100, new BigDecimal(30));
		priceManagementBean.setPrice( 1000, new BigDecimal(15));
		priceManagementBean.setPrice( 5000, new BigDecimal(5));
		priceManagementBean.setPrice( 10000, new BigDecimal(1));
	}
	
	
	public Future<String> getTotalBillFor(String username) throws NoOpenBillsException {
		return generalManagementBean.getTotalBillFor(username);
	}
	
	
	public void login( String username, String password ) {
		try {
			jobManagementBean.login(username, password);
			System.out.println("Login successful");
		} catch (LoginFailedException e) {
			System.err.println("Login failed: "+e.getMessage());
		}
	}
	
	public void addJobToList(Long gridId, int numCPUs, String workflow, List<String> params) {
		try {
			jobManagementBean.addJobToList(gridId, numCPUs, workflow, params);
			System.out.println("Adding job successful");
		} catch (JobAssignmentException e) {
			System.err.println("Job not assigned: "+e.getMessage());
		}
	}
	
	public void submitJobList() {
		try {
			jobManagementBean.submitJobList();
			System.out.println("Successfully submitted job list");
		} catch (JobAssignmentException e) {
			System.err.println("Job list not submitted: "+e.getMessage());
		} catch (NotLoggedInException e) {
			System.err.println("Must be logged in: "+e.getMessage());
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Client Bean Test\n\n");
		
		String entity = "my entity";
		System.out.println("Save "+entity);
		TestingClient testingClient = new TestingClient();
		testingClient.saveEntities();
		
		System.out.println("Set prices ...");
		testingClient.setPrices();
		
		List<String> params = new ArrayList<String>();
		params.add("param__1");
		params.add("param__2");
		
		List<String> params2 = new ArrayList<String>();
		params2.add("param__3");
		params2.add("param__4");
		
		
		while(true) {
			
			testingClient.getJobManagementBean();
			
			testingClient.login("usr1", "usr1");
			
			testingClient.addJobToList(6L, 8, "workflow2", params);
			Thread.sleep(2000);
				
			testingClient.addJobToList(6L, 8, "workflow3", params2);
			Thread.sleep(2000);
	
			testingClient.addJobToList(7L, 4, "workflow4", params);
			Thread.sleep(2000);
			
			
			try {
				Future<String> totalBill = testingClient.getTotalBillFor("usr1");
				
				while(!totalBill.isDone());
				System.out.println(totalBill.get());
				
//				System.out.println("Total bill for user usr: "+testingClient.getTotalBillFor("usr1").get());
			} catch (NoOpenBillsException e) {
				System.err.println(e.getMessage());
			} catch (ExecutionException e) {
				System.err.println(e.getMessage());
			}

			
			testingClient.submitJobList();
			
		}
		
	}
}
