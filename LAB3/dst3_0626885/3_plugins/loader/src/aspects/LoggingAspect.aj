package aspects;

import dst3.dynload.IPluginExecutable;

public aspect LoggingAspect {

	pointcut logExecute():
		call (void IPluginExecutable.execute());
	
	before(): logExecute() {
		System.out.println("before execute");
	}
	
	after(): logExecute() {
		System.out.println("after execute");
	}
		
}
