package uk.co.kyleharrison.pim.service.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.pim.interfaces.RegisterInterface;

public class RegisterService implements RegisterInterface {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String parameters;
	
	public RegisterService() {
		super();
	}
	
	public RegisterService(HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	@Override
	public boolean userExists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateUserCredentials() {
		String json = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.request.getInputStream()));
			json = "";
			if (br != null) {
				json = br.readLine();
			}
			System.out.println("JSON : " + json);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(this.request.getParameterMap().toString());
		System.out.println(this.request.getParameterMap().size());
		System.out.println(this.request.getParameterMap().keySet().iterator().next());
		json = this.request.getParameterMap().keySet().iterator().next();
		
		this.setParameters(json);
		if(json.equals(null)){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean addUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}
