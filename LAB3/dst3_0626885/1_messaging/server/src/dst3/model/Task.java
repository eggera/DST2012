package dst3.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Task implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	private Long jobId;
	@Enumerated (EnumType.STRING)
	private TaskStatus status;
	private String ratedBy;
	@Enumerated (EnumType.STRING)
	private TaskComplexity complexity;
	
	
	public Task() {
		// used by the persistence provider, eclipselink
	}
	
	public Task(Long jobId, TaskStatus status, TaskComplexity complexity) {
		this.jobId = jobId;
		this.status = status;
		this.complexity = complexity;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public TaskStatus getStatus() {
		return status;
	}
	
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	public String getRatedBy() {
		return ratedBy;
	}
	
	public void setRatedBy(String ratedBy) {
		this.ratedBy = ratedBy;
	}
	
	public TaskComplexity getComplexity() {
		return complexity;
	}
	
	public void setComplexity(TaskComplexity complexity) {
		this.complexity = complexity;
	}
	
}
