package dst1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@IdClass( value = MembershipKey.class )
@Entity
//@NamedQuery ( name = "discountJobsOnGrid",
//			  query = "")
public class Membership implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne (fetch=FetchType.EAGER)
	private Grid grid;
	@Id
	@ManyToOne (fetch=FetchType.EAGER)
	private User user;
	@Temporal (value = TemporalType.TIMESTAMP)
	private Date registration;
	private Double discount;
	
	public Membership() {
		// used by Hibernate
	}
	
	public Membership(Grid grid, User user) {
		this.grid = grid;
		this.user = user;
	}
	
	public Membership(Grid grid, User user, 
						Date registration, Double discount) {
		
		this(grid, user);
		this.registration = registration;
		this.discount = discount;
	}
	
	/**
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
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
	 * @param grid the grid to set
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	
	/**
	 * String representation of this membership
	 */
	public String toString() {
		return "gridId = "+grid.getGridId()+", " +
				"userId = "+user.getId();
	}
	
	/**
	 * Extended String representation of this membership
	 */
	public String toExtendedString() {
		return "gridId = "+grid.getGridId()+", " +
				"userId = "+user.getId()+", " +
				"registration = "+registration+", " +
				"discount = "+discount;
	}
	
}
