package dst1.model;

import java.util.List;

import javax.persistence.*;

public class MembershipDAO {

	private EntityManager entityManager;
	
	public MembershipDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the entityManager for this DAO
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	/**
	 * Sets the entityManager for this DAO
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Get all managed membership entities
	 * @return a list of membership objects
	 */
	public List<Membership> getAllMemberships() {
		return entityManager.createQuery("from Membership", Membership.class).getResultList();
	}
	
	/**
	 * Saves a membership to the persistence context
	 * @param membership the membership to save
	 * @return true if saved successfully, false otherwise
	 */
	public boolean saveMembership(Membership membership) {
		entityManager.persist(membership);
		return true;
	}
	
}
