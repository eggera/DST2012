package dst3.sample;

import org.apache.log4j.Logger;

import dst3.dynload.IPluginExecutable;

public class PluginOne implements IPluginExecutable {

	private static final Logger logger = Logger.getLogger(PluginOne.class);
	
	@Override
	public void execute() {
		
		logger.info("Execute Plugin One");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.warn("plugin interrupted");
		}
		
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
