package dst1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;

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
	
	@OneToOne (cascade = CascadeType.REMOVE)
//	@OnDelete (action = OnDeleteAction.CASCADE)
	private Job job;
	
	public Execution() {
		// used by Hibernate
	}
	
	public Execution(Date start, Date end) {
		this.start = start;
		this.end = end;
		this.status = JobStatus.SCHEDULED;
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
	}
	
	
	public String toString() {
		return "start = "+start+", " +
				"end = "+end+", " +
				"status = "+status;
	}
	
}
