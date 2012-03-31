package dst1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Execution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date start;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date end;
	
	public enum JobStatus { SCHEDULED, RUNNING, FAILED, FINISHED }
	
	@Enumerated (EnumType.ORDINAL)
	private JobStatus status;
	
	public Execution() {
		// used by Hibernate
	}
	
	public Execution(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	public Execution(Date start, Date end, JobStatus status) {
		this(start, end);
		this.status = status;
	}
	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	
}
