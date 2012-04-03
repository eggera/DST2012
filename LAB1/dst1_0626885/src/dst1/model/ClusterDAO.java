package dst1.model;

import javax.persistence.EntityManager;

public class ClusterDAO {

	private EntityManager entityManager;
	
	public ClusterDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void removeCluster(Cluster cluster_) {
		entityManager.remove(cluster_);
	}
}
