package controllers;

import java.util.ArrayList;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class RequestHttp extends Controller{

	@Before 
	private static void setCORS() {		
			Http.Header hd = new Http.Header(); 
			hd.name = "Access-Control-Allow-Origin"; 
			hd.values = new ArrayList<String>(); 
			hd.values.add("*"); 
			Http.Response.current().headers.put("Access-Control-Allow-Origin",hd);		
	}
	
}
