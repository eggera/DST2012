package dst2.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestingClient {

	private Testing testingBean;
	
	private Context ctx;
	
	
	public TestingClient() {
		
		System.out.println("TestingClient constructor");
		try {
			ctx = new InitialContext();
			testingBean = (Testing) ctx.lookup("java:global/dst2_1/TestingBean");
		} catch (NamingException e) {
			System.err.println("Creating initial context exception: "+e.getMessage());
		}
	}

	
	public void saveEntities() {
		System.out.println("Saving entities ...");
		testingBean.saveEntities();
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Client Bean Test\n\n");
		
		String entity = "my entity";
		
		System.out.println("Save "+entity);
		
		TestingClient testingClient = new TestingClient();
		
		testingClient.saveEntities();
	}
}
