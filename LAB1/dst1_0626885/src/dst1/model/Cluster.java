package dst1.model;

import java.io.Serializable;
import java.util.Date;

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
	
	@ManyToOne (optional = false)
	private Admin admin;
	
	public Cluster() {
		// used by Hibernate
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
	
}
