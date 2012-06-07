package dst3.sample;

import org.apache.log4j.Logger;

import dst3.dynload.IPluginExecutable;

public class PluginThree implements IPluginExecutable {
	
	private static final Logger logger = Logger.getLogger(PluginThree.class);

	@Override
	public void execute() {
		
		logger.info("Execute Plugin Three");
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			logger.warn("plugin interrupted");
		}
		
	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}
