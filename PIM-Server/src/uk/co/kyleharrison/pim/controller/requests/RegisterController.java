package uk.co.kyleharrison.pim.controller.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.storage.mysql.connector.UserConnectorMySQL;
import uk.co.kyleharrison.pim.utilities.JSONService;

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

		System.out.println("Register");
		
        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        System.out.println("JSON :"+json);
        
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        
		System.out.println("Register Controller : "+user.getUsername() );
		boolean authenticationFlag = true;

		try {
			if (request.getParameterMap().containsKey("username")
					&& request.getParameterMap().containsKey("password")) {
				//authenticationFlag = true;
				String username = URLEncoder.encode(
						request.getParameter("username"), "UTF-8");
				String password = URLEncoder.encode(
						request.getParameter("password"), "UTF-8");

				if (username == null || password == null) {
					JSONService.JSONResponse(response, "{ success: false }");
					//authenticationFlag = false;
					Log.info("Failed Login");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (authenticationFlag) {
			UserStore activeUser = null;
			try {
				activeUser = new UserStore();

				activeUser.setUsername(request.getParameter("username"));
				activeUser.setPassword(request.getParameter("password"));
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
			System.out.println(JSONService.objectToJSON(activeUser));
			try {
				if (request.getParameterMap().containsKey("callback")) {
					JSONService.JSONPResponse(response,
							JSONService.objectToJSON(activeUser),
							request.getParameter("callback"));
				} else {
					System.out.println("Response returned");
					JSONService.JSONResponse(response,
							JSONService.objectToJSON(activeUser));
							Log.info("Response Returned");
							
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
