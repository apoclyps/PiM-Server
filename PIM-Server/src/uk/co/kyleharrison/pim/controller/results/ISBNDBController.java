package uk.co.kyleharrison.pim.controller.results;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.service.model.ISBNDBService;
import uk.co.kyleharrison.pim.utilities.JSONService;

public class ISBNDBController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ISBNDBService isbndbService;
    
    public ISBNDBController() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		this.isbndbService = new ISBNDBService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonResponse = isbndbService.testISBNDB("Batman");
		JSONService.JSONResponse(response,jsonResponse);
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