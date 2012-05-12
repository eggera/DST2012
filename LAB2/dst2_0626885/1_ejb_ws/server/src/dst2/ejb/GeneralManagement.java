package dst2.ejb;

import java.math.BigDecimal;

import javax.ejb.Remote;

import dst2.ejb.exception.NoOpenBillsException;


@Remote
public interface GeneralManagement {
	
	void setPrice( Integer numberOfHistoricalJobs, BigDecimal price );
	
	String getTotalBillFor(String username) throws NoOpenBillsException;
	
}
