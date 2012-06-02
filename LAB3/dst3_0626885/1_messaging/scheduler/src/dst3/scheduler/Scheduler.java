package dst3.scheduler;


import javax.annotation.Resource;
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
	
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	private Thread sendThread;
	
	// deps
	
	@Resource (name = "dst.Factory")
	private static ConnectionFactory connectionFactory;
	@Resource (name = "queue.dst.SchedulerQueue")
	private static Queue sendQueue;
	@Resource (name = "queue.dst.SchedulerReplyQueue")
	private static Queue replyQueue;
	
	
	public Scheduler() throws Exception {
		
	}
	
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
		} catch (NamingException e) {
			logger.error("Naming exception, "+e.getMessage());
			throw new Exception(e);
		}
		
		try {
			connection 	= connectionFactory.createConnection();
			session 	= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer 	= session.createProducer(sendQueue);
			sendThread 	= new Thread(new SchedulerSendThread(session, producer, this));
			
			consumer 	= session.createConsumer(replyQueue);
			consumer.setMessageListener(new SchedulerListener());
			
			connection.start();
		} catch (JMSException e) {
			logger.error("Error when creating JMS artifacts, "+e.getMessage());
			throw new Exception(e);
		}
		
		logger.info("Scheduler initialization done");
		
		sendThread.start();
		
	}
	
	public void releaseResources() throws JMSException {
		connection.close();
	}
	
}
