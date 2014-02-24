package uk.co.kyleharrison.pim.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.service.RequestService;
import uk.co.kyleharrison.pim.utilities.Convertors;

/**
 * Servlet implementation class RequestServlet
 */
@WebServlet({ "/request", "/request/*", "/request/*/*" })
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected RequestService requestService;
	protected User currentUserSession;

	public RequestController() {
		super();

	}

	public void init() {
		this.requestService = new RequestService();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String requestComponents[] = Convertors.SplitRequestPath(request);

		this.requestService.logAccess(requestComponents);

		RequestDispatcher dispatcher = null;

		switch (requestComponents[requestComponents.length - 1].toUpperCase()) {
			case "COMICVINE":
				dispatcher = getServletContext().getRequestDispatcher("/ComicVineController");
				break;
			case "AMAZON":
				dispatcher = getServletContext().getRequestDispatcher("/AmazonController");
				break;
			case "SPOTIFY":
				dispatcher = getServletContext().getRequestDispatcher("/SpotifyController");
				break;
			case "STEAM":
				dispatcher = getServletContext().getRequestDispatcher("/SteamController");
				break;
			case "IMDB":
				dispatcher = getServletContext().getRequestDispatcher("/IMDBController");
				break;
			case "ISBNDB":
				dispatcher = getServletContext().getRequestDispatcher("/ISBNDBController");
				break;
			default:
				dispatcher = getServletContext().getRequestDispatcher("/MasterController");
				break;
		}

		this.currentUserSession = null;
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
