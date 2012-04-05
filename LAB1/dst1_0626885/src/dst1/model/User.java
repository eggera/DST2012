package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class User extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	@Column( columnDefinition = "CHARACTER(32)")
	private byte[] password;
	
	@OneToMany (mappedBy="user", cascade=CascadeType.ALL)
	private List<Job> jobList;
	
	@OneToMany (mappedBy="user", cascade=CascadeType.ALL)
	private List<Membership> membershipList;
	
	
	public User() {
		// used by Hibernate
		jobList = new ArrayList<Job>();
		membershipList = new ArrayList<Membership>();
	}
	
	public User(String firstName, String lastName) {
		super(firstName, lastName, null);
		
		jobList = new ArrayList<Job>();
		membershipList = new ArrayList<Membership>();
	}
	
	public User(String firstName, String lastName, 
			Address address, String username, byte[] password) {
		
		super(firstName, lastName, address);
		this.username = username;
		this.password = password;
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
		jobList.add(job);
		job.setUser(this);
	}
	
	/**
	 * Remove a job from the jobList
	 * @param job the job to remove
	 */
	public void removeJob(Job job) {
		jobList.remove(job);
		job.setUser(null);
	}
	
	/**
	 * Get the jobList
	 * @return the jobList of this User
	 */
	public List<Job> getJobList() {
		return this.jobList;
	}
	
	/**
	 * Set the jobList
	 * @param jobList the jobList to set
	 */
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
	
	/**
	 * Adds a membership entity to the list
	 * @param membership the membership to set
	 */
	public void addMembership(Membership membership) {
		this.membershipList.add(membership);
	}
	
	/**
	 * Removes a membership entity from the list
	 * @return the membership of this Grid
	 */
	public void removeMembership(Membership membership) {
		this.membershipList.remove(membership);
	}
	
	/**
	 * Get the membershipList
	 * @return the membershipList
	 */
	public List<Membership> getMembershipList() {
		return this.membershipList;
	}
	
	/**
	 * Sets the membershipList
	 * @param membershipList the membershipList to set
	 */
	public void setMembershipList(List<Membership> membershipList) {
		this.membershipList = membershipList;
	}
	
	/**
	 * String representation of this User
	 */
	public String toString() {
		return	 "user id = "+id+", " +
			   "firstName = "+firstName+", " +
			    "lastName = "+lastName+" \n";
	}
	
	/**
	 * Extended String representation of this User
	 */
	public String toExtendedString() {
		String result = "";
		if(id != null)
			result = "user id = "+id+", ";
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
