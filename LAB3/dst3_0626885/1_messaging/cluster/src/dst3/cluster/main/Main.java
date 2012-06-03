package dst3.cluster.main;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import dst3.cluster.Cluster;

public class Main {
	
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		
		if( args.length != 1 ) {
			logger.error("The unique name of the cluster must be given as argument");
			System.exit(1);
		}
		
		try {
			Cluster cluster = new Cluster(args[0]);
			cluster.init();
			
		} catch (JMSException e) {
			logger.error("Error in initializiation procedure, "+e.getMessage());
		} catch (Exception e) {
			logger.error("General Error in main, "+e.getMessage());
		}
		
	}
	
}
