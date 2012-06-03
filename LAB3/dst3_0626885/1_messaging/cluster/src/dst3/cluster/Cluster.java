package dst3.cluster;


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


public class Cluster {

	// defs
	
	private static final Logger logger = Logger.getLogger(Cluster.class);
	
	// state
	
	private static ConnectionFactory connectionFactory;
	private static Queue recieveQueue;
	private static Queue replyQueue;
	
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	private Thread clusterInputThread;
	
	private String name;
	
	
	public Cluster(String name) {
		this.name = name;
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
			recieveQueue		= (Queue) jndiContext.lookup("queue.dst.ClusterQueue");
			replyQueue 			= (Queue) jndiContext.lookup("queue.dst.ClusterReplyQueue");
		} catch (NamingException e) {
			logger.error("Naming exception, "+e.getMessage());
			throw new Exception(e);
		}
		
		try {
			connection 	= connectionFactory.createConnection();
			session 	= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer 	= session.createConsumer(recieveQueue);
			producer 	= session.createProducer(replyQueue);
			
			clusterInputThread 	= new Thread( new ClusterInputThread(session, producer, consumer, this) );
			clusterInputThread.start();
			
			connection.start();
		} catch (JMSException e) {
			logger.error("Error when creating JMS artifacts, "+e.getMessage());
			throw new Exception(e);
		}
		
		logger.info("Scheduler initialization done");
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void releaseResources() throws JMSException {
		connection.close();
	}
	
}
