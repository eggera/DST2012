package dst1.model;

import java.io.Serializable;

public final class MembershipKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Grid grid;
	private User user;
	
	public MembershipKey() {
		// used by Hibernate
	}
	
	public MembershipKey(Grid grid, User user) {
		this.grid = grid;
		this.user = user;
	}
	
//	/**
//	 * Get the grid
//	 */
//	public Grid getGrid() {
//		return this.grid;
//	}
//	
//	/**
//	 * Get the user
//	 */
//	public User getUser() {
//		return this.user;
//	}
//	
//	/**
//	 * Set the grid
//	 */
//	public void setGrid(Grid grid) {
//		this.grid = grid;
//	}
//	
//	/**
//	 * Set the user
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	@Override
	public boolean equals(Object otherObj) {
		if(this == otherObj) 
			return true;
		
		if(otherObj instanceof MembershipKey == false)
			return false;
		
		MembershipKey other = (MembershipKey) otherObj;
		return (
				grid.getGridId() == null 
							? other.grid.getGridId() == null 
							: grid.getGridId().equals(other.grid.getGridId())
					&&
				user.getId() == null 
							? other.user.getId() == null 
							: user.getId().equals(other.user.getId())
			   );				
				
	}
	
	@Override
	public int hashCode() {
		return (
				(grid.getGridId() == null ? 0 : grid.getGridId().hashCode())
						^
				(user.getId() == null ? 0 : user.getId().hashCode())
			   );
	}
	
	/**
	 * Get the String representation of this primary key class
	 */
	@Override
	public String toString() {
		return "gridId = "+grid.getGridId()+", userId = "+user.getId();
	}
}
