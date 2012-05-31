package dst1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

@Entity
@NamedQueries({
			@NamedQuery (name = "findAllUsers", query = "select u from User u"), 
			@NamedQuery (name = "findExecutions", query = "select e from Execution e"),
			@NamedQuery (name = "findActiveUsersForGrid", 
						 query = "select u " +
						 		"	from User u join u.membershipList m join m.grid g" +
						 		"	where g.name = :gname"),
			@NamedQuery (name = "activeUsersWithXJobs",
						 query = "select u" +
						 		"	from User u join u.membershipList m join m.grid g" +
								"				join u.jobList j 		join j.execution e" +
						 		" 				join e.computerList c 	join c.cluster cl" +
								"				join cl.grid cGrid" +
						 		"	where g.name = :gname and cGrid.name = :gname " +
								"	group by u having count(distinct j) >= :minJobs"),
			@NamedQuery (name = "mostActiveUsers",
						 query = "select u" +
						 		"	from User u join u.jobList j" +
						 		"	group by u having count(j) >= " +
						 		"					all (select count(jobs)" +
						 		"						from User usr join usr.jobList jobs" +
						 		"						group by usr)")
			})
@Table (uniqueConstraints = {
		@UniqueConstraint (columnNames={"accountNo", "bankCode"})
							})
public class User extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column (unique=true)
	@NotNull
	protected String username;
	@Column (columnDefinition = "CHARACTER(32)")
	@Index (name = "passwordIndex")
	protected byte[] password;
	protected String accountNo;
	protected String bankCode;
	
	@OneToMany (mappedBy="user", cascade=CascadeType.ALL)
	protected List<Job> jobList;
	
	@OneToMany (mappedBy="user", cascade=CascadeType.ALL)
	protected List<Membership> membershipList;
	
	
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
		
		this(firstName, lastName);
		this.address = address;
		this.username = username;
		this.password = password;
	}
	
	public User(String firstName, String lastName, Address address, 
					String username, byte[] password, String accountNo, String bankCode) {
		
		this(firstName, lastName, address, username, password);
		this.accountNo = accountNo;
		this.bankCode = bankCode;
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
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return this.accountNo;
	}
	
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return this.bankCode;
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
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	@Override
	public String toString() {
		return	 "user id = "+id+", " +
			   "firstName = "+firstName+", " +
			    "lastName = "+lastName;
	}
	
	/**
	 * Extended String representation of this User
	 */
	@Override
	public String toExtendedString() {
		return "userId = "+id+", " +
			"firstName = "+firstName+", " +
			 "lastName = "+lastName+" " +
		"\n		username = "+username+", " +
			 "password = "+password+", " +
			"accountNo = "+accountNo+", " +
			 "bankCode = "+bankCode;
	}
	
	
}
