package dst2.ejb;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.ejb.Remote;

import dst2.ejb.exception.NoOpenBillsException;


@Remote
public interface GeneralManagement {
	
	void setPrice( Integer numberOfHistoricalJobs, BigDecimal price );
	
	Future<String> getTotalBillFor(String username) throws NoOpenBillsException;
	
}
