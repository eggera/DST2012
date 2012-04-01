package dst1.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Admin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminId;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	
	public Admin() {
		// used by Hibernate
	}
	
	public Admin(String firstName, String lastName, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	
	/**
	 * @return the id
	 */
	public Long getAdminId() {
		return adminId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param id the id to set
	 */
	public void setAdminId(Long id) {
		this.adminId = id;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * Summarizes the user's values into a String
	 */
	public String toString() {
		String result = "";
		if(adminId != 0)
			result = "admin id = "+getAdminId()+", ";
		else
			result = "admin id = -, ";
		
		result += "firstName = "+getFirstName()+", " +
				   "lastName = "+getLastName();
		return result;
	}
	
	
}
