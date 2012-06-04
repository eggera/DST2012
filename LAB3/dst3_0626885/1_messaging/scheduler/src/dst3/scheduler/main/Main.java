package dst3.scheduler.main;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import dst3.scheduler.Scheduler;

public class Main {
	
	// defs
	
	private final static Logger logger = Logger.getLogger(Main.class);

	// main
	
	public static void main(String[] args) {		
		
		try {
			Scheduler scheduler = new Scheduler();
			scheduler.init();
			
		} catch (JMSException e) {
			logger.error("Error in initializiation procedure, "+e.getMessage());
		} catch (Exception e) {
			logger.error("General Error in main, "+e.getMessage());
		}
		
	}
	
}
