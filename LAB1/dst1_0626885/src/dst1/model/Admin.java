package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Admin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminId;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	
	@OneToMany (mappedBy = "admin")
	private List<Cluster> clusterList;
	
	public Admin() {
		// used by Hibernate
		this.clusterList = new ArrayList<Cluster>();
	}
	
	public Admin(String firstName, String lastName, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		
		this.clusterList = new ArrayList<Cluster>();
	}
	
	
	/**
	 * @return the id
	 */
	public Long getAdminId() {
		return adminId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param id the id to set
	 */
	public void setAdminId(Long id) {
		this.adminId = id;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
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
		return "admin id = "+adminId+", " +
			  "firstName = "+firstName+", " +
			   "lastName = "+lastName;
	}
	
	/**
	 * Extended String representation of this Admin
	 */
	public String toExtendedString() {
		return "admin id = "+adminId+", " +
			  "firstName = "+firstName+", " +
			   "lastName = "+lastName;
	}
	
	
}
