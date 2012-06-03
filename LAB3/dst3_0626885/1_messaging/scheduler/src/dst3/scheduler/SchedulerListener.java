package dst3.scheduler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;

public class SchedulerListener implements MessageListener {
	
	// defs
	
	private final static Logger logger = Logger.getLogger(SchedulerListener.class);

	@Override
	public void onMessage(Message message) {
	
		logger.debug("on message ...");
		
		TextMessage textMessage = null;
		
		try {
		
			if( TextMessage.class.isInstance(message) ) {
				
				logger.debug("textmessage: ");
				textMessage = TextMessage.class.cast(message);
				logger.debug(textMessage.getText());

			}
			
			else if( StreamMessage.class.isInstance(message) ) {
				
				logger.debug("getting stream message ...");
				StreamMessage streamMsg = StreamMessage.class.cast(message);
				logger.info("Task Id of the newly created Task: "+streamMsg.readLong());
			}
			
			else if( ObjectMessage.class.isInstance(message) ) {
				
				logger.debug("getting object message ..." );
				ObjectMessage objectMsg = ObjectMessage.class.cast(message);
				TaskDTO taskDTO = (TaskDTO) objectMsg.getObject();
				logger.info(taskDTO);
//				logger.info(objectMsg.getObject());
			}
			
			else {
				logger.debug("no textmessage");
			}
		} catch (JMSException e) {
			logger.error("Error in SchedulerListener, "+e.getMessage());
		}
	}

	

}
