package uk.co.kyleharrison.pim.interfaces;

public interface RegisterInterface {

	public boolean userExists();
	public boolean passwordExists();
	public boolean validateUserCredentials();
	public boolean generateUser();
	public boolean addUser();
	public boolean removeUser();
	public boolean updateUser();
	//public boolean addUser(boolean authenticationFlag);	
	
}
