package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.service.control.RegisterService;
import uk.co.kyleharrison.pim.utilities.JSONService;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String json = null;
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
					if (registerService.userExists()) {
						authenticationFlag = true;
						System.out.println("Authentication " + authenticationFlag);
						// 4. Checking credentials exist
						
						if(registerService.passwordExists()){
							System.out.println("Authentication Success");
							// TODO Method required to do this inside Register Service 
							UserStore ac = registerService.getUserStore();
							ac.setSuccess(true);
							registerService.setUserStore(ac);
						}else{
							System.out.println("Authentication Failed");
						}
						
					} else {
						// 4. Adding a user to the userstore
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
	}

}
