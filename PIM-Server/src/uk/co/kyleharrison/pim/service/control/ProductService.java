package uk.co.kyleharrison.pim.service.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import uk.co.kyleharrison.pim.interfaces.ProductInterface;
import uk.co.kyleharrison.pim.model.Product;
import uk.co.kyleharrison.pim.model.User;

public class ProductService implements ProductInterface {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String parameters;
	private Product product;
	
	public ProductService() {
		super();
	}
	
	public ProductService(HttpServletRequest request,
			HttpServletResponse response, String parameters) {
		super();
		this.request = request;
		this.response = response;
		this.parameters = parameters;
	}

	public ProductService(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public boolean getProductJson() {
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

		System.out.println("\n"+"GetProductJson()");
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
	
	public boolean jsonToProduct(){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.setProduct(mapper.readValue(getParameters(), Product.class));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Object object) {
		this.product = (Product) object;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		 
		// convert java object to JSON format,
		// and returned as JSON formatted string
		return gson.toJson(this.product);
	}

}
