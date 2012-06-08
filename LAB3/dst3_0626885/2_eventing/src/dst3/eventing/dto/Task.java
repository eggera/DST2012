package dst3.eventing.dto;


public class Task {

	private Long id;
	private Long jobId;
	private TaskStatus status;
	private String ratedBy;
	private TaskComplexity complexity;

	public enum TaskStatus {
		ASSIGNED,
		READY_FOR_PROCESSING,
		PROCESSING_NOT_POSSIBLE,
		PROCESSED
	}
	
	public enum TaskComplexity {
		UNRATED,
		EASY,
		CHALLENGING
	}
	
	
	public Task() {
		
	}
	
	public Task(Long id, Long jobId, TaskStatus status, String ratedBy, TaskComplexity complexity) {
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
	
}
