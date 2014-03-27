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

		//String json = null;
		User user = null;
		boolean authenticationFlag = false;

		// 1. validate user credentials as being recieved and parsed to a user
		// object
		RegisterService registerService = new RegisterService(request, response);
		if (registerService.validateUserCredentials()) {
			// 2. parse json parameters to a user object
			if (registerService.generateUser()) {
				user = registerService.getUser();
			}

			// 3. Check if the user exists
			UserStore activeUser = new UserStore(user);
			registerService.setUserStore(activeUser);
			if (registerService.userExists()) {
				authenticationFlag = true;
				System.out.println("Authentication " + authenticationFlag);
			} else {
				// 4. Adding a user to the userstore
				registerService.addUser();
			}
		}else{
			Log.info("Login attempt invalid : Parameters Missing");
			authenticationFlag = false;
		}

		// 5. JSON Output
		try {
			System.out.println("callback("+ JSONService.objectToJSON(registerService.getUserStore()) + ");");
			JSONService.JSONPResponse(response, JSONService.objectToJSON(registerService.getUserStore()), "callback");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
