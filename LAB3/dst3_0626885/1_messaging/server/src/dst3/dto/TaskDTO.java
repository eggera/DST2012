package dst3.dto;

import java.io.Serializable;


public class TaskDTO implements Serializable {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -2886453366333509552L;
	private Long id;
	private Long jobId;
	private TaskDTOStatus status;
	private String ratedBy;
	private TaskDTOComplexity complexity;

	public enum TaskDTOStatus {
		ASSIGNED,
		READY_FOR_PROCESSING,
		PROCESSING_NOT_POSSIBLE,
		PROCESSED
	}
	
	public enum TaskDTOComplexity {
		UNRATED,
		EASY,
		CHALLENGING
	}
	
	
	public TaskDTO() {
		
	}
	
	public TaskDTO(Long id, Long jobId, TaskDTOStatus status, String ratedBy, TaskDTOComplexity complexity) {
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
	
	public TaskDTOStatus getStatus() {
		return status;
	}
	
	public void setStatus(TaskDTOStatus status) {
		this.status = status;
	}
	
	public String getRatedBy() {
		return ratedBy;
	}
	
	public void setRatedBy(String ratedBy) {
		this.ratedBy = ratedBy;
	}
	
	public TaskDTOComplexity getComplexity() {
		return complexity;
	}
	
	public void setComplexity(TaskDTOComplexity complexity) {
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
