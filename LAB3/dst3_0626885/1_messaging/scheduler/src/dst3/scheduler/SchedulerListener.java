package dst3.scheduler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;

public class SchedulerListener implements MessageListener {
	
	// defs
	
	private final static Logger logger = Logger.getLogger(SchedulerListener.class);

	@Override
	public void onMessage(Message message) {
	
		logger.debug("on message ...");
		
		try {
		
			if( ObjectMessage.class.isInstance(message) ) {
				
				ObjectMessage objectMsg = ObjectMessage.class.cast(message);
				
				if( objectMsg.getStringProperty("type").equals("assignConfirm") ) {					
					TaskDTO taskDTO = (TaskDTO) objectMsg.getObject();
					logger.info("assign confirm for jobId "+ taskDTO.getJobId() +": "+taskDTO.getId());
				}
				
				if( objectMsg.getStringProperty("type").equals("infoReply") ) {				
					TaskDTO taskDTO = (TaskDTO) objectMsg.getObject();
					logger.info("info reply: "+taskDTO);
				}
				
				else if( objectMsg.getStringProperty("type").equals("denied") ) {
					TaskDTO taskDTO = (TaskDTO) objectMsg.getObject();
					
					logger.info("task denied: "+taskDTO);
				}
				
				else if( objectMsg.getStringProperty("type").equals("processed") ) {
					TaskDTO taskDTO = (TaskDTO) objectMsg.getObject();
					
					logger.info("task processed: "+taskDTO);
				}
				
			}
			
			else {
				logger.debug("other message type: "+message.getClass().getName());
			}
		} catch (JMSException e) {
			logger.error("Error in SchedulerListener, "+e.getMessage());
		}
	}

}
