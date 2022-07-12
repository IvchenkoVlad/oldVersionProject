package com.ivchenko.administration;
 

public class Customer {
	private int customerID;
	private String firstName;
	private String lastName;   
	private int pmbNumber;
	private String phone;
	private String email;
	private int userFK;
	
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", pmbNumber=" + pmbNumber + ", phone=" + phone + ", email=" + email + ", userFK=" + userFK + "]";
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPmbNumber() {
		return pmbNumber;
	}

	public void setPmbNumber(int pmbNumber) {
		this.pmbNumber = pmbNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserFK() {
		return userFK;
	}

	public void setUserFK(int userFK) {
		this.userFK = userFK;
	}
	
	public Customer(String firstName, String lastName, int pmbNumber, String phone, String email, int userFK) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.pmbNumber = pmbNumber;
		this.phone = phone;
		this.email = email;
		this.userFK = userFK;
	}

	public Customer(int customerID, String firstName, String lastName, int pmbNumber, String phone, String email,
			int userFK) {
		super();
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pmbNumber = pmbNumber;
		this.phone = phone;
		this.email = email;
		this.userFK = userFK;
	}
	
	public Customer(int customerID, String firstName, String lastName, int pmbNumber, String phone, String email) {
		super();
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pmbNumber = pmbNumber;
		this.phone = phone;
		this.email = email;
	}
	
	
}
