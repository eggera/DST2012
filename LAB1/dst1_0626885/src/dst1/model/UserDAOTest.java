package dst1.model;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.*;

import org.junit.*;

import junit.framework.TestCase;

/**
 * This class is used to test the functionality of the user DAO
 * @author whiteangel
 *
 */
public class UserDAOTest {

	private EntityManagerFactory entityManagerFactory;
	private UserDAO userDAO;
	
	public void setUp() {
		// an EntityManagerFactory is created once for an application
		// the name must match the one specified for persistence-unit in persistence.xml
		entityManagerFactory = Persistence.createEntityManagerFactory( "grid" );
	}
	
	public void tearDown() {
		entityManagerFactory.close();
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void saveUserTest() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Address address = new Address("longStreet","niceCity","5555");
		User user = new User("Pete", "Lain", address, "cheatpete", Service.getMD5Hash("r234"));
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
//		userDAO.saveUser(user);
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<User> result = entityManager.createQuery( "from User", User.class ).getResultList();
		
		assertTrue(result.size() == 1);
		
		for(User usr : result) {
			System.out.println("Name: "+usr.getFirstName()+" "+usr.getLastName());
			assertTrue(user.getFirstName().equals(usr.getFirstName()));
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
	}
}
