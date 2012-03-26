package dst1.model;

import javax.persistence.*;

public class Admin {

	@Id
	private Long id;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
}
