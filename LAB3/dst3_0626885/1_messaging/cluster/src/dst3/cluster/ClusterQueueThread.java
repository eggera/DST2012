package dst3.cluster;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class ClusterQueueThread implements Runnable {

	// defs
	
	private static final Logger logger = Logger.getLogger(ClusterQueueThread.class);

	// state
	
	private MessageConsumer consumer;
	private ObjectMessage message;
	
	private Object monitor = new Object();
	
	
	public ClusterQueueThread(MessageConsumer consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			message = null;
			
			try {
				Message msg = consumer.receive();
				
				if( !ObjectMessage.class.isInstance(msg) ) {
					logger.debug("Exiting ...");
					break;
				}
				
				message = ObjectMessage.class.cast(msg);
				
				synchronized(monitor) {
					try {
						logger.info("Task to rate: "+message.getObject());
						
						monitor.wait();
					} catch (InterruptedException e) {
						// exit synchronized block
						logger.debug("interrupted - message delivered");
					}
				}
				
			} catch (JMSException e) {
				logger.debug("Exiting queue thread");
				break;
			}
		}
		
	}
	
	public boolean messageAvailable() {
		return message != null;
	}
	
	public ObjectMessage getMessage() {
		return message;
	}
	
	public void wakeUp() {
		synchronized(monitor) {
			monitor.notifyAll();
		}
	}

}
