package uk.co.kyleharrison.pim.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class JSONService {

	public static void JSONPResponse(HttpServletResponse response,
			JSONObject jsonResponse, String callback) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(callback + "(" + jsonResponse + ");");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void JSONResponse(HttpServletResponse response,
			String jsonResponse) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(jsonResponse);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
