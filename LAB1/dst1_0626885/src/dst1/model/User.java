package dst1.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	private String username;
	private byte[] password;
	
	public User() {
		// used by Hibernate
	}
	
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String firstName, String lastName, 
			Address address, String username, byte[] password) {
		
		this(firstName, lastName);
		this.address = address;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Long getID() {
		return id;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public byte[] getPassword() {
		return password;
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
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	/**
	 * Summarizes the user's values into a String
	 */
	public String toString() {
		String result = "";
		if(id != 0)
			result = "user id = "+getID()+", ";
		else
			result = "user id = -, ";
		
		result += "firstName = "+getFirstName()+", " +
				   "lastName = "+getLastName();
		return result;
	}
	
	
}
