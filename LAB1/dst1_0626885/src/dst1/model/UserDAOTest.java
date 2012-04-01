package dst1.model;

import java.util.List;

import javax.persistence.*;

/**
 * This class is used to test the functionality of the user DAO
 *
 */
public class UserDAOTest {

	private UserDAO userDAO;
	
	public UserDAOTest() {
		this.userDAO = new UserDAO();
	}
	
	public void saveUserTest() {
		
		System.out.println("\n--------  Save User Test  ---------");
		
		List<User> userList = userDAO.getAllUsers();
		printAllUsers(userList);
		
		Address address = new Address("longStreet","niceCity","5555");
		User user = new User("Pete", "Lain", address, "cheatpete", Service.getMD5Hash("r234"));
		
		userDAO.saveUser(user);
		System.out.println("User saved: "+user);

		
		Address address2 = new Address("shortWay","Destiny","1234");
		User user2 = new User("Jonny", "Ronny", address2, "jonron", Service.getMD5Hash("Yuzz"));
		
		userDAO.saveUser(user2);
		System.out.println("User saved: "+user2);
		
		
		userList = userDAO.getAllUsers();
		printAllUsers(userList);		
	}
	
	public void removeUserTest() {
		
		System.out.println("\n--------  Remove User Test  ---------");
		
		List<User> userList = userDAO.getAllUsers();
		printAllUsers(userList);
		User user = userDAO.findUser(userList.get(0).getUserId());

		userDAO.removeUser(user);
		System.out.println("User removed: "+user);
		
		userList = userDAO.getAllUsers();
		printAllUsers(userList);
	}
	
	/**
	 * Prints all users currently stored in the persistence context
	 */
	public void printAllUsers(List<User> userList) {
		
		System.out.println("Existing users in db:");
		if(userList.size() == 0) {
			System.out.println("No records.");
			return;
		}
		for(User usr : userList)
			System.out.println(usr);
	}
	
	/**
	 * Free all resources from related classes
	 */
	public void freeResources() {
		userDAO.freeResources();
	}
	
}
