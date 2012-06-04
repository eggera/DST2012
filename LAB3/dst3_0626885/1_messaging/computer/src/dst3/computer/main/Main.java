package dst3.computer.main;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import dst3.computer.Computer;

public class Main {

	// defs
	
	private static final Logger logger = Logger.getLogger(Main.class);
	
	// main
	
	public static void main(String[] args) {
		
		if ( args.length != 3 ) {
			logger.error("usage: Computer <name> <clusterName> <complexity>");
			System.exit(1);
		}
		
		try {
			Computer computer = new Computer(args[0],args[1],args[2]);
			computer.init();
			
		} catch (JMSException e) {
			logger.error("Error in initialization procedure, "+e.getMessage());
		} catch (Exception e) {
			logger.error("General error in initialization procedure, "+e.getMessage());
		}
	}
}
