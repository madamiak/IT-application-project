package services;

import models.Point;
import models.domain.DistanceData;
import models.domain.DurationData;
import models.domain.PointsPairData;
import models.domain.RouteData;

import org.json.JSONArray;
import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonNode;

public class GoogleRouteFinder implements RouteFinder {

	@Override
	public RouteData getRoute(PointsPairData points) {
		JsonNode routeJson = askGoogle(points);
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

	private JsonNode askGoogle(PointsPairData points) {
		return GoogleDirectionsAPI.getRoute(points.origin, points.destination);
	}

	@Override
	public Point getAlternativeWaypoint(PointsPairData points, int meters) {
		int currentMeters = 0;
		double[] newDestination = new double[2];
		JsonNode routeJson = askGoogle(points);
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
