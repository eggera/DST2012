package dst1.model;

import java.io.Serializable;

public final class MembershipKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long grid;
	private Long user;
	
	public MembershipKey() {
		// used by Hibernate
	}
	
	public MembershipKey(Long gridId, Long userId) {
		this.grid = gridId;
		this.user = userId;
	}
	
	/**
	 * Get the grid
	 */
	public Long getGrid() {
		return this.grid;
	}
	
	/**
	 * Get the user
	 */
	public Long getUser() {
		return this.user;
	}
	
	/**
	 * Set the grid
	 */
	public void setGrid(Long grid) {
		this.grid = grid;
	}
	
	/**
	 * Set the user
	 */
	public void setUser(Long user) {
		this.user = user;
	}
	
	
	@Override
	public boolean equals(Object otherObj) {
		if(this == otherObj) 
			return true;
		
		if(otherObj instanceof MembershipKey == false)
			return false;
		
		MembershipKey other = (MembershipKey) otherObj;
		return (
				grid == null 
							? other.grid == null 
							: grid.equals(other.grid)
					&&
				user == null 
							? other.user == null 
							: user.equals(other.user)
			   );				
				
	}
	
	@Override
	public int hashCode() {
		return (
				(grid == null ? 0 : grid.hashCode())
						^
				(user == null ? 0 : user.hashCode())
			   );
	}
	
	/**
	 * Get the String representation of this primary key class
	 */
	public String toString() {
		return "gridId = "+grid+", userId = "+user;
	}
}
