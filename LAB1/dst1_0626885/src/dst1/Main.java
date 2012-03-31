package dst1;

import javax.persistence.*;

import dst1.model.Address;
import dst1.model.Admin;
import dst1.model.AdminDAO;
import dst1.model.AdminDAOTest;
import dst1.model.PersistenceUtil;
import dst1.model.User;
import dst1.model.UserDAO;
import dst1.model.UserDAOTest;

public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
		dst01();
		dst02a();
		dst02b();
		dst02c();
		dst03();
		dst04a();
		dst04b();
		dst04c();
		dst04d();
		dst05a();
		dst05b();
		dst05c();
	}

	public static void dst01() {
	
		EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		UserDAO userDAO = new UserDAO();
		AdminDAO adminDAO = new AdminDAO();
		
		
		entityManager.getTransaction().begin();
		
		userDAO.saveUser(new User("Herbert","Franz"));
		adminDAO.saveAdmin(new Admin("Huaba", "Sepp", new Address("street1","city4","9203")));
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
/*		UserDAOTest userDAOTest = new UserDAOTest();
		AdminDAOTest adminDAOTest = new AdminDAOTest();
		userDAOTest.saveUserTest();
		userDAOTest.removeUserTest();
		adminDAOTest.saveAdminTest();
		adminDAOTest.removeAdminTest();
		userDAOTest.freeResources();
		adminDAOTest.freeResources();*/
		PersistenceUtil.freeResources();
	}

	public static void dst02a() {

	}

	public static void dst02b() {

	}

	public static void dst02c() {

	}

	public static void dst03() {

	}

	public static void dst04a() {

	}

	public static void dst04b() {

	}

	public static void dst04c() {

	}

	public static void dst04d() {

	}

        public static void dst05a() {

        }

        public static void dst05b() {

        }

        public static void dst05c() {

        }
}
