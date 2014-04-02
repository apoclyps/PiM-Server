package uk.co.kyleharrison.pim.service.control;

import uk.co.kyleharrison.pim.connectors.DatabaseConnector;
import uk.co.kyleharrison.pim.model.User;

public class AuthenticationService extends DatabaseConnector {

	public boolean authenticate(String email, String hash) {
		
		return true;
	}

	// Find successfully logged in user and extract into a bean for use in a jsp.
	public User getUser(String email) {
		
		User user = new User();
		user.setEmail(email);
		user.setFirstName("Kyle");
		user.setLastName("Harrison");
		
		return new User();
	}
	
}
