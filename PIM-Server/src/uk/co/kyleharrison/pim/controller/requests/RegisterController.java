package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.utilities.JSONService;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Register Controller : ");
		
		String jsonResponse= null;
		
//		String reg_first_name = request.getParameter("reg_first_name");
//		String reg_surname = request.getParameter("reg_surname");
//		String reg_username = request.getParameter("reg_username");
//		String reg_password = request.getParameter("reg_password");
//		String reg_password_confirm = request
//				.getParameter("reg_password_confirm");
//		String reg_email = request.getParameter("reg_email");
		
		try{
			if(request.getParameterMap().containsKey("username")){
				String query = URLEncoder.encode(request.getParameter("username"),"UTF-8");

				//System.out.println(query);
			}
			if(request.getParameterMap().containsKey("password")){
				String query = URLEncoder.encode(request.getParameter("password"),"UTF-8");

				System.out.println(query);
			}
			}catch(Exception e){
				e.printStackTrace();
				jsonResponse =  "{ \"ComicVine\": \"No Results\" }";
			}
		
		UserStore activeUser = null;
		try {
			activeUser = new UserStore();

			//activeUser.setUsername(reg_username);
			//activeUser.setFirstName(reg_first_name);
			//activeUser.setLastName(reg_surname);
			//activeUser.setEmail(reg_email);
			//activeUser.setPassword(reg_password);
			activeUser.setUsername(request.getParameter("username"));
			activeUser.setPassword(request.getParameter("passwords"));
			activeUser.setJoined();
			activeUser.setFollowees("0");
			activeUser.setFollowers("0");

			/*UserConnector UC = new UserConnector();
			UC.addUser(activeUser);
			*/
			System.out.println(activeUser.toString());
			
			// CODE HERE FOR Checking / Inserting to MySQL

		} catch (Exception e) {
			System.out.println("Error creating account " + e.getMessage());
		}

		/*RequestDispatcher rd = getServletContext()
				.getRequestDispatcher("/Home");
		rd.forward(request, response);
		*/
		
		// return true or false here
		try{
			if(request.getParameterMap().containsKey("callback")){
				JSONService.JSONPResponse(response, jsonResponse, request.getParameter("callback"));
			}else{
				JSONService.JSONResponse(response, activeUser.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
