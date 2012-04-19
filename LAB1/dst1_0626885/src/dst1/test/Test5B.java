package dst1.test;

import dst1.mongodb.MongoDBTask;

public class Test5B {

	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 5B  --------------\n\n");

		MongoDBTask mongoDBTask = new MongoDBTask();
		mongoDBTask.init();
		
		long jobId = 2;
		System.out.println("Getting document for job with id "+jobId);
		mongoDBTask.getDocumentForJob(jobId);
		
		System.out.println("\nGetting documents last updated after timestamp 1325397600");
		mongoDBTask.getLastUpdatedAfter(1325397600L);
		
		mongoDBTask.freeResources();
		
	}
}
