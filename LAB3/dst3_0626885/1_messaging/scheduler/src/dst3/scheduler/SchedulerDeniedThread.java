package dst3.scheduler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;

public class SchedulerDeniedThread implements Runnable {

	// defs
	
	private static final Logger logger = Logger.getLogger(SchedulerDeniedThread.class);

	// state
	
	private MessageConsumer consumer;
	
	
	public SchedulerDeniedThread(MessageConsumer consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void run() {
		
		ObjectMessage message;
		
		while(true) {
			
			try {
				Message msg = consumer.receive();
				
				if( !ObjectMessage.class.isInstance(msg) ) {
					logger.debug("Exiting ...");
					break;
				}
				
				message = ObjectMessage.class.cast(msg);
				
				printMessage(message);
				
			} catch (JMSException e) {
				logger.warn("Error in consumer recieve, "+e.getMessage());
			}
		}
		
	}
	
	public void printMessage(ObjectMessage message) throws JMSException {
		TaskDTO taskDTO = (TaskDTO) message.getObject();
		
		logger.info("This task has been denied: ");
		logger.info(taskDTO);
	}

}
