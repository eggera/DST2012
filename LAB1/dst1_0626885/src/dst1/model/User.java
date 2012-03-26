package dst1.model;

import javax.persistence.*;

@Entity
public class User {

	@Id
	private Long id;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	private String username;
	private byte[] password;
	
	@PersistenceContext
	EntityManager em;
	
	
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User createUser(User user) {
		em.persist(user);
	}
}
