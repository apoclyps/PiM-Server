package uk.co.kyleharrison.pim.controller.requests;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.model.User;
import uk.co.kyleharrison.pim.security.SecurityUtils;
import uk.co.kyleharrison.pim.service.control.AuthenticationService;

@WebServlet("/login")
public class AuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthenticationService loginService = new AuthenticationService();

	public AuthenticationController() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//Returns json with validation of user - no password. 2 stage authentication
		System.out.println("Servlet Reached");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet Reached : POST");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Hash password before storing in database. Compare hereafter with hashes only
		String hashedPass = password;
		try {
			hashedPass = SecurityUtils.sha1(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Authenticate login
		boolean authenticated = loginService.authenticate(email, hashedPass);

		// TODO: Remove debug
		System.out.println("Authenticated: "
				+ (authenticated ? "true" : "false"));

		// If logged in correctly
		if (authenticated) {
			User user = loginService.getUser(email);

			// Set a session User object - Remember user as LOGGED IN
			request.getSession().setAttribute("user", user);

			// Success, go to main profile page
			//response.sendRedirect("profile");

			response.sendRedirect("profile.jsp");
			return;
		} else {
			// if incorrect login, return to login page with message
			request.setAttribute("failureMessage",
					"Invalid Username or Password.");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		}
	}

}
