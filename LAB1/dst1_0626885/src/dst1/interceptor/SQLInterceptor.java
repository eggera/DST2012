package dst1.interceptor;

import org.hibernate.EmptyInterceptor;

public class SQLInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 3894614218727237142L;

	private static int countSQLSelects;
	
	@Override
	public String onPrepareStatement(String sql) {
		
		// only count select statements on computers or executions
		if(sql.startsWith("select")  &&  (sql.contains("computer0_")  ||
				 						  sql.contains("execution0_"))) {
			++countSQLSelects;
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
