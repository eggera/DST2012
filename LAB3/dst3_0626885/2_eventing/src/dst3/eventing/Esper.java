package dst3.eventing;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import dst3.eventing.dto.Task;

public class Esper {

	
	public void init() {
		
		EPServiceProvider serviceProvider = EPServiceProviderManager.getProvider("DST_EsperSP");
		EPAdministrator admin = serviceProvider.getEPAdministrator();
		
		Task task = new Task();
		
		EPStatement secRecurTrigger = admin.createPattern("every timer:at(*, *, *, *, *, */10)");

		EPStatement countStmt = admin.createEPL("select count(*) from MarketDataBean.win:time(60 sec)");

//		String eventName = ServiceMeasurement.class.getName();
//
//		EPStatement myTrigger = admin.createEPL("select * from pattern [" +
//		  "every (spike=" + eventName + "(latency>20000) or error=" + eventName + "(success=false))]");
	}
	
	
	
	public void releaseResources() {
		EPServiceProviderManager.getProvider("DST_EsperSP").destroy();
	}
	
}
