package controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import models.POI;
import models.POIDetailed;
import models.Point;
import models.Route;
import models.domain.PointData;
import models.dto.POIsDTO;
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

	public static Result getPois(double lng, double lat, double radius, boolean withdetails) {
		List<Point> pois = Point.getPOIs(lat, lng, radius, withdetails);
		POIsDTO dto = new POIsDTO();
		dto.pois = new ArrayList<POI>();
		for (int i = 0; i < pois.size(); i++) {
			if(withdetails) {
				POIDetailed dest = new POIDetailed();
				dest.id = pois.get(i).id;
				dest.lat = pois.get(i).latitude;
				dest.lng = pois.get(i).longitude;
				dest.name = pois.get(i).name;
				dto.pois.add(dest);
			} else {
				POI dest = new POI();
				dest.id = pois.get(i).id;
				dto.pois.add(dest);
			}
		}
		return ok(Json.toJson(dto));
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
