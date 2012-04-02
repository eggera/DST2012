package dst1.model;

import java.io.Serializable;

import javax.persistence.*;


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
				user.getUserId() == null 
							? other.user.getUserId() == null 
							: user.getUserId().equals(other.user.getUserId())
			   );				
				
	}
	
	@Override
	public int hashCode() {
		return (
				(grid.getGridId() == null ? 0 : grid.getGridId().hashCode())
						^
				(user.getUserId() == null ? 0 : user.getUserId().hashCode())
			   );
	}
	
	/*
	@Override
	public boolean equals(Object otherObj) {
		if(this == otherObj) 
			return true;
		
		if(otherObj instanceof MembershipKey == false)
			return false;
		
		MembershipKey other = (MembershipKey) otherObj;
		return (
				gridId == null ? other.gridId == null : gridId.equals(other.gridId)
					&&
				userId == null ? other.userId == null : userId.equals(other.userId)
			   );				
				
	}
	
	@Override
	public int hashCode() {
		return (
				(gridId == null ? 0 : gridId.hashCode())
						^
				(userId == null ? 0 : userId.hashCode())
			   );
	}
	*/
	/**
	 * Get the String representation of this primary key class
	 */
	public String toString() {
		return "gridId = "+grid.getGridId()+", userId = "+user.getUserId();
	}
}
