package dst2.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import dst2.model.PriceStep;

@Remote
public interface PriceManagement {

	void setPrice(Integer numberOfHistoricalJobs, BigDecimal price);
	
	BigDecimal getFee(Integer numberOfHistoricalJobs);
	
	List<PriceStep> getAllPrices();
}
