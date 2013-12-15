package controllers;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;

import models.Point;
import models.Route;
import models.dto.RouteDTO;
import play.libs.Json;
import play.mvc.*;

public class TripController extends Controller {
	public static Result getPlaceByPhrase(String name) {
		
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Methods", "POST");
		response()
				.setHeader("Access-Control-Allow-Headers",
						"accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");

		return ok(Point.getDestinationsByName(name));
	}

	public static Result getPlaceById(String id) {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Methods", "POST");
		response()
				.setHeader("Access-Control-Allow-Headers",
						"accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
		
		return ok(Point.getDestinationById(Integer.parseInt(id)));
	}

	public static Result getPlaceImageById(String id) {
		return ok();
	}

	public static Result getPois() {
		return ok(Point.getPOIs());
	}

	public static Result getPoiById(String id) {
		return ok(Point.getPOIById(Integer.parseInt(id)));
	}
	
	public static Result getScheduledTrip(String ids) throws JSONException {
		JSONArray idArray = new JSONArray(Json.parse(ids).findValue("ids").toString());
		long[] pointIds = new long[idArray.length()];
		for (int i = 0; i < idArray.length(); i++) {
			pointIds[i] = Long.parseLong(idArray.getJSONObject(i).get("id").toString());
		}
		RouteDTO scheduledTrip = Route.schedule(pointIds);
		return ok(scheduledTrip.asJson());
	}

}
