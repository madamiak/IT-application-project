package services;

import com.fasterxml.jackson.databind.JsonNode;

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

}
