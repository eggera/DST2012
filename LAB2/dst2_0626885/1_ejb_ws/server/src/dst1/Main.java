package dst1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import dst1.model.Grid;
import dst1.model.Job;
import dst1.test.*;


public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
//		dst01();
//		dst02a();
		
		testBigDecimal();
		
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
	
	
	public static void testBigDecimal() {
		
		double discounts = 10;
		
		BigDecimal discount = BigDecimal.valueOf(100)
	   			.subtract( BigDecimal.valueOf( discounts ) );

		System.out.println("discount = "+discount);

		
		BigDecimal eleven = BigDecimal.valueOf(0.11);
		BigDecimal result = eleven.divide(BigDecimal.valueOf(60), 10, RoundingMode.HALF_UP);
		
		System.out.println("eleven = "+eleven);
		System.out.println("eleven scale = "+eleven.scale());
		System.out.println("eleven precision = "+eleven.precision());
		System.out.println("result of division = "+result);
		
		BigDecimal integer = new BigDecimal(BigInteger.valueOf(4000), 10);
		System.out.println("integer = "+integer);
		
		BigDecimal result2 = eleven.multiply(BigDecimal.valueOf(5));
		System.out.println("result of multiplication = "+result2);
		
		BigDecimal execution = BigDecimal.valueOf(0);
		execution = execution.add(BigDecimal.valueOf(1.12));
		
		System.out.println("execution = "+execution);

//		BigDecimal result = computeFee( job, grid )
//			.multiply( discount )
//			.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//		executionCosts.add( result );
		
	}
	
	
	public static BigDecimal computeFee(Job job, Grid grid) {
		
		return (BigDecimal.valueOf( 60000/*job.getExecutionTime()*/)
					.divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP)
					.multiply(grid.getCostsPerCPUMinute()
								.divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP) )
				);
	
	}
}
