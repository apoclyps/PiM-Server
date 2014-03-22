package uk.co.kyleharrison.pim.interfaces;

import uk.co.kyleharrison.pim.model.UserStore;

public interface UserConnectorInterface {

	public boolean addUser(UserStore user);
	public boolean checkUserExists(UserStore user);
	public boolean searchUserByEmail(UserStore user);
	public boolean searchUserByUsername(UserStore user);
	public boolean updateUser(UserStore user);
	public boolean removeUser(UserStore user);
	
}
