package dst1.model;

import java.util.Date;

import javax.persistence.*;


@IdClass( value = MembershipKey.class )
@Entity
public class Membership {
	
	@Id
	private Long gridId;
	@Id
	private Long userId;
	@Temporal (value = TemporalType.TIMESTAMP)
	private Date registration;
	private Double discount;
	
	
	/**
	 * @return the gridId
	 */
	public Long getGridId() {
		return gridId;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @return the registration
	 */
	public Date getRegistration() {
		return registration;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @param registration the registration to set
	 */
	public void setRegistration(Date registration) {
		this.registration = registration;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}
