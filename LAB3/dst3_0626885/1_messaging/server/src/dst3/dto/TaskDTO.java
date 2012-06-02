package dst3.dto;

import java.io.Serializable;

import dst3.model.Task.TaskComplexity;
import dst3.model.Task.TaskStatus;


public class TaskDTO implements Serializable {

	private Long id;
	private Long jobId;
	private TaskStatus status;
	private String ratedBy;
	private TaskComplexity complexity;

	
	public TaskDTO() {
		
	}
	
	public TaskDTO(Long id, Long jobId, TaskStatus status, String ratedBy, TaskComplexity complexity) {
		this.id = id;
		this.jobId = jobId;
		this.status = status;
		this.ratedBy = ratedBy;
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
	
	@Override
	public String toString() {
		return "Task Id: "+id+"\n" +
				"Job Id: "+jobId+"\n" +
				"Status: "+status+"\n" +
				"RatedBy: "+ratedBy+"\n" +
				"Complexity: "+complexity;
	}
}
