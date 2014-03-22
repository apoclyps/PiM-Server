package uk.co.kyleharrison.pim.cassandra;

import uk.co.kyleharrison.pim.interfaces.UserConnectorInterface;
import uk.co.kyleharrison.pim.model.UserStore;

public class UserConnector implements UserConnectorInterface {

	public UserConnector() {
		super();
	}

	@Override
	public boolean addUser(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUserExists(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean searchUserByEmail(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean searchUserByUsername(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(UserStore user) {
		// TODO Auto-generated method stub
		return false;
	}


}
