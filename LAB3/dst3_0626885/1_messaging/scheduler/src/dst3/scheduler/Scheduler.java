package dst3.scheduler;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class Scheduler {

	// defs
	
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	
	// state
	
	private static ConnectionFactory connectionFactory;
	private static Queue sendQueue;
	private static Queue replyQueue;
	private static Queue deniedQueue;
	
	private Connection connection;
	
	
	public void init() throws Exception {
		
		/*
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
		Context jndiContext = null;
		
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			logger.error("jndiContext not obtained, "+e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		/*
		 * Look up connection factory and destination
		 */
		try {
			connectionFactory 	= (ConnectionFactory) jndiContext.lookup("dst.Factory");
			sendQueue 			= (Queue) jndiContext.lookup("queue.dst.SchedulerQueue");
			replyQueue 			= (Queue) jndiContext.lookup("queue.dst.SchedulerReplyQueue");
			deniedQueue			= (Queue) jndiContext.lookup("queue.dst.SchedulerDeniedQueue");
		} catch (NamingException e) {
			logger.error("Naming exception, "+e.getMessage());
			throw new Exception(e);
		}
		
		try {
			connection 						= connectionFactory.createConnection();
			Session session 				= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer 		= session.createProducer(sendQueue);
			MessageConsumer consumer	 	= session.createConsumer(replyQueue);
			MessageConsumer deniedConsumer 	= session.createConsumer(deniedQueue);
			
			
			consumer.setMessageListener(new SchedulerListener());
			
			new Thread(new SchedulerInputThread(session, producer, this)).start();
			new Thread(new SchedulerDeniedThread(deniedConsumer)).start();
			
			connection.start();
		} catch (JMSException e) {
			logger.error("Error when creating JMS artifacts, "+e.getMessage());
			throw new Exception(e);
		}
		
		logger.info("Scheduler initialization done");
		
	}
	
	public void releaseResources() throws JMSException {
		connection.close();
	}
	
}
