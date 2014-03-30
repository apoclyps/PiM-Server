package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String mediaType = null;
		String requestType = null;
		
		try{
			String pathInfo = request.getPathInfo(); // /{value}/test
			String[] pathParts = pathInfo.split("/");
			requestType = pathParts[1];
			mediaType = pathParts[2]; // {value}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		switch(requestType){
		case "add" :
			break;
		case "remove" :
			break;
		case "update" :
			break;
		case "default" :
			break;
		}
		
		// Selecting the apprpriate media type for request.
		switch(mediaType){
		case "comic" : 
			Log.info("Product Controller : Add Comic");
			break;
		case "dvd" :
			Log.info("Product Controller : Add DVD");
			break;
		case "cd" :
			Log.info("Product Controller : Add CD");
			break;
		case "book" :
			Log.info("Product Controller : Add Book");
			break;
		case "game" :
			Log.info("Product Controller : Add Game");
			break;
		case "other" :
			Log.info("Product Controller : Add Other");
			break;
		default :
			Log.info("Product Controller : Unkown Product");
			System.out.println("Product Controller : Unkown Product");
			break;
		}
		
		System.out.println("Request Type : " + requestType);
		System.out.println("Media Type   : "+mediaType);
	}

}
