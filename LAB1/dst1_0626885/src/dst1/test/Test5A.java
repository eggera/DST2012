package dst1.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dst1.model.Job;
import dst1.model.PersistenceUtil;
import dst1.mongodb.MongoDBTask;
import dst1.query.CriteriaQueries;

public class Test5A {

	public static void test() {
		
		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 5A  --------------\n\n");
	
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		CriteriaQueries criteriaQueries = new CriteriaQueries(entityManager);
		List<Job> finishedJobs = criteriaQueries.findJobsByStatusAndDate(null, null);
		
		MongoDBTask mongoDBTask = new MongoDBTask();
		
		mongoDBTask.dropDB();
		mongoDBTask.init();
		
		for(int workflow = 1; workflow <= 5; workflow++) {
			Job job = finishedJobs.get(workflow-1);
			mongoDBTask.createWorkflow(job.getJobId(), workflow);
		}
		
		mongoDBTask.createWorkflowIndex();
		mongoDBTask.printAllWorkflows();
		
		mongoDBTask.freeResources();
	}
}
