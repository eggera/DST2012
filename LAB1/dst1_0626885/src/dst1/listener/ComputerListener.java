package dst1.listener;

import java.util.Date;

import javax.persistence.*;

import dst1.model.Computer;

public class ComputerListener {

	@PostPersist
	public void setComputerDates(Computer computer) {
		Date date = new Date(System.currentTimeMillis());
		computer.setCreation(date);
		computer.setLastUpdate(date);
	}
	
	@PreUpdate
	public void setLastUpdate(Computer computer) {
		Date date = new Date(System.currentTimeMillis());
		computer.setLastUpdate(date);
	}
}
