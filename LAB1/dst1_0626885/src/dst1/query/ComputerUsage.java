package dst1.query;

import dst1.model.Computer;

public class ComputerUsage {

	private Computer computer;
	private Long totalUsage;
	
	public ComputerUsage() {
		
	}
	
	public ComputerUsage(Computer computer, Long totalUsage) {
		this.computer = computer;
		this.totalUsage = totalUsage;
	}
	
	/**
	 * @return the computer
	 */
	public Computer getComputer() {
		return computer;
	}

	/**
	 * @return the totalUsage
	 */
	public Long getTotalUsage() {
		return totalUsage;
	}

	/**
	 * @param computer the computer to set
	 */
	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	/**
	 * @param totalUsage the totalUsage to set
	 */
	public void setTotalUsage(Long totalUsage) {
		this.totalUsage = totalUsage;
	}
	
	
}
