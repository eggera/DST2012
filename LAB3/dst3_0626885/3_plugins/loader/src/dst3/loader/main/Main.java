package dst3.loader.main;

import java.io.File;

import dst3.dynload.IPluginExecutor;
import dst3.dynload.PluginExecutor;

public class Main {

	
	public static void main(String[] args) throws InterruptedException {
		
		
		IPluginExecutor pluginExecutor = new PluginExecutor();
		
//		System.out.println("java.library.path = "+System.getProperty("java.library.path"));
//		System.out.println("user.dir = "+System.getProperty("user.dir"));
		
		pluginExecutor.monitor(new File("plugins"));
		
		pluginExecutor.start();
		
		Thread.sleep(100000);
		
		pluginExecutor.stop();
		
//		Thread.sleep(5000);
		
		
		
	}
	
}
