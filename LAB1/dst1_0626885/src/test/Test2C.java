package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import dst1.model.Environment;
import dst1.model.Job;
import dst1.model.PersistenceUtil;
import dst1.model.User;
import dst1.query.CriteriaQueries;

public class Test2C {

	/**
	 * Test code for assignment 2C
	 */
	public static void test() {

		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2C  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		
		CriteriaQueries criteriaQueries = new CriteriaQueries(entityManager);
		List<Job> jobList = criteriaQueries.findJobs("usr1", "workflow2");
		
		for(Job j : jobList)
			System.out.println(j.toExtendedString());
	
				
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
