package uk.co.kyleharrison.pim.service.control;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import uk.co.kyleharrison.pim.interfaces.ProductInterface;
import uk.co.kyleharrison.pim.model.Product;

public class ProductService implements ProductInterface {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String parameters;
	private Product product;
	private String callback;
	
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
	public boolean createJSONProduct() {
		String json = null;
		String callback = null;
	
		if(this.request.getParameterMap().containsKey("callback")){
			callback = this.request.getParameter("callback");
			//System.out.println("Callback = " + callback);
		} else{
			callback = "callback";
		}
		
		if(this.request.getParameterMap().containsKey("data")){
			json  = this.request.getParameter("data");
			//System.out.println("data packet = " + json);
		}
		
		this.setCallback(callback);
		this.setParameters(json);
		
		if(json.equals(null)){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean createProduct(){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			//System.out.println("Validate Parameters :" +getParameters());
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
		return gson.toJson(this.product);
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
