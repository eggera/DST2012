package dst1.test;

import dst1.mongodb.MongoDBUtil;

public class Test5A {

	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 5A  --------------\n\n");
	
		MongoDBUtil mongoDBUtil = new MongoDBUtil();
		
		mongoDBUtil.dropDB();
		mongoDBUtil.init();
		mongoDBUtil.insert();
//		mongoDBUtil.findFirst();
//		mongoDBUtil.findAll();
//		mongoDBUtil.basicQuery();
		mongoDBUtil.anotherQuery();
		mongoDBUtil.freeResources();
	}
}
