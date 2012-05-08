package dst1;

import dst1.test.*;


public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
		dst01();
		dst02a();
	}

	public static void dst01() {
		
		Test1B.insertEntities();
//		Test1B.test();
		Test.freeResources();
	}

	public static void dst02a() {

		Test2A.insertEntities();
		Test2A.test();
        	Test.freeResources();
        }
}
