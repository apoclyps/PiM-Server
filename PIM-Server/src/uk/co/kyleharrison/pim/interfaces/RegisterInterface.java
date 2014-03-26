package uk.co.kyleharrison.pim.interfaces;

public interface RegisterInterface {

	public boolean userExists();
	public boolean validateUserCredentials();
	public boolean addUser();
	public boolean removeUser();
	public boolean updateUser();	
	
}
