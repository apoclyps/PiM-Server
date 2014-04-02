package uk.co.kyleharrison.pim.service.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.kyleharrison.pim.interfaces.RegisterInterface;
import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.storage.mysql.connector.UserConnectorMySQL;

public class RegisterService implements RegisterInterface {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String parameters;
	private User user = null;
	private UserStore userStore = null;
	
	public RegisterService() {
		super();
	}
	
	public RegisterService(HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	@Override
	public boolean userExists() {
		UserConnectorMySQL UC = new UserConnectorMySQL();
		// If user does not exist
		if (UC.checkUserExists(this.userStore)) {
			return true;
			/*if (UC.addUser(this.userStore)) {
				// return true - user added
				this.userStore.setCreated(true);
				return true;
			}*/
		}
		return false;
	}

	@Override
	public boolean validateUserCredentials() {
		/*
		String json = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.request.getInputStream()));
			json = "";
			if (br != null) {
				json = br.readLine();
			}
			System.out.println("JSON : " + json);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(this.request.getParameterMap().toString());
		System.out.println(this.request.getParameterMap().size());
		System.out.println(this.request.getParameterMap().keySet().iterator().next());
		 * */
		
		if(this.request.getParameterMap().containsKey("data")){
			this.setParameters(this.request.getParameter("data"));
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean addUser() {
		//4. Adding a user to the userstore
			System.out.println("create UserStore");
			this.userStore = null;
			try {
				// Userstore needs a constructor to pass in user
				this.userStore  = new UserStore(this.user);

				//activeUser.setUsername(user.getUsername());
			//	activeUser.setPassword(user.getPassword());
				this.userStore.encryptPassword();
				this.userStore.setJoined();

				// CODE HERE FOR Checking / Inserting to MySQL
				UserConnectorMySQL UC = new UserConnectorMySQL();
				// If user does not exist
				if (UC.checkUserExists(this.userStore) == false) {
					if (UC.addUser(this.userStore)) {
						// return true - user added
						this.userStore.setCreated(true);
						this.userStore.setSuccess(true);
						this.userStore.setLoggedIn(true);
						return true;
					}
				} else {
					// return true - user exists
					this.userStore.setExists(true);
				}
			} catch (Exception e) {
				System.out.println("Error creating account " + e.getMessage());
			}
		return false;
	}

	@Override
	public boolean removeUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean generateUser() {
		try {
			System.out.println("JSON :" + getParameters());
			this.user = null;
			ObjectMapper mapper = new ObjectMapper();
			this.user = mapper.readValue(getParameters(), User.class);

			System.out.println("Register Controller : " + user.getUsername());
			return true;
		} catch (NullPointerException e) {
			System.out.println("JSON not recieved");
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserStore getUserStore() {
		return userStore;
	}

	public void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	
	public void setUserStoreUser(User user){
		this.userStore = new UserStore(user);
	}

	@Override
	public boolean passwordExists() {
		UserConnectorMySQL UC = new UserConnectorMySQL();
		// If user does not exist
		System.out.println("Register Service : Password " + this.userStore.getPassword());
		if (UC.checkPasswordExists(this.userStore)) {
			return true;
		}
		return false;
	}

}
