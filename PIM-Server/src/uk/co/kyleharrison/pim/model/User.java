package uk.co.kyleharrison.pim.model;

public class User {
	
	protected String username;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;
	protected String uID;
	
	public User() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUniqueID() {
		return uID;
	}
	public void setUniqueID(String uniqueID) {
		this.uID = uniqueID;
	}
	
}
