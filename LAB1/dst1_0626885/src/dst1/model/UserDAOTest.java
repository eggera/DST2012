package dst1.model;

/**
 * This class is used to test the functionality of the user DAO
 * @author whiteangel
 *
 */
public class UserDAOTest {

	UserDAO userDAO;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void saveUserTest() {
		Address address = new Address("longStreet","niceCity","5555");
		User user = new User("Pete", "Lain", address, "cheatpete", Service.getMD5Hash("r234"));
		userDAO.saveUser(user);
	}
}
