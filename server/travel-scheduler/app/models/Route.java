package models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.JsonNode;

import models.domain.DistanceData;
import models.domain.DurationData;
import models.domain.PointsPairData;
import models.domain.RouteData;
import models.domain.RoutePointData;
import models.domain.SummaryData;
import models.dto.RouteDTO;
import play.db.ebean.Model;
import services.GoogleRouteFinder;
import services.RouteFinder;

@Entity
public class Route extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_route")
	public long id;
	@Column(name = "route_name")
	public String name;
	@Column(name = "route_starting_time")
	public Date startingTime;
	@Column(name = "route_budget")
	public int budget;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_transport_type")
	public TransportType transportType;

	public static RouteDTO schedule(long[] pointIds) {
		RouteFinder finder = new GoogleRouteFinder();
		int summaryDistanceInMeters = 0;
		int summaryDurationInSeconds = 0;

		RouteDTO routeDTO = new RouteDTO();
		routeDTO.summary = new SummaryData();
		routeDTO.summary.distance = new DistanceData();
		routeDTO.summary.duration = new DurationData();
		routeDTO.points = new ArrayList<RoutePointData>();
		routeDTO.routes = new ArrayList<RouteData>();

		JsonNode originPointAsJson = Point.getById(pointIds[0]);
		String origin = originPointAsJson.findValuesAsText("name").get(0);

		RoutePointData originRoutePoint = createRoutePopintData(originPointAsJson);
		routeDTO.points.add(originRoutePoint);

		for (int i = 1; i < pointIds.length; i++) {
			JsonNode destinationPointAsJson = Point.getById(pointIds[i]);
			String destination = destinationPointAsJson.findValuesAsText("name").get(0);
			
			RoutePointData destinationRoutePoint = createRoutePopintData(destinationPointAsJson);
			routeDTO.points.add(destinationRoutePoint);
			
			RouteData route = finder.getRoute(new PointsPairData(origin, destination));
			routeDTO.routes.add(route);

			summaryDurationInSeconds += route.duration.value;
			summaryDistanceInMeters += route.distance.value;

			origin = destination;
		}

		routeDTO.summary.distance.value = summaryDistanceInMeters;
		routeDTO.summary.distance.text = getDistanceText(summaryDistanceInMeters);
		routeDTO.summary.duration.value = summaryDurationInSeconds;
		routeDTO.summary.duration.text = getDurationText(summaryDurationInSeconds);

		return routeDTO;
	}

	private static RoutePointData createRoutePopintData(JsonNode point1) {
		RoutePointData pointData1 = new RoutePointData();
		pointData1.id = Long.parseLong(point1.findValuesAsText("id").get(0));
		pointData1.type = point1.findValue("type").findValuesAsText("name").get(0);
		pointData1.name = point1.findValuesAsText("name").get(0);
		pointData1.lat = point1.findValuesAsText("latitude").get(0);
		pointData1.lng = point1.findValuesAsText("longitude").get(0);
		return pointData1;
	}

	private static String getDurationText(int summaryDurationInSeconds) {
		int hours = summaryDurationInSeconds / 3600;
		int minutes = (summaryDurationInSeconds - (3600 * hours)) / 60;
		return hours + " hours " + minutes + " mins";
	}

	private static String getDistanceText(int summaryDistanceInMeters) {
		return summaryDistanceInMeters / 1000 + " km";
	}
}
