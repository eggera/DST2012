package dst1.model;

import java.io.Serializable;
import java.util.Date;

public class Computer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long computerId;
	private String name;
	private Integer cpus;
	private String location;
	private Date creation;
	private Date lastUpdate;
	
	
	public Computer() {
		// used by Hibernate
	}
	
	public Computer(String name, Integer cpus, String location,
						Date creation, Date lastUpdate) {
		
		this.name = name;
		this.cpus = cpus;
		this.location = location;
		this.creation = creation;
		this.lastUpdate = lastUpdate;
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
	
	public String toString() {
		return "name = "+ name + ", " +
				"cpus = "+ cpus + ", " +
				"location = "+ location + ", " +
				"creation = "+ creation + ", " +
				"lastUpdate = "+ lastUpdate;
	}
}
