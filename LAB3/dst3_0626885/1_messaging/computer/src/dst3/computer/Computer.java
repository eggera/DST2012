package dst3.computer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class Computer {

	// defs
	
	private static final Logger logger = Logger.getLogger(Computer.class);
	
	// resources
	
	private static ConnectionFactory connectionFactory;
	
	private Connection connection;
	private Topic computerTopic;
	private Queue replyQueue;
	
	// state
	
	private String name;
	private String cluster;
	private String complexity;
	
	
	public Computer(String name, String cluster, String complexity) {
		this.name 			= name;
		this.cluster 		= cluster;
		this.complexity 	= complexity;
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
			computerTopic		= (Topic) jndiContext.lookup("topic.dst.ClusterComputerTopic");
			replyQueue			= (Queue) jndiContext.lookup("queue.dst.ComputerReplyQueue");

		} catch (NamingException e) {
			logger.error("Naming exception, "+e.getMessage());
			throw new Exception(e);
		}
		
		try {
			
			connection 						= connectionFactory.createConnection();
			connection.setClientID( name );
			
			Session session 				= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer 	 	= session.createProducer(replyQueue);
			MessageConsumer consumer	 	= session.createDurableSubscriber(computerTopic, name, "ratedBy = '"+cluster+"' AND complexity = '"+complexity+"'", false);
			
			new Thread(new ComputerInputThread(session, producer, consumer, this)).start();
			
			connection.start();
		} catch (JMSException e) {
			logger.error("Error when creating JMS artifacts, "+e.getMessage());
			throw new Exception(e);
		}
		
		logger.info("Computer initialization done");
		
	}
	
	public void releaseResources() throws JMSException {
		connection.close();
	}
	
}
