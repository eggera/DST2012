package dst1.test;

import dst1.mongodb.MongoDBTask;

public class Test5C {

	public static void test() {
			
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 5C  --------------\n\n");
	
		MongoDBTask mongoDBTask = new MongoDBTask();
		mongoDBTask.init();
		
		
		mongoDBTask.freeResources();
		
	}
}
