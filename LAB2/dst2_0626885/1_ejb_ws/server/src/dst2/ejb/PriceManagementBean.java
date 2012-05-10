package dst2.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.ejb.model.PriceStep;


@Startup
@Singleton
public class PriceManagementBean implements PriceManagement {
	
	// state
	
	private List<PriceStep> prices;
	
	// deps
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@PostConstruct
	public void init() {
		updatePrices();
	}
	
	
	@Override
	public void setPrice( Integer numberOfHistoricalJobs, BigDecimal price ) {
		
		entityManager.persist(new PriceStep(numberOfHistoricalJobs, price));
		updatePrices();
	}
	
	
	@Override
	@Lock (LockType.READ)
	public BigDecimal getFee( Integer numberOfHistoricalJobs ) {
		
		BigDecimal price = null;
		for(PriceStep priceStep : prices) {
			if( priceStep.getNumberOfHistoricalJobs() > numberOfHistoricalJobs )
				return priceStep.getPrice();
			price = priceStep.getPrice();
		}
		return price;
	}
	
	
	@Override
	public List<PriceStep> getAllPrices() {
		return entityManager.createQuery(
							"SELECT p FROM PriceStep p ORDER BY p.numberOfHistoricalJobs ASC", 
							PriceStep.class)
						.getResultList();
	}
	
	/**
	 * Update all in-memory prices
	 */
	private void updatePrices() {
		prices = getAllPrices();
	}
	
}
