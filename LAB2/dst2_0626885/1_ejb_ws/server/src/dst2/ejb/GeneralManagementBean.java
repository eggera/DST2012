package dst2.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.enterprise.security.auth.realm.User;

import dst1.model.Execution.JobStatus;

@Stateless
public class GeneralManagementBean implements GeneralManagement {

	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB private PriceManagement priceManagement;

	@Override
	public void setPrice(Integer numberOfHistoricalJobs, BigDecimal price) {
		
		priceManagement.setPrice(numberOfHistoricalJobs, price);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTotalBillFor(String username) {
		
		JobStatus status = JobStatus.FINISHED;
		
		Query query = entityManager.createQuery("select u, g" +
						 		"	from User u " +
								"				join u.jobList j 		join j.execution e" +
						 		" 				join e.computerList c 	join c.cluster cl" +
								"				join cl.grid g" +
								"	where u.username = :username and j.isPaid = false and " +
								"			e.status = :status ");
		
		query.setParameter("username", username).setParameter("status", status);
		
		List<Object> resultObjects = query.getResultList();
		
		System.out.println(" BILL RESULT  ================= ");
		System.out.println(resultObjects);
						// 		"			g.name = :gname and cGrid.name = :gname " +
						//		"	group by u having count(distinct j) >= :minJobs", User.class);
		
		
		
		//left join u.membershipList m join m.grid g
		
		return null;
		
	} 
	
}
