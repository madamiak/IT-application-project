package services;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import models.Point;
import models.domain.DistanceData;
import models.domain.DurationData;
import models.domain.PointsPairData;
import models.domain.RouteData;

public class GoogleRouteFinder implements RouteFinder {

	@Override
	public RouteData getRoute(PointsPairData points) {
		JsonNode routeJson = GoogleDirectionsAPI.getRoute(points.origin, points.destination);
		RouteData routeDTO = new RouteData();
		routeDTO.duration = new DurationData();
		routeDTO.distance = new DistanceData();
		routeDTO.duration.text = routeJson.findValue("duration").findValuesAsText("text").get(0);
		routeDTO.duration.value = Integer.parseInt(routeJson.findValue("duration").findValuesAsText("value").get(0));
		routeDTO.distance.text = routeJson.findValue("distance").findValuesAsText("text").get(0);
		routeDTO.distance.value = Integer.parseInt(routeJson.findValue("distance").findValuesAsText("value").get(0));
		routeDTO.polyline = routeJson.findValue("overview_polyline").findValuesAsText("points").get(0);
		return routeDTO;
	}

	@Override
	public Point getAlternativeWaypoint(PointsPairData points, int meters) {
		int currentMeters = 0;
		double[] newDestination = new double[2];
		JsonNode routeJson = GoogleDirectionsAPI.getRoute(points.origin, points.destination);
		try {
			JSONArray array = new JSONArray(routeJson.findValue("steps").toString());
			for(int i = 0; i < array.length(); i++) {
				int distance = array.getJSONObject(i).getJSONObject("distance").getInt("value");
				if(distance + currentMeters > meters) {
					newDestination[0] = array.getJSONObject(i).getJSONObject("end_location").getDouble("lat");
					newDestination[1] = array.getJSONObject(i).getJSONObject("end_location").getDouble("lng");
					Point point = Point.findNearestHotel(newDestination);
					if(point != null) {
						return point;
					} else {
						break;
					}
				} else {
					currentMeters += distance;
				}
			}
		} catch (JSONException e) {
			throw new RuntimeException();
		}
		return null;
	}

}
