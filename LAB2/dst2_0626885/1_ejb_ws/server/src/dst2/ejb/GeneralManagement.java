package dst2.ejb;

import java.math.BigDecimal;

import javax.ejb.Remote;


@Remote
public interface GeneralManagement {
	
	void setPrice( Integer numberOfHistoricalJobs, BigDecimal price );
	
}
