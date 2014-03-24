package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mlesniak.amazon.backend.SearchIndex;

import uk.co.kyleharrison.pim.model.UserStore;
import uk.co.kyleharrison.pim.storage.mysql.connector.UserConnectorMySQL;
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

		try{
			if(request.getParameterMap().containsKey("username")){
				String query = URLEncoder.encode(request.getParameter("username"),"UTF-8");
			}
			if(request.getParameterMap().containsKey("password")){
				String query = URLEncoder.encode(request.getParameter("password"),"UTF-8");
			}
			}catch(Exception e){
				e.printStackTrace();
				jsonResponse =  "{ \"ComicVine\": \"No Results\" }";
			}
		
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
			if(UC.checkUserExists(activeUser)==false){
				if(UC.addUser(activeUser)){
					// return true - user added
					activeUser.setCreated(true);
				}
			}else{
				//return true - user exists
				activeUser.setExists(true);
			}
			
			System.out.println(activeUser.toString());
			
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
				

				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String generatedJson = null;

				try {
					generatedJson = ow.writeValueAsString(activeUser);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				JSONService.JSONResponse(response, generatedJson);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
