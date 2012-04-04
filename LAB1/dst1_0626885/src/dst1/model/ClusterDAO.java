package dst1.model;

import javax.persistence.EntityManager;

public class ClusterDAO {

	private EntityManager entityManager;
	
	public ClusterDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
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
		
		entityManager.remove(cluster_);
		return true;
	}
}
