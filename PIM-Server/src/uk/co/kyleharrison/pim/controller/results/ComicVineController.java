package uk.co.kyleharrison.pim.controller.results;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.service.model.ComicVineService;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.pim.utilities.JSONService;


public class ComicVineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComicVineService comicVineService;
       
    public ComicVineController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		this.comicVineService = new ComicVineService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jsonResponse = null;
		// Preforms a query based upon if the query parameter is set or not
		try{
		if(request.getParameterMap().containsKey("query")){
			jsonResponse = this.comicVineService.executeQuery(request.getParameter("query"));
		}else{
			jsonResponse = this.comicVineService.executeQuery("Batman");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		// Returns prefered JSON as the response.
		try{
			if(request.getParameterMap().containsKey("callback")){
				JSONService.JSONPResponse(response, jsonResponse, request.getParameter("callback"));
			}else{
				JSONService.JSONResponse(response, jsonResponse);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
