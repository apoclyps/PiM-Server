package uk.co.kyleharrison.pim.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.service.RequestService;

/**
 * Servlet implementation class RequestServlet
 */
@WebServlet({"/request","/request/*","/request/*/*"})
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected RequestService requestService;
    protected User currentUserSession;
    
    public RequestController() {
        super();
        
    }
    
    public void init(){
    	this.requestService = new RequestService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Request Servlet Reached : GET");
		this.currentUserSession = null;
		this.requestService.testGrapeVine("Batman");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
