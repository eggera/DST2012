package dst1.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	protected String firstName;
	protected String lastName;
	@Embedded
	protected Address address;
	
	public Person() {
		// used by Hibernate
	}
	
	public Person(String firstName, String lastName, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * String representation of this person
	 */
	@Override
	public String toString() {
		return "id = "+id+", " +
				"firstName = "+firstName+", " +
				"lastName = "+lastName;
	}
	
	/**
	 * String representation of this person
	 */
	public String toExtendedString() {
		return "id = "+id+", " +
				"firstName = "+firstName+", " +
				"lastName = "+lastName+", " +
				address;
	}
}
