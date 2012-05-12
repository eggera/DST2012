package dst2.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.Execution;
import dst1.model.Execution.JobStatus;
import dst1.service.Service;


@Startup
@Singleton
public class TimerServiceBean {

	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private TimerService timerService;
	
	
	@PostConstruct
	public void init() {
		setTimer(1000*10);
	}
	
	public void setTimer(int intervalDuration) {
	        
	   	 	timerService.createIntervalTimer(intervalDuration, intervalDuration, 
	   	 									new TimerConfig("jobFinishTimer", true));
	}
	 
	
//	@Schedule(second = "*", info = "jobFinishTimer")
	@Timeout
	public void finishJobs() {
		
		List<Execution> executionList = entityManager.createQuery("" +
				"SELECT e FROM Execution e " +
				"WHERE e.end IS NULL", Execution.class)
				.getResultList();

		for(Execution execution : executionList) {
			execution.setEnd(Service.getCurrentDate());
			execution.setStatus(JobStatus.FINISHED);
		}
		
	}
	
}
