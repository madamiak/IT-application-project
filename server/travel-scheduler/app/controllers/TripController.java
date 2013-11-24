package controllers;

import models.Point;
import play.mvc.*;

public class TripController extends Controller
{
	public static Result getPlaceByPhrase(String name) {
response().setHeader("Access-Control-Allow-Origin", "*");
  response().setHeader("Access-Control-Allow-Methods", "POST");
  response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");  		
		return ok(Point.getByName(name));
	}
	
	public static Result getPlaceById(String id) {
		return ok(Point.getById(Integer.parseInt(id)));
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
