package dst2.ejb;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GeneralManagementBean implements GeneralManagement {

	// deps
	
	@EJB private PriceManagement priceManagement;

	@Override
	public void setPrice(Integer numberOfHistoricalJobs, BigDecimal price) {
		
		priceManagement.setPrice(numberOfHistoricalJobs, price);
	} 
	
	
	
}
