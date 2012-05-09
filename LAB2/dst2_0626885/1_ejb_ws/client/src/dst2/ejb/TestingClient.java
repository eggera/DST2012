package dst2.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dst2.exception.LoginFailedException;

public class TestingClient {

	// deps
	
	private Testing testingBean;
	private JobManagement jobManagementBean;
	
	private Context ctx;
	
	
	public TestingClient() {
		
		System.out.println("TestingClient constructor");
		try {
			ctx = new InitialContext();
			testingBean = (Testing) ctx.lookup("java:global/dst2_1/TestingBean");
			jobManagementBean = (JobManagement) ctx.lookup("java:global/dst2_1/JobManagementBean");
		} catch (NamingException e) {
			System.err.println("Creating initial context exception: "+e.getMessage());
		}
	}

	
	public void saveEntities() {
		System.out.println("Saving entities ...");
		testingBean.saveEntities();
	}
	
	
	public void login( String username, String password ) {
		try {
			jobManagementBean.login(username, password);
			System.out.println("Login successful");
		} catch (LoginFailedException e) {
			System.err.println("Login failed: "+e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Client Bean Test\n\n");
		
		String entity = "my entity";
		
		System.out.println("Save "+entity);
		
		TestingClient testingClient = new TestingClient();
		
		testingClient.saveEntities();
		
		testingClient.login("usr1", "usr1");
	}
}
