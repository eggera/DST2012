package dst3.loader.thread;

import java.io.File;
import java.util.Scanner;

import org.apache.log4j.Logger;

import dst3.dynload.IPluginExecutor;
import dst3.dynload.PluginExecutor;

public class PluginLoader implements Runnable {
	
	//defs
	
	private static final Logger logger = Logger.getLogger(PluginLoader.class);

	// state
	
	private Object monitor = new Object();
	
	
	@Override
	public void run() {

		PluginExecutor pluginExecutor = new PluginExecutor();
		
		pluginExecutor.monitor(new File("plugins"));
		
		pluginExecutor.start();
		
		new Thread(userInput).start();
		
		logger.info("Press enter to stop listening");
		
		synchronized(monitor) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				logger.debug("wait interrupted");
			}
		}

		pluginExecutor.stop();
		
		pluginExecutor.releaseResources();
	}
	
	
	public void wakeUp() {		
		synchronized(monitor) {
			this.monitor.notifyAll();
		}
	}
	
	
	private final Runnable userInput = new Runnable() {

		@Override
		public void run() {
			
			Scanner sc = new Scanner(System.in);
			
			sc.nextLine();
			
			wakeUp();
			
		}
		
	};

}

