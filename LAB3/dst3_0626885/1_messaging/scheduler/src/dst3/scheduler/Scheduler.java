package dst3.scheduler;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.log4j.Logger;

public class Scheduler {

	// defs
	
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	
	// deps
	
	@Resource (mappedName = "queue.dst.SchedulerQueue")
	private static ConnectionFactory connectionFactory;
	@Resource (mappedName = "queue.dst.SchedulerQueue")
	private static Queue queue;
	
	
	public void test() {
		logger.debug("just a test!");
	}
	
}
