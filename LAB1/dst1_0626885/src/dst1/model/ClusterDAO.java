package dst1.model;

import java.util.List;

import javax.persistence.EntityManager;

public class ClusterDAO {

	private EntityManager entityManager;
	
	public ClusterDAO(EntityManager entityManager) {
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
	 * Find a cluster by Id
	 * @param clusterId the clusterId of the cluster to find
	 * @return if found, the cluster, null otherwise
	 */
	public Cluster findCluster(Long clusterId) {
		return entityManager.find(Cluster.class, clusterId);
	}
	
	/**
	 * Saves a cluster to the persistence context
	 * @param cluster the cluster to save
	 * @return true if saved successfully, false otherwise
	 */
	public boolean saveCluster(Cluster cluster) {
		entityManager.persist(cluster);
		return true;
	}
	
	/**
	 * Removes a cluster from the persistence context
	 * @param clusterId the Id of the cluster to remove
	 * @return true if successfully removed, false otherwise
	 */
	public boolean removeCluster(Long clusterId) {
		Cluster cluster_ = entityManager.find(Cluster.class, clusterId);
		if(cluster_ == null)
			return false;
		
		List<Cluster> parentsOfDeleted = cluster_.getSuperClusters();
		List<Cluster> childrenOfDeleted = cluster_.getSubClusters();
		
		for(Cluster parent : parentsOfDeleted) {
			List<Cluster> pChildren = parent.getSubClusters();
			
			for(Cluster child : childrenOfDeleted) {
				// wenn der Supercluster ein Kind des zu löschenden Clusters
				// enthält, soll er es nicht noch einmal hinzufügen
				if(pChildren.contains(child))
					continue;
				
				parent.addSubCluster(child);
				child.addSuperCluster(parent);
			}
			parent.removeSubCluster(cluster_);
		}
		
		for(Cluster child : childrenOfDeleted) 
			child.removeSuperCluster(cluster_);
				
		for(Computer computer : cluster_.getComputers()) 
			computer.setCluster(null);
		cluster_.getAdmin().removeCluster(cluster_);
		cluster_.getGrid().removeCluster(cluster_);
				
		entityManager.remove(cluster_);
		return true;
	}
}
