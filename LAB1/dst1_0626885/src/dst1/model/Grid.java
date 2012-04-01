package dst1.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class Grid implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long gridId;
	@Column(unique = true)
	private String name;
	private String location;
	private BigDecimal costsPerCPUMinute;
	
	public Grid() {
		// used by Hibernate
	}
	
	public Grid(String name, String location, BigDecimal costsPerCPUMinute) {
		this.name = name;
		this.location = location;
		this.costsPerCPUMinute = costsPerCPUMinute;
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
}
