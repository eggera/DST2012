package dst1.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

/**
 * Implementation of a DefaultListener
 * A DefaultListener listens to all specified operations performed anywhere
 * during the program run
 * Since it is globally valid it has to be declared in its own xml file
 * (in this case META-INF/Listener.orm.xml) which in turn has to be mapped
 * in persistence.xml
 *
 */
public class DefaultListener {

	private static int loadOperations;
	private static int updateOperations;
	private static int removeOperations;
	private static int persistOperations;
	
	// saves the persist-time-differences for each Object 
	// (each persist operation)
	private static Map<Object, long[]> persistMap;
	private static long totalPersistTime;
	
	public DefaultListener() {
		persistMap = Collections.synchronizedMap(new HashMap<Object, long[]>());
	}
	
	@PostLoad
	public synchronized void countLoadOperations(Object obj) {
		++loadOperations;
//		System.out.println("entity loaded");
	}
	
	@PostUpdate
	public synchronized void countUpdateOperations(Object obj) {
		++updateOperations;
//		System.out.println("entity updated");
	}
	
	@PostRemove
	public synchronized void countRemoveOperations(Object obj) {
		++removeOperations;
//		System.out.println("entity removed");
	}
	
	@PrePersist
	public synchronized void startPersist(Object obj) {
		// persist time start and end
		long[] persistTime = new long[2];
		persistTime[0] = System.currentTimeMillis();
		persistMap.put(obj, persistTime);
	}
	
	@PostPersist
	public synchronized void endPersist(Object obj) {
		++persistOperations;
		
		// the objects are stored in a map since each object
		// (= operation) is unique and assigned its time values
		if(persistMap.containsKey(obj)) { 
			long[] persist_time = persistMap.get(obj);
			persist_time[1] = System.currentTimeMillis();
			persistMap.put(obj, persist_time);
			totalPersistTime += persist_time[1] - persist_time[0];
		}
	}
	
	public static int getLoadOperations() {
		return loadOperations;
	}
	
	public static int getUpdateOperations() {
		return updateOperations;
	}
	
	public static int getRemoveOperations() {
		return removeOperations;
	}
	
	public static int getPersistOperations() {
		return persistOperations;
	}
	
	public static long getTotalPersistTime() {
		return totalPersistTime;
	}
	
	public static double getAveragePersistTime() {
		if(persistOperations == 0)
			return 0;
		return totalPersistTime / persistOperations;
	}
	
}
