package dst3.computer;

import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;

public class ComputerListener implements MessageListener {

	// defs
	
	private final static Logger logger = Logger.getLogger(ComputerListener.class);
	
	// state
	
	private List<TaskDTO> taskList = new ArrayList<TaskDTO>();
	
	
	@Override
	public void onMessage(Message msg) {
		
		try {
			if( ObjectMessage.class.isInstance(msg) ) {
				ObjectMessage message = ObjectMessage.class.cast(msg);
				
				TaskDTO taskDTO = (TaskDTO) message.getObject();
				
				logger.debug("Property ratedBy = "+taskDTO.getRatedBy());
				logger.debug("Property complexity = "+taskDTO.getComplexity());
				
				logger.info("Task assigned: "+taskDTO);
				
				taskList.add(taskDTO);
			}
		} catch(JMSException e) {
			logger.error("Error in computer onMessage, "+e.getMessage());
		}
		
	}
	
	
	public boolean taskAvailable(long id) {
		return isValidId(id);
	}
	
	public TaskDTO takeTask(long id) {
		for(TaskDTO task : taskList) {
			if(task.getId() == id) 
				return taskList.remove(taskList.indexOf(task));
		}
		return null;
	}
	
	public boolean isValidId(long id) {
		for(TaskDTO task : taskList) {
			if(task.getId() == id) 
				return true;
		}
		return false;
	}

}
