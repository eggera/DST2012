package dst1;

import dst1.test.*;


public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
		dst01();
		dst02a();
		dst02b();
//		dst02c();
//		dst03();
//		dst04a();
//		dst04b();
//		dst04c();
		dst04d();
//		dst05a();
//		dst05b();
//		dst05c();
	}

	public static void dst01() {
		
		Test1B.insertEntities();
		Test1B.test();
		Test.freeResources();
	}

	public static void dst02a() {

		Test2A.insertEntities();
		Test2A.test();
	}

	public static void dst02b() {
		
		Test2B.test();
	}

	public static void dst02c() {
		
		Test2C.test();
	}

	public static void dst03() {
		
		Test3.test();
	}

	public static void dst04a() {
		
		Test4A.test();
	}

	public static void dst04b() {

		Test4B.test();
	}

	public static void dst04c() {

		Test4C.test();
	}

	public static void dst04d() {

		Test4D.test();
	}

        public static void dst05a() {

        	Test5A.test();
        }

        public static void dst05b() {

        	Test5B.test();
        }

        public static void dst05c() {
        	
        	Test5C.test();
        	Test.freeResources();
        }
}
