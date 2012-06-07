package dst3.loader.main;

import org.apache.log4j.Logger;

import dst3.loader.thread.PluginLoader;

public class Main {

	// defs
	
	private final static Logger logger = Logger.getLogger(Main.class);
	
	
	public static void main(String[] args) throws InterruptedException {
		
		logger.info("Start listening for new Plugins ...");
		
		new Thread(new PluginLoader()).start();
		
	}
	
}
