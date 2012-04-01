package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	private String username;
	@Column( columnDefinition = "CHARACTER(32)")
	private byte[] password;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
	private List<Job> jobList;
	
	public User() {
		// used by Hibernate
		jobList = new ArrayList<Job>();
	}
	
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		jobList = new ArrayList<Job>();
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
	public Long getUserId() {
		return userId;
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
	 * Add a job to the jobList
	 * @param job the job to add
	 */
	public void addJob(Job job) {
		job.setUser(this);
		jobList.add(job);
	}
	
	/**
	 * Remove a job from the jobList
	 * @param job the job to remove
	 */
	public void removeJob(Job job) {
		job.setUser(null);
		jobList.remove(job);
	}
	
	/**
	 * Set the jobList
	 * @param jobList the jobList to set
	 */
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
		for(Job job : jobList) {
			job.setUser(this);
		}
	}
	
	/**
	 * Get the jobList
	 * @return the jobList of this User
	 */
	public List<Job> getJobList() {
		return this.jobList;
	}
	
	/**
	 * Summarizes the user's values into a String
	 */
	public String toString() {
		String result = "";
		if(userId != null)
			result = "user id = "+getUserId()+", ";
		else
			result = "user id = -, ";
		
		result += "firstName = "+getFirstName()+", " +
				   "lastName = "+getLastName()+" \n";
		
		result += "Jobs: \n";
		for(Job job : jobList) {
			result += job.toString()+"\n";
		}
		return result;
	}
	
	
}
