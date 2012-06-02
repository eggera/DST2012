package dst3.main;

import javax.jms.JMSException;

import dst3.scheduler.Scheduler;

public class Main {

	public static void main(String[] args) {
		
		
		try {
			Scheduler scheduler = new Scheduler();
			scheduler.init();
//			scheduler.processUserInput();
//			scheduler.releaseResources();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
