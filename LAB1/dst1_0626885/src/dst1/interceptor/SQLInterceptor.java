package dst1.interceptor;

import org.hibernate.EmptyInterceptor;

public class SQLInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 3894614218727237142L;

	private static int countSQLSelects;
	
	public String onPrepareStatement(String sql) {
		
		if(sql.startsWith("select")) {
			++countSQLSelects;
//			System.out.println("select: "+sql);
		}
		return sql;
	}
	
	public static int getSelectCount() {
		return countSQLSelects;
	}
	
	public static void resetSelectCount() {
		countSQLSelects = 0;
	}
	
	public static void printSelectCount() {
		System.out.println("Counted select statements for Computers and Executions: "+countSQLSelects);
	}

}
