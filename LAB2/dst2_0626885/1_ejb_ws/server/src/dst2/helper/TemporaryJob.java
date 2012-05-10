package dst2.helper;

import java.util.ArrayList;
import java.util.List;

import dst1.model.Computer;



public class TemporaryJob {

	private Long gridId;
	private int numCPUs;
	private String workflow;
	private List<String> params;
	
	private List<Computer> assignedComputers = new ArrayList<Computer>();

	public TemporaryJob() {
		
	}
	
	public TemporaryJob(Long gridId, int numCPUs, String workflow, List<String> params) {
		this.gridId = gridId;
		this.numCPUs = numCPUs;
		this.workflow = workflow;
		this.params = params;
	}
	
	/**
	 * @return the gridId
	 */
	public Long getGridId() {
		return gridId;
	}

	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}

	/**
	 * @return the numCPUs
	 */
	public int getNumCPUs() {
		return numCPUs;
	}

	/**
	 * @param numCPUs the numCPUs to set
	 */
	public void setNumCPUs(int numCPUs) {
		this.numCPUs = numCPUs;
	}

	/**
	 * @return the workflow
	 */
	public String getWorkflow() {
		return workflow;
	}

	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return the params
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	/**
	 * @param computer the computer to add
	 */
	public void addAssignedComputer(Computer computer) {
		this.assignedComputers.add(computer);
	}
	
	/**
	 * @param computer the computer to remove
	 */
	public void removeAssignedComputer(Computer computer) {
		this.assignedComputers.remove(computer);
	}

	/**
	 * @return the assignedComputers
	 */
	public List<Computer> getAssignedComputers() {
		return assignedComputers;
	}

	/**
	 * @param assignedComputers the assignedComputers to set
	 */
	public void setAssignedComputers(List<Computer> assignedComputers) {
		this.assignedComputers = assignedComputers;
	}
	
}
