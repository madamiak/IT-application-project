package controllers;

import play.mvc.*;

public class TripController extends Controller
{
	public static Result getPlaceByPhrase(String name) {
response().setHeader("Access-Control-Allow-Origin", "*");
  response().setHeader("Access-Control-Allow-Methods", "POST");
  response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");

		if(name.equals("War")) {
			String result="{\"destinations\": [{\"id\": 321,\"value\": \"Warmia\" },{\"id\": 321,\"value\": \"Warszawa\"},{\"id\": 3,\"value\": \"Warta\" }  ]}";
			return ok(result);

		}
		return ok();
	}
	
	public static Result getPlaceById(String id) {
		return ok();
	}
	
	public static Result getPlaceImageById(String id) {
		return ok();
	}
	
	public static Result getPois() {
		return ok();
	}
	
	public static Result getPoiById(String id) {
		return ok();
	}

}
