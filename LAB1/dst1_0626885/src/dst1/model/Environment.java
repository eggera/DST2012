package dst1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
public class Environment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Long environmentId;
	private String workflow;
	@ElementCollection
	private List<String> params;
	
	
	public Environment() {
		// used by Hibernate
	}
	
	public Environment(String workflow, List<String> params) {
		this.workflow = workflow;
		this.params = params;
	}
	
	/**
	 * @return the id
	 */
	public Long getEnvironmentId() {
		return environmentId;
	}
	/**
	 * @return the workflow
	 */
	public String getWorkflow() {
		return workflow;
	}
	/**
	 * @return the params
	 */
	public List<String> getParams() {
		return params;
	}
	/**
	 * @param id the id to set
	 */
	public void setEnvironmentId(Long id) {
		this.environmentId = id;
	}
	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	/**
	 * Returns a String representation of this Environment
	 */
	@Override
	public String toString() {
		String result = "workflow = "+workflow+"\nparams: ";
		for(int i = 0; i < params.size(); i++) {
			result += "\n		"+(i+1)+" = "+params.get(i);
		}
		return result;
	}
	
}
