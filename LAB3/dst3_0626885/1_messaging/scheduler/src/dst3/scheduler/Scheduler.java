package dst3.scheduler;


import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class Scheduler implements MessageListener {

	// defs
	
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	
	// state
	
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	// deps
	
	@Resource (name = "dst.Factory")
	private static ConnectionFactory connectionFactory;
	@Resource (name = "queue.dst.SchedulerQueue")
	private static Queue sendQueue;
	@Resource (name = "queue.dst.SchedulerReplyQueue")
	private static Queue replyQueue;
	
	
	public Scheduler() throws Exception {
		init();
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
			connectionFactory = (ConnectionFactory) jndiContext.lookup("dst.Factory");
			sendQueue = (Queue) jndiContext.lookup("queue.dst.SchedulerQueue");
			replyQueue = (Queue) jndiContext.lookup("queue.dst.SchedulerReplyQueue");
		} catch (NamingException e) {
			logger.error("Naming exception, "+e.getMessage());
			throw new Exception(e);
		}
		
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(sendQueue);
			
			consumer = session.createConsumer(replyQueue);
			consumer.setMessageListener(this);
			
			connection.start();
		} catch (JMSException e) {
			logger.error("Error when creating JMS artifacts, "+e.getMessage());
			throw new Exception(e);
		}
		
		logger.info("Scheduler initialization done");
		
	}
	
	
	public void sendMessage(String message) throws JMSException {
		Message msg = session.createTextMessage(message);
		
		if( logger.isDebugEnabled() )
			logger.debug("sending message ... ");
		producer.send(msg);
		if( logger.isDebugEnabled() )
			logger.debug("sending message done ");
	}
	
	
	public void test() {
		logger.debug("just a test!");
	}
	
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
			else {
				logger.debug("no textmessage");
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseResources() throws JMSException {
		connection.close();
	}
	
}
