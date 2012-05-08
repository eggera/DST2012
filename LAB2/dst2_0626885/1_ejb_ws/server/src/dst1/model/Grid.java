package dst1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Grid implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gridId;
	@Column(unique = true)
	private String name;
	private String location;
	private BigDecimal costsPerCPUMinute;
	
	@OneToMany (mappedBy="grid", cascade=CascadeType.ALL)
	private List<Membership> membershipList;
	
	@OneToMany (mappedBy="grid", cascade=CascadeType.REMOVE)
	private List<Cluster> clusterList;
	
	public Grid() {
		// used by Hibernate
		this.membershipList = new ArrayList<Membership>();
		this.clusterList = new ArrayList<Cluster>();
	}
	
	public Grid(String name, String location, BigDecimal costsPerCPUMinute) {
		this.name = name;
		this.location = location;
		this.costsPerCPUMinute = costsPerCPUMinute;
		
		this.membershipList = new ArrayList<Membership>();
		this.clusterList = new ArrayList<Cluster>();
	}
	
	/**
	 * @return the id
	 */
	public Long getGridId() {
		return gridId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @return the costsPerCPUMinute
	 */
	public BigDecimal getCostsPerCPUMinute() {
		return costsPerCPUMinute;
	}
	/**
	 * @param id the id to set
	 */
	public void setGridId(Long id) {
		this.gridId = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @param costsPerCPUMinute the costsPerCPUMinute to set
	 */
	public void setCostsPerCPUMinute(BigDecimal costsPerCPUMinute) {
		this.costsPerCPUMinute = costsPerCPUMinute;
	}
	
	/**
	 * Adds a membership entity to the list
	 * @param membership the membership to set
	 */
	public void addMembership(Membership membership) {
		this.membershipList.add(membership);
	}
	
	/**
	 * Removes a membership entity from the list
	 * @return the membership of this Grid
	 */
	public void removeMembership(Membership membership) {
		this.membershipList.remove(membership);
	}
	
	/**
	 * Get the membershipList
	 * @return the membershipList
	 */
	public List<Membership> getMembershipList() {
		return this.membershipList;
	}
	
	/**
	 * Sets the membershipList
	 * @param membershipList the membershipList to set
	 */
	public void setMembershipList(List<Membership> membershipList) {
		this.membershipList = membershipList;
	}
	
	/**
	 * Adds a cluster to the clusterList
	 * @param cluster the cluster to add
	 */
	public void addCluster(Cluster cluster) {
		this.clusterList.add(cluster);
		cluster.setGrid(this);
	}
	
	/**
	 * Removes a cluster from the clusterList
	 * @param cluster the cluster to remove
	 */
	public void removeCluster(Cluster cluster) {
		this.clusterList.remove(cluster);
		cluster.setGrid(null);
	}
	
	/**
	 * Gets the clusterList
	 * @return the clusterList
	 */
	public List<Cluster> getClusterList() {
		return this.clusterList;
	}
	
	/**
	 * Sets the clusterList
	 * @param clusterList the clusterList to set
	 */
	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}
	
	/**
	 * String representation of this Grid
	 */
	public String toString() {
		return "gridId = "+gridId+", " +
				 "name = "+name;
	}
	
	/**
	 * Extended String representation of this Grid
	 */
	public String toExtendedString() {
		return "gridId = "+gridId+", " +
				 "name = "+name+", " +
			 "location = "+location+", " +
	"costsPerCPUMinute = "+costsPerCPUMinute;
	}
}
