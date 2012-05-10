package dst2.ejb.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@SuppressWarnings("serial")
@Entity
public class PriceStep implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer numberOfHistoricalJobs;
	private BigDecimal price;
	
	
	public PriceStep() {
		
	}
	
	public PriceStep(Integer numberOfHistoricalJobs, BigDecimal price) {
		this.numberOfHistoricalJobs = numberOfHistoricalJobs;
		this.price = price;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the numberOfHistoricalJobs
	 */
	public Integer getNumberOfHistoricalJobs() {
		return numberOfHistoricalJobs;
	}
	
	/**
	 * @param numberOfHistoricalJobs the numberOfHistoricalJobs to set
	 */
	public void setNumberOfHistoricalJobs(Integer numberOfHistoricalJobs) {
		this.numberOfHistoricalJobs = numberOfHistoricalJobs;
	}
	
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
