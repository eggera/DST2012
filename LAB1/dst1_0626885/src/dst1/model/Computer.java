package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@NamedQueries({
		@NamedQuery (name = "getViennaComputers", 
					 query = "select distinct c " +
							 "		from Computer c left join fetch c.executionList" +
							 "		where c.location like 'AUT-VIE%'"),
		})
public class Computer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long computerId;
	private String name;
	private Integer cpus;
	private String location;
	private Date creation;
	private Date lastUpdate;
	
	private Cluster cluster;
	
	private List<Execution> executionList;
	private Long totalUsage;
	
	public Computer() {
		// used by Hibernate
		this.executionList = new ArrayList<Execution>();
	}
	
	public Computer(String name, Integer cpus, String location,
						Date creation, Date lastUpdate) {
		
		this.name = name;
		this.cpus = cpus;
		this.location = location;
		this.creation = creation;
		this.lastUpdate = lastUpdate;
		
		this.executionList = new ArrayList<Execution>();
	}
	
	
	/**
	 * @return the id
	 */
	public Long getComputerId() {
		return computerId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the cpus
	 */
	public Integer getCpus() {
		return cpus;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @return the creation
	 */
	public Date getCreation() {
		return creation;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param id the id to set
	 */
	public void setComputerId(Long id) {
		this.computerId = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param cpus the cpus to set
	 */
	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @param creation the creation to set
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the cluster
	 */
	public Cluster getCluster() {
		return this.cluster;
	}
	
	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	/**
	 * Adds an execution to the list
	 * @param execution the execution to add
	 */
	public void addExecution(Execution execution) {
		this.executionList.add(execution);
	}
	
	/**
	 * Removes an execution from the list
	 * @param execution the execution to remove
	 */
	public void removeExecution(Execution execution) {
		this.executionList.remove(execution);
	}
	
	/**
	 * Gets the execution list of this computer
	 * @return the execution list
	 */
	public List<Execution> getExecutionList() {
		return this.executionList;
	}
	
	/**
	 * Sets the execution list
	 * @param list the execution list to set
	 */
	public void setExecutionList(List<Execution> list) {
		this.executionList = list;
	}
	
	/**
	 * Gets the total usage of this computer, which comprises all execution times
	 * on this computer
	 * @return the total usage of this computer
	 */
	public Long getTotalUsage() {
		return this.totalUsage;
	}
	
	/**
	 * Sets the total usage (total time of execution) of this computer
	 * @param usage the usage of this computer
	 */
	public void setTotalUsage(Long usage) {
		this.totalUsage = usage;
	}
	
	/**
	 * String representation of this computer
	 */
	public String toString() {
		return  "id = "+ computerId + ", " +
				"name = "+ name;
	}
	
	/**
	 * Extended String representation of this computer
	 */
	public String toExtendedString() {
		return  "id = "+ computerId + ", " +
				"name = "+ name + ", " +
				"cpus = "+ cpus + ", " +
				"clusterId = "+ (cluster == null ? null : cluster.getClusterId());
//				"location = "+ location + ", " +
//				"creation = "+ creation + ", " +
//				"lastUpdate = "+ lastUpdate;
	}
}
