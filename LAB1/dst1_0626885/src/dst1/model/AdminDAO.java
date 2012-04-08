package dst1.model;

import java.util.List;

import javax.persistence.*;

public class AdminDAO {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	public AdminDAO() {
		this.entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	}
	
	public AdminDAO (EntityManager entityManager) {
		this();
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets the entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the only EntityManager for this object
	 */
	public EntityManager getEntityManager() {
		if(entityManager == null)
			this.entityManager = entityManagerFactory.createEntityManager();
		return this.entityManager;
	}
	
	/**
	 * Saves a admin to the current PeristenceContext
	 * @param admin the admin to save
	 */
	public void saveAdmin(Admin admin) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive()) {
			entityManager.persist(admin);
			return;
		}
		
		entityManager.getTransaction().begin();
		entityManager.persist(admin);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Search for and find a admin in the PersistenceContext
	 * @param adminID the admin ID of the admin to find
	 * @return the admin object if found, null otherwise
	 */
	public Admin findAdmin(Long adminID) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive())
			return entityManager.find(Admin.class, adminID);
		
		entityManager.getTransaction().begin();
		Admin admin = entityManager.find(Admin.class, adminID);
		entityManager.getTransaction().commit();
		return admin;
	}
	
	/**
	 * Get all admins currently stored in the persistence context
	 * @return a list of all admins
	 */
	public List<Admin> getAllAdmins() {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive())
			return entityManager.createQuery( "from Admin", Admin.class ).getResultList();
		
		entityManager.getTransaction().begin();
		List<Admin> result = entityManager.createQuery( "from Admin", Admin.class ).getResultList();
		entityManager.getTransaction().commit();
		return result;
	}
	
	/**
	 * Remove a admin from the current PersistenceContext
	 * @param adminId the adminId of the admin to remove
	 */
	public void removeAdmin(Long adminId) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive()) {
			Admin admin_ = entityManager.find(Admin.class, adminId);
			if(admin_ == null)
				return;
			List<Cluster> adminClusterList = admin_.getClusterList();
			for(Cluster cluster : adminClusterList) 
				cluster.setAdmin(null);
					
			entityManager.remove(admin_);
			return;
		}
		
		entityManager.getTransaction().begin();
		Admin admin_ = entityManager.find(Admin.class, adminId);
		if(admin_ == null)
			return;
		List<Cluster> adminClusterList = admin_.getClusterList();
		for(Cluster cluster : adminClusterList) 
			cluster.setAdmin(null);
				
		entityManager.remove(admin_);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Removes all admins from the persistence context
	 */
	public void removeAllAdmins() {
		List<Admin> allAdmins = getAllAdmins();
		for(Admin admin : allAdmins) 
			removeAdmin(admin.getId());
	}
	
	/**
	 * Frees allocated and created resources
	 */
	public void freeResources() {
		if(entityManager != null) {
			entityManager.close();
			entityManager = null;
		}
	}
	
}
