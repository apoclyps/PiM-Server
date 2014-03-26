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

		String json = null;
		RegisterService registerService = new RegisterService(request,response);
		if(registerService.validateUserCredentials()){
			json = registerService.getParameters();
		}

		try {
			if (request.getParameterMap().containsKey("username")
					&& request.getParameterMap().containsKey("password")) {
				// authenticationFlag = true;
				String username = URLEncoder.encode(
						request.getParameter("username"), "UTF-8");
				String password = URLEncoder.encode(
						request.getParameter("password"), "UTF-8");

				System.out.println(username);

				if (username == null || password == null) {
					JSONService.JSONResponse(response, "{ success: false }");
					// authenticationFlag = false;
					Log.info("Failed Login");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Register");
		User user = null;
		try {
			System.out.println("JSON :" + json);

			ObjectMapper mapper = new ObjectMapper();
			user = mapper.readValue(json, User.class);

			System.out.println("Register Controller : " + user.getUsername());
		} catch (NullPointerException e) {
			System.out.println("JSON not recieved");
		}

		boolean authenticationFlag = true;

		if (authenticationFlag) {
			System.out.println("create UserStore");
			UserStore activeUser = null;
			try {
				// Userstore needs a constructor to pass in user
				activeUser = new UserStore();

				activeUser.setUsername(user.getUsername());
				activeUser.setPassword(user.getPassword());
				activeUser.encryptPassword();
				activeUser.setJoined();

				// CODE HERE FOR Checking / Inserting to MySQL
				UserConnectorMySQL UC = new UserConnectorMySQL();
				// If user does not exist
				if (UC.checkUserExists(activeUser) == false) {
					if (UC.addUser(activeUser)) {
						// return true - user added
						activeUser.setCreated(true);
					}
				} else {
					// return true - user exists
					activeUser.setExists(true);
				}
			} catch (Exception e) {
				System.out.println("Error creating account " + e.getMessage());
			}

			// return true or false here
			System.out.println("callback("
					+ JSONService.objectToJSON(activeUser) + ");");
			try {

				JSONService.JSONPResponse(response,
						JSONService.objectToJSON(activeUser), "callback");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
