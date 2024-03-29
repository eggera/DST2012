package dst1.model;

import javax.persistence.*;

@Embeddable
public class Address {

	private String street;
	private String city;
	private String zipCode;
	
	public Address() {
		// used by Hibernate
	}
	
	public Address(String street, String city, String zipCode) {
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * String representation of this Address
	 */
	@Override
	public String toString() {
		return street+", "+zipCode+" "+city;
	}
	
}
