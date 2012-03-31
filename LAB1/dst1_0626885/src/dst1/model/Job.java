package dst1.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean isPaid;
	
	
	public Job() {
		// used by Hibernate
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param isPaid the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	
	// Derived Properties (calculated from other entities)
	@Transient
	public int getNumCPUs() {
		return -1;
	}
	
	@Transient
	public Integer getExecutionTime() {
		return null;
	}
	
}
