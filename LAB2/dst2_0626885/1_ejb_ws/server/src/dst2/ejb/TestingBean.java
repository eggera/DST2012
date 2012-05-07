package dst2.ejb;

import javax.ejb.Stateful;


@Stateful
public class TestingBean implements Testing {

	public String saveEntities(String entity) {
		
		System.out.println("Saving entities ...");
		
		return "this is a test!";
	}
	
}
