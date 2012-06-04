package dst3.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.log4j.Logger;

public class SchedulerInputThread implements Runnable {

	// defs
	
	private final static Logger logger = Logger.getLogger(SchedulerInputThread.class);
	
	public enum Command { ASSIGN, INFO, STOP, INVALID };
	
	// state
	
	private Session session;
	private MessageProducer producer;
	private Scheduler scheduler;
	
	private boolean stop;
	
	private List<String> arguments = new ArrayList<String>();
	
	
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
			
			logger.info("Input command (assign|info|stop) ");
			
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
			case INVALID:	invalid();
							break;
			}
			
		}
		logger.info("Exit program");
		
		try {
			scheduler.releaseResources();
		} catch (JMSException e) {
			logger.error("Failed to release resources, "+e.getMessage());
		}
		
		System.exit(0);
		
	}
	
	private void sendAssignRequest(String userInput) {
		
		try {
			MapMessage mapMsg = session.createMapMessage();
			mapMsg.setLong("jobId", Long.parseLong(arguments.remove(0)));
			mapMsg.setBooleanProperty("assign", true);
			
			logger.debug("sending assign request ...");
			producer.send(mapMsg);
			logger.debug("assign request sent");
		} catch (JMSException e) {
			logger.error("Could not send assign command, "+e.getMessage());
		}
	
	}
	
	private void sendInfoRequest(String userInput) {
		
		try {
			MapMessage mapMsg = session.createMapMessage();
			mapMsg.setLong("taskId", Long.parseLong(arguments.remove(0)));
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
	
	private void invalid() {
		logger.info("Command invalid");
	}
	
	
	private Command getUserCommand(String userInput) {
		String[] input = userInput.split(" ");
		
		if(userInput.startsWith("assign")) {
			
			if( input.length != 2 ) {
				logger.info("Please provide a job id");
				return Command.INVALID;
			}
			
			try {
				Long.parseLong(input[1]);

			} catch(NumberFormatException nfe) {
				logger.warn("assign: Not a valid jobId");
				return Command.INVALID;
			}
			arguments.add(input[1]);
			
			return Command.ASSIGN;
		}
			
		if( userInput.startsWith("info")) {
			
			if( input.length < 2 ) {
				logger.info("Please provide a task id");
				return Command.INVALID;
			}
			
			try {
				Long.parseLong(input[1]);

			} catch(NumberFormatException nfe) {
				logger.warn("assign: No valid taskId");
				return Command.INVALID;
			}
			arguments.add(input[1]);
			
			return Command.INFO;
		}
		
		if( userInput.startsWith("stop"))
			return Command.STOP;
		
		return Command.INVALID;
	}

}
