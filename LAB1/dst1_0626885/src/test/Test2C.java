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

import dst1.model.Job;
import dst1.model.PersistenceUtil;
import dst1.model.User;

public class Test2C {

	/**
	 * Test code for assignment 2C
	 */
	public static void test() {

		System.out.println("\n\n------------  TESTING CODE ASSIGNMENT 2C  --------------\n\n");
		
		
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Metamodel m = entityManager.getMetamodel();
		EntityType<Job> Job_ = m.entity(Job.class);
		EntityType<User> User_ = m.entity(User.class);
		
		CriteriaQuery<Job> cq = criteriaBuilder.createQuery(Job.class);
//		Root<User> user = cq.from(User.class);
		
//		Join<User, Job> jobResult = user.join(User_.)
		
		Root<Job> job = cq.from(Job.class);
		
//		Job_ = job.getModel();
//		Join<Job, User> user = job.join(Job_.getAttribute("jobId"));
//		SingularAttribute sa = job....
		cq.select(job);
//		criteriaQuery.where(criteriaBuilder.equal(job.get(job_.))
		
		TypedQuery<Job> q = entityManager.createQuery(cq);
		List<Job> jobList = q.getResultList();
		
		for(Job job__ : jobList) 
			System.out.println(job__.toExtendedString());
				
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
