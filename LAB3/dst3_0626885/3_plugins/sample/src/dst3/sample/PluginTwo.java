package dst3.sample;

import org.apache.log4j.Logger;

import dst3.dynload.IPluginExecutable;

public class PluginTwo implements IPluginExecutable {

	private static final Logger logger = Logger.getLogger(PluginTwo.class);
	
	@Override
	public void execute() {
		
		logger.info("Execute Plugin Two");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.warn("plugin interrupted");
		}
		
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}
