package dst1.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long jobId;
	private boolean isPaid;
	
	@ManyToOne
	private User user;
	
	@OneToOne (cascade = CascadeType.PERSIST)
	private Environment environment;
	
	@OneToOne (cascade = CascadeType.ALL)
	private Execution execution;
	
	
	public Job() {
		// used by Hibernate
	}
	
	public Job(Environment environment) {
		this.environment = environment;
	}
	

	/**
	 * @return the id
	 */
	public Long getJobId() {
		return jobId;
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
	public void setJobId(Long id) {
		this.jobId = id;
	}
	/**
	 * @param isPaid the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}
	
	/**
	 * @return the execution
	 */
	public Execution getExecution() {
		return execution;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	/**
	 * @param execution the execution to set
	 */
	public void setExecution(Execution execution) {
		this.execution = execution;
		this.execution.setJob(this);
	}
	
	// Derived Properties (calculated from other entities)
	
	/**
	 * @return the number of cpus on which this job is executed
	 */
	@Transient
	public int getNumCPUs() {
		return -1;
	}
	
	/**
	 * @return the execution time for this job
	 */
	@Transient
	public Integer getExecutionTime() {
		return null;
	}
	
	/**
	 * String representation of a Job
	 */
	public String toString() {
		return 	"id = "+jobId+", " +
			"isPaid = "+isPaid+", " +
			"userID = "+(user == null?"-":user.getUserId());
	}
	
}
