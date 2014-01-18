package controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import models.Point;
import models.Route;
import models.domain.PointData;
import models.dto.PrefferencesDTO;
import models.dto.RouteDTO;
import play.libs.Json;
import play.mvc.*;
import services.PointOrderManipulator;

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
		return TODO;
	}

	public static Result getPois() {
		return ok(Point.getPOIs());
	}

	public static Result getPoiById(String id) {
		return ok(Point.getPOIById(Integer.parseInt(id)));
	}

	public static Result getScheduledTrip(String ids, String prefs) throws JSONException {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Methods", "GET");
		response()
				.setHeader("Access-Control-Allow-Headers",
						"accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
		JSONArray idArray = new JSONArray(Json.parse(ids).findValue("ids")
				.toString());
		List<Point> points = new ArrayList<>();
		List<PointData> pointList = new ArrayList<>();
		long[] pointIds = new long[idArray.length()];
		for (int i = 0; i < idArray.length(); i++) {
			pointIds[i] = Long.parseLong(idArray.getJSONObject(i).get("id")
					.toString());
			points.add(Point.getById(pointIds[i]));
		}
		for (int i = 0; i < points.size(); i++) {
			Point current = points.get(i);
			pointList.add(new PointData(current.id, current.latitude, current.longitude));
		}
		boolean suggest = Json.parse(prefs).findValue("suggest").asBoolean();
		if(suggest) 
			PointOrderManipulator.sortByStraightLine(pointList);
		points.clear();
		for (PointData pd : pointList) {
			points.add(Point.getById(pd.id));
		}
		RouteDTO scheduledTrip = Route.schedule(points, Json.fromJson(Json.parse(prefs), PrefferencesDTO.class));
		return ok(scheduledTrip.asJson());
	}

}
