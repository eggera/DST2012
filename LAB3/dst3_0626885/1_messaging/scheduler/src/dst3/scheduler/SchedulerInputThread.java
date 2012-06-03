package dst3.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.log4j.Logger;

public class SchedulerInputThread implements Runnable {

	// defs
	
	private final static Logger logger = Logger.getLogger(SchedulerInputThread.class);
	
	public enum Command { ASSIGN, INFO, STOP, UNKNOWN };
	
	// state
	
	private Session session;
	private MessageProducer producer;
	private Scheduler scheduler;
	
	private boolean stop;
	
	
	public SchedulerInputThread(Session session, MessageProducer producer, Scheduler scheduler) {
		this.session = session;
		this.producer = producer;
		this.scheduler = scheduler;
	}
	

	@Override
	public void run() {
		
		String userInput = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(!stop) {
			
			logger.debug("read user input: ");
			
			try {
				userInput = in.readLine();
			} catch (IOException e) {
				logger.error("User Input could not be read, "+e.getMessage());
				continue;
			}
			
			logger.debug("processing user input ...");
				
			Command command = getUserCommand(userInput);
			
			switch(command) {
			case ASSIGN:	sendAssignRequest(userInput);
							break;
			case INFO:		sendInfoRequest(userInput);
							break;
			case STOP:		stop();
							break;
			case UNKNOWN:	unknown();
							break;
			}
			
		}
		logger.info("Exit Program");
		
		try {
			scheduler.releaseResources();
		} catch (JMSException e) {
			logger.error("Failed to release resources, "+e.getMessage());
		}
		
		System.exit(0);
		
	}
	
	private void sendAssignRequest(String userInput) {
		String[] arr = userInput.split(" ");
		
		if( arr.length < 2 ) {
			logger.warn("Please provide a job id");
			return;
		}
		
		long jobId = 0l;
		try {
			jobId = Long.parseLong(arr[1]);

		} catch(NumberFormatException nfe) {
			logger.warn("assign: No valid jobId");
			return;
		}
		
		try {
			MapMessage mapMsg = session.createMapMessage();
			mapMsg.setLong("jobId", jobId);
			mapMsg.setBooleanProperty("assign", true);
			
			logger.debug("sending assign request ...");
			producer.send(mapMsg);
			logger.debug("assign request sent");
		} catch (JMSException e) {
			logger.error("Could not send assign command, "+e.getMessage());
		}
	
	}
	
	private void sendInfoRequest(String userInput) {
		String[] arr = userInput.split(" ");
		
		if( arr.length < 2 ) {
			logger.warn("Please provide a task id");
			return;
		}
		
		long taskId = 0l;
		try {
			taskId = Long.parseLong(arr[1]);

		} catch(NumberFormatException nfe) {
			logger.warn("assign: No valid taskId");
			return;
		}
		try {
			MapMessage mapMsg = session.createMapMessage();
			mapMsg.setLong("taskId", taskId);
			mapMsg.setBooleanProperty("info", true);
			
			logger.debug("sending info request ... ");
			producer.send(mapMsg);
			logger.debug("info request sent");
		} catch (JMSException e) {
			logger.error("Could not send info command, "+e.getMessage());
		}
	
	}
	
	private void stop() {
		logger.debug("stopping thread ...");
		stop = true;
	}
	
	private void unknown() {
		logger.warn("Command unknown");
	}
	
	
	private Command getUserCommand(String userInput) {
		if(userInput.startsWith("assign"))
			return Command.ASSIGN;
			
		if( userInput.startsWith("info"))
			return Command.INFO;
		
		if( userInput.startsWith("stop"))
			return Command.STOP;
		
		return Command.UNKNOWN;
	}

}
