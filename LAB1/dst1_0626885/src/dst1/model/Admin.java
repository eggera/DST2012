package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Admin extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany (mappedBy = "admin")
	private List<Cluster> clusterList;
	
	public Admin() {
		// used by Hibernate
		this.clusterList = new ArrayList<Cluster>();
	}
	
	public Admin(String firstName, String lastName, Address address) {
		super(firstName, lastName, address);
		this.clusterList = new ArrayList<Cluster>();
	}
	
	
	/**
	 * Adds a cluster to the clusterList
	 * @param cluster the cluster to add
	 */
	public void addCluster(Cluster cluster) {
		this.clusterList.add(cluster);
		cluster.setAdmin(this);
	}
	
	/**
	 * Removes a cluster from the clusterList
	 * @param cluster the cluster to remove
	 */
	public void removeCluster(Cluster cluster) {
		this.clusterList.remove(cluster);
		cluster.setAdmin(null);
	}
	
	/**
	 * Get the clusterList
	 * @return the clusterList
	 */
	public List<Cluster> getClusterList() {
		return this.clusterList;
	}
	
	/**
	 * Set the clusterList
	 * @param clusterList the clusterList to set
	 */
	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}
	
	/**
	 * String representation of this Admin
	 */
	public String toString() {
		return "admin id = "+id+", " +
			  "firstName = "+firstName+", " +
			   "lastName = "+lastName;
	}
	
	/**
	 * Extended String representation of this Admin
	 */
	public String toExtendedString() {
		return "admin id = "+id+", " +
			  "firstName = "+firstName+", " +
			   "lastName = "+lastName;
	}
	
	
}
