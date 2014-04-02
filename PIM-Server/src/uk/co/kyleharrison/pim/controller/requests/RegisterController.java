package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.service.control.RegisterService;
import uk.co.kyleharrison.pim.storage.mysql.connector.UserConnectorMySQL;
import uk.co.kyleharrison.pim.utilities.JSONService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterController() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserStore activeUser = null;
		boolean authenticationFlag = false;

		// 1. validate user credentials as being recieved and parsed to a user
		// object
		RegisterService registerService = new RegisterService(request, response);
		if (registerService.validateUserCredentials()) {
			// 2. parse json parameters to a user object
			if (registerService.generateUser()) {
				activeUser = new UserStore(registerService.getUser());
				registerService.setUserStore(activeUser);
			}

			// 3. Check if the user exists
			if (!(registerService.userExists())) {
				authenticationFlag = true;
				System.out.println("Authentication " + authenticationFlag);
				// 4. Checking credentials exist
				if(registerService.passwordExists()){
					System.out.println("Authentication Success");
					// TODO Method required to do this inside Register Service 
					UserStore ac = registerService.getUserStore();
					ac.setSuccess(false);
					registerService.setUserStore(ac);
				}else{
					System.out.println("Creating User");
					//Check Database - If returns users exists false 
					UserConnectorMySQL ucs = new UserConnectorMySQL();
					UserStore ac = registerService.getUserStore();
					
					if(ucs.checkUserExists(ac)){
						System.out.println("Existing user with username");
						ac.setSuccess(false);
					}
					else{
						System.out.println("User creation");
						//Create USer
						if(ucs.addUser(ac)){
							System.out.println("User Created");
							ac.setSuccess(true);
						}else{
							System.out.println("User Not created");
							ac.setSuccess(false);
						}
						
					}
					//add to database- if true - then set true and return
					
					
					//ac.setSuccess(true);
					registerService.setUserStore(ac);
				}
				
			} else {
				// 4. User Exists so register fails
				UserStore ac = registerService.getUserStore();
				ac.setMessage("Username already exists");
				registerService.setUserStore(ac);
				
				authenticationFlag = false;
			}
		}else{
			Log.info("Login attempt invalid : Parameters Missing");
			authenticationFlag = true;
		}

		// 5. JSON Output
		try {
			System.out.println("callback("+ JSONService.objectToJSON(registerService.getUserStore()) + ");");
			JSONService.JSONPResponse(response, JSONService.objectToJSON(registerService.getUserStore()), "callback");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Reached");
		//System.out.println("\nCallback "+request.getParameter("callback"));
		//System.out.println("Data "+request.getParameter("data"));

		
			
			
		
	}

}
