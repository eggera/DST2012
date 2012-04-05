package dst1.model;

import java.util.List;

import javax.persistence.*;

/**
 * This class is used to test the functionality of the user DAO
 *
 */
public class AdminDAOTest {

	private AdminDAO adminDAO;
	
	public AdminDAOTest() {
		this.adminDAO = new AdminDAO();
	}
	
	public void saveAdminTest() {
		
		System.out.println("\n--------  Save Admin Test  ---------");
		
		List<Admin> adminList = adminDAO.getAllAdmins();
		printAllAdmins(adminList);
		
		Address address = new Address("topStreet 1","Gotham","9999");
		Admin admin = new Admin("Huber", "Franz", address);
		
		adminDAO.saveAdmin(admin);
		System.out.println("Admin saved: \n"+admin);

		
		Address address2 = new Address("topStreet 5","Springfield","8888");
		Admin admin2 = new Admin("Herbert", "Meier", address2);
		
		adminDAO.saveAdmin(admin2);
		System.out.println("Admin saved: \n"+admin2);
		
		
		adminList = adminDAO.getAllAdmins();
		printAllAdmins(adminList);		
	}
	
	public void removeAdminTest() {
		
		System.out.println("\n--------  Remove Admin Test  ---------");
		
		List<Admin> adminList = adminDAO.getAllAdmins();
		printAllAdmins(adminList);
		Admin admin = adminDAO.findAdmin(adminList.get(0).getId());

		adminDAO.removeAdmin(admin.getId());
		System.out.println("Admin removed: \n"+admin);
		
		adminList = adminDAO.getAllAdmins();
		printAllAdmins(adminList);
	}
	
	/**
	 * Prints all admins currently stored in the persistence context
	 */
	public void printAllAdmins(List<Admin> adminList) {
		
		System.out.println("Existing admins in db:");
		if(adminList.size() == 0) {
			System.out.println("No records.");
			return;
		}
		for(Admin usr : adminList)
			System.out.println(usr);
	}
	
	/**
	 * Free all resources from related classes
	 */
	public void freeResources() {
		adminDAO.freeResources();
	}
	
}
