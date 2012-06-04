package dst3.computer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Logger;

import dst3.dto.TaskDTO;

public class ComputerInputThread implements Runnable {

	// defs
	
	private final static Logger logger = Logger.getLogger(ComputerInputThread.class);
	
	public enum Command { PROCESSED, STOP, INVALID };
	
	// state
	
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private Computer computer;
	private ComputerListener computerListener;
	
	private boolean stop;
	private List<String> arguments = new ArrayList<String>();
	
	
	public ComputerInputThread(Session session, MessageProducer producer, MessageConsumer consumer, Computer computer) {
		this.session 	= session;
		this.producer 	= producer;
		this.consumer 	= consumer;
		this.computer 	= computer;
	}
	
	@Override
	public void run() {
		
		String userInput = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		computerListener = new ComputerListener();
		try {
			consumer.setMessageListener(computerListener);
		} catch (JMSException e) {
			logger.error("when setting computer listener, "+e.getMessage());
			return;
		}
		
		while(!stop) {

			try {
				
				logger.info("Input command (processed|stop) ");
				
				userInput = in.readLine();
				
				logger.debug("processing user input ...");
				
				Command command = getUserCommand(userInput);
				
				if( command.equals(Command.STOP) ) {
					stop();
					break;
				}
				
				else if( command.equals(Command.INVALID) ) {
					invalid();
					continue;
				}
				
				else if( command.equals(Command.PROCESSED) ) {
					
					long taskId = Long.parseLong(arguments.remove(0));
					
					if( computerListener.taskAvailable(taskId) ) {

						TaskDTO taskDTO = computerListener.takeTask(taskId);
						
						try {
							sendProcessed(taskDTO);
						} catch (JMSException e) {
							logger.error("sending of processed message failed");
						}
					}
				
				}
				else {
					logger.info("No tasks available");				
				}

			} catch (IOException e) {
				logger.error("User Input could not be read, "+e.getMessage());
			}

		}
		logger.info("Exit Program");
		
		try {
			computer.releaseResources();
		} catch (JMSException e) {
			logger.error("Failed to release resources, "+e.getMessage());
		} catch (Exception e) {
			logger.fatal("Unrecognized exception, "+e.getMessage());
		}
		
		System.exit(0);
		
	}
	
	private void sendProcessed(TaskDTO taskDTO) throws JMSException {
		
		taskDTO.setStatus(TaskDTO.TaskDTOStatus.PROCESSED);
		ObjectMessage message = session.createObjectMessage(taskDTO);
		message.setStringProperty("type", "processed");
		producer.send(message);
	}
	
	private void stop() {
		logger.debug("stopping thread ...");
		stop = true;
	}
	
	private void invalid() {
		logger.warn("Command invalid");
	}
	
	private Command getUserCommand(String userInput) {
		String[] input = userInput.split(" ");
		
		if( userInput.startsWith("processed") ) {
			if( input.length != 2 ) {
				logger.warn("Please provide a Task id");
				return Command.INVALID;
			}

			try {
				long taskId = Long.parseLong(input[1]);
				if( !computerListener.isValidId(taskId) ) 
					throw new IllegalArgumentException();
				
				arguments.add(input[1]);

			} catch(IllegalArgumentException e) {
				logger.warn("No task with taskId "+input[1]);
				return Command.INVALID;
			}
			return Command.PROCESSED;
		}
		
		if( userInput.startsWith("stop") )
			return Command.STOP;
		
		return Command.INVALID;
	}

}
