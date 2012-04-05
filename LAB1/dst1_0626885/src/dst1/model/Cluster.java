package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Cluster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long clusterId;
	@Column(unique = true)
	private String name;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastService;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date nextService;
	
	@ManyToOne
	private Admin admin;
	
	@ManyToOne
	private Grid grid;
	
	@ManyToMany
	@JoinTable (name = "Cluster_Cluster",
				uniqueConstraints = 
					@UniqueConstraint(columnNames = {"super_cluster_id", "sub_cluster_id"}),
				joinColumns = 
					@JoinColumn(name = "super_cluster_id", referencedColumnName="clusterId"),
				inverseJoinColumns = 
					@JoinColumn(name = "sub_cluster_id", referencedColumnName="clusterId"))
	private List<Cluster> subClusters;
	
	@ManyToMany (mappedBy = "subClusters")
	private List<Cluster> superClusters;
	
	@OneToMany (mappedBy = "cluster")
	private List<Computer> computers;
	
	public Cluster() {
		// used by Hibernate
		this.subClusters = new ArrayList<Cluster>();
		this.superClusters = new ArrayList<Cluster>();
		this.computers = new ArrayList<Computer>();
	}
	
	public Cluster(String name, Date lastService, Date nextService) {
		this();
		
		this.name = name;
		this.lastService = lastService;
		this.nextService = nextService;
	}
	
	
	/**
	 * @return the id
	 */
	public Long getClusterId() {
		return clusterId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the lastService
	 */
	public Date getLastService() {
		return lastService;
	}
	/**
	 * @return the nextService
	 */
	public Date getNextService() {
		return nextService;
	}
	/**
	 * @param id the id to set
	 */
	public void setClusterId(Long id) {
		this.clusterId = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param lastService the lastService to set
	 */
	public void setLastService(Date lastService) {
		this.lastService = lastService;
	}
	/**
	 * @param nextService the nextService to set
	 */
	public void setNextService(Date nextService) {
		this.nextService = nextService;
	}
	
	/**
	 * @return the admin
	 */
	public Admin getAdmin() {
		return this.admin;
	}
	
	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	/**
	 * @return the grid
	 */
	public Grid getGrid() {
		return this.grid;
	}
	
	/**
	 * @param grid the grid to set
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * @param cluster the sub-cluster to add
	 */
	public void addSubCluster(Cluster cluster) {
		this.subClusters.add(cluster);
	}
	
	/**
	 * @param cluster the sub-cluster to remove
	 */
	public void removeSubCluster(Cluster cluster) {
		this.subClusters.remove(cluster);
	}
	
	/**
	 * @param cluster the super-cluster to add
	 */
	public void addSuperCluster(Cluster cluster) {
		this.superClusters.add(cluster);
	}
	
	/**
	 * @param cluster the super-cluster to remove
	 */
	public void removeSuperCluster(Cluster cluster) {
		this.superClusters.remove(cluster);
	}
	
	/**
	 * @return the subCluster list
	 */
	public List<Cluster> getSubClusters() {
		return this.subClusters;
	}
	
	/**
	 * @param subCluster the subCluster list to set
	 */
	public void setSubClusters(List<Cluster> subCluster) {
		this.subClusters = subCluster;
	}
	
	/**
	 * @return the superCluster list
	 */
	public List<Cluster> getSuperClusters() {
		return this.superClusters;
	}
	
	/**
	 * @param superCluster the superCluster list to set
	 */
	public void setSuperClusters(List<Cluster> superCluster) {
		this.superClusters = superCluster;
	}
	
	/**
	 * Add a computer
	 * @param computer the computer to add
	 */
	public void addComputer(Computer computer) {
		this.computers.add(computer);
		computer.setCluster(this);
	}
	
	/**
	 * Remove a computer
	 * @param computer the computer to remove
	 */
	public void removeComputer(Computer computer) {
		this.computers.remove(computer);
		computer.setCluster(null);
	}
	
	/**
	 * Gets the list of computers related to this cluster
	 * @return the list of computers
	 */
	public List<Computer> getComputers() {
		return this.computers;
	}
	
	/**
	 * Sets the list of computers
	 * @param computers the computer list to set
	 */
	public void setComputers(List<Computer> newComputers) {		
		this.computers = newComputers;
	}
	
	/**
	 * Gets the String representation of this cluster
	 */
	public String toString() {
		return "clusterId = "+clusterId+", " +
				"name = "+name;
	}
	
	/**
	 * Gets the extended String representation of this cluster
	 */
	public String toExtendedString() {
		return "clusterId = "+clusterId+", " +
				"name = "+name+", " +
				"lastService = "+lastService+", " +
				"nextService = "+nextService;
	}
	
}
