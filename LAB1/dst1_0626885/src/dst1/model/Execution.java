package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Execution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long executionId;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date start;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date end;
	
	public enum JobStatus { SCHEDULED, RUNNING, FAILED, FINISHED }
	@Enumerated (EnumType.ORDINAL)
	private JobStatus status;
	
	@OneToOne (fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Job job;
	
	
	@ManyToMany /*(mappedBy="executionList")*/
	@JoinTable (
			uniqueConstraints =
					@UniqueConstraint(columnNames = {"executionId", "computerId"}),
			joinColumns = 
					@JoinColumn (name="executionId", referencedColumnName="executionId"), 
			inverseJoinColumns = 
					@JoinColumn (name="computerId", referencedColumnName="computerId"))
	private List<Computer> computerList;
	
	
	public Execution() {
		// used by Hibernate
		computerList = new ArrayList<Computer>();
	}
	
	public Execution(Date start, Date end) {
		this.start = start;
		this.end = end;
		this.status = JobStatus.SCHEDULED;
		
		computerList = new ArrayList<Computer>();
	}
	
	public Execution(Date start, Date end, JobStatus status) {
		this(start, end);
		this.status = status;
	}
	
	

	/**
	 * @return the id
	 */
	public Long getExecutionId() {
		return executionId;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @return the status
	 */
	public JobStatus getStatus() {
		return status;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setExecutionId(Long id) {
		this.executionId = id;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	
	/**
	 * @param job the job to set
	 */
	public void setJob(Job job) {
		this.job = job;
		if(job.getExecution() != this)
			job.setExecution(this);
	}
	
	/**
	 * Adds a computer to the computer list
	 * @param computer the computer to add
	 */
	public void addComputer(Computer computer) {
		this.computerList.add(computer);
	}
	
	/**
	 * Removes a computer from the computer list
	 * @param computer the computer to remove
	 */
	public void removeComputer(Computer computer) {
		this.computerList.remove(computer);
	}
	
	/**
	 * Gets the computer list of this execution
	 * @return the computer list
	 */
	public List<Computer> getComputerList() {
		return this.computerList;
	}
	
	/**
	 * Sets the computer list of this execution
	 * @param list the computer list to set
	 */
	public void setComputerList(List<Computer> list) {
		this.computerList = list;
	}
	
	/**
	 * String representation of this Execution
	 */
	public String toString() {
		return "executionId = "+executionId+", "+
					"status = "+status;
	}
	
	/**
	 * Extended String representation of this Execution
	 */
	public String toExtendedString() {
		return "executionId = "+executionId+", "+
					 "start = "+start+", " +
					   "end = "+end+", " +
					"status = "+status;
	}
	
}
