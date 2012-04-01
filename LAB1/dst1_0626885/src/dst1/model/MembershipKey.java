package dst1.model;

import java.io.Serializable;

import javax.persistence.*;


public final class MembershipKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long gridId;
	private Long userId;
	
	public MembershipKey() {
		// used by Hibernate
	}
	
	public MembershipKey(Long gridId, Long userId) {
		this.gridId = gridId;
		this.userId = userId;
	}
	
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
	
	/**
	 * Get the String representation of this primary key class
	 */
	public String toString() {
		return "gridId = "+gridId+", userId = "+userId;
	}
}
