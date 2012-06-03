package dst3.cluster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;
import dst3.dto.TaskDTO.TaskDTOComplexity;

public class ClusterInputThread implements Runnable {

	// defs
	
	private final static Logger logger = Logger.getLogger(ClusterInputThread.class);
	
	public enum Command { ACCEPT, DENY, STOP, UNKNOWN };
	
	// state
	
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	private Session session;
	private MessageProducer producer;
	private Cluster cluster;
	private ClusterQueueThread queueThread;
	
	private boolean stop;
	
	
	public ClusterInputThread(Session session, MessageProducer producer, MessageConsumer consumer, Cluster cluster) {
		this.session 			= session;
		this.producer 			= producer;
		this.cluster 			= cluster;
		
		this.queueThread 		= new ClusterQueueThread(consumer);
	}
	

	@Override
	public void run() {
		
		String userInput = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		TaskDTO taskDTO = null;
		
		executorService.execute( queueThread );
		
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
			
			if( command.equals(Command.STOP) ) {
				stop();
				break;
			}
			
			else if( command.equals(Command.UNKNOWN) ) {
				unknown();
				continue;
			}
			
			else if( queueThread.messageAvailable() ) {
				try {
					taskDTO = TaskDTO.class.cast(queueThread.getMessage().getObject());
					
					switch(command) {
					case ACCEPT:	sendAccept(taskDTO, userInput);
									break;
					case DENY:		sendDeny(taskDTO);
									break;
					}
					
					queueThread.wakeUp();
					
				} catch (JMSException e) {
					logger.error("Error when retrieving taskDTO, "+e.getMessage());
				}
			
			}
			else {
				logger.info("No tasks delivered");				
			}
			
		}
		logger.info("Exit program");
		
		try {
			cluster.releaseResources();
		} catch (JMSException e) {
			logger.error("Failed to release resources, "+e.getMessage());
		}
		
		System.exit(0);
		
	}
	
	private void sendAccept(TaskDTO taskDTO, String userInput) {
		String[] input = userInput.split(" ");
		
		if( input.length != 2 ) {
			logger.warn("Please provide a Task Complexity");
			return;
		}
		
		taskDTO.setStatus(	TaskDTO.TaskDTOStatus.READY_FOR_PROCESSING );
		taskDTO.setRatedBy(	cluster.getName() );
		taskDTO.setComplexity( TaskDTO.TaskDTOComplexity.valueOf(input[1]) );
		
		try {
			ObjectMessage objectMsg = session.createObjectMessage(taskDTO);
			
			objectMsg.setStringProperty("type", "accept");
			
			logger.debug("sending accept ...");
			producer.send(objectMsg);
			logger.debug("accept sent");
		} catch (JMSException e) {
			logger.error("Could not send accept, "+e.getMessage());
		}
	
	}
	
	private void sendDeny(TaskDTO taskDTO) {

		taskDTO.setStatus(	TaskDTO.TaskDTOStatus.PROCESSING_NOT_POSSIBLE );
		taskDTO.setRatedBy(	cluster.getName() );
		
		try {

			ObjectMessage objectMsg = session.createObjectMessage(taskDTO);
			
			objectMsg.setStringProperty("type", "deny");
			
			logger.debug("sending deny ...");
			producer.send(objectMsg);
			logger.debug("deny sent");
		} catch (JMSException e) {
			logger.error("Could not send deny, "+e.getMessage());
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
		String[] input = userInput.split(" ");
		
		if( userInput.startsWith("accept") ) {
			if( input.length != 2 ) {
				logger.warn("Please provide a Task Complexity");
				return Command.UNKNOWN;
			}
			try {
				TaskDTOComplexity.valueOf(TaskDTOComplexity.class, input[1]);
			} catch(IllegalArgumentException e) {
				logger.warn("no valid task complexity given");
				return Command.UNKNOWN;
			}
			return Command.ACCEPT;
		}
			
		if( userInput.startsWith("deny") )
			return Command.DENY;
		
		if( userInput.startsWith("stop") )
			return Command.STOP;
		
		return Command.UNKNOWN;
	}

}
