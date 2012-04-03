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
	private List<Cluster> subCluster;
	
	@ManyToMany (mappedBy = "subCluster")
	private List<Cluster> superCluster;
	
	public Cluster() {
		// used by Hibernate
		this.subCluster = new ArrayList<Cluster>();
		this.superCluster = new ArrayList<Cluster>();
	}
	
	public Cluster(String name, Date lastService, Date nextService) {
		this.name = name;
		this.lastService = lastService;
		this.nextService = nextService;
		
		this.subCluster = new ArrayList<Cluster>();
		this.superCluster = new ArrayList<Cluster>();
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
		this.subCluster.add(cluster);
	}
	
	/**
	 * @param cluster the sub-cluster to remove
	 */
	public void removeSubCluster(Cluster cluster) {
		this.subCluster.remove(cluster);
	}
	
	/**
	 * @param cluster the super-cluster to add
	 */
	public void addSuperCluster(Cluster cluster) {
		this.superCluster.add(cluster);
	}
	
	/**
	 * @param cluster the super-cluster to remove
	 */
	public void removeSuperCluster(Cluster cluster) {
		this.superCluster.remove(cluster);
	}
	
	/**
	 * @return the subCluster list
	 */
	public List<Cluster> getSubCluster() {
		return this.subCluster;
	}
	
	/**
	 * @param subCluster the subCluster list to set
	 */
	public void setSubCluster(List<Cluster> subCluster) {
		this.subCluster = subCluster;
	}
	
	/**
	 * @return the superCluster list
	 */
	public List<Cluster> getSuperCluster() {
		return this.superCluster;
	}
	
	/**
	 * @param superCluster the superCluster list to set
	 */
	public void setSuperCluster(List<Cluster> superCluster) {
		this.superCluster = superCluster;
	}
	
}
