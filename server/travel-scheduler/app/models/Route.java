package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.domain.DistanceData;
import models.domain.DurationData;
import models.domain.PointsPairData;
import models.domain.RouteData;
import models.domain.RoutePointData;
import models.domain.SummaryData;
import models.dto.POIsDTO;
import models.dto.PrefferencesDTO;
import models.dto.RouteDTO;
import play.db.ebean.Model;
import play.libs.Json;
import services.GoogleRouteFinder;
import services.RouteFinder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Route extends Model {

	private static final long serialVersionUID = 1L;
	public static final Finder<Long, Route> find = new Finder<Long, Route>(
			Long.class, Route.class);

	@Id
	@Column(name = "id_route")
	public long id;
	@Column(name = "route_name")
	public String name;
	@Column(name = "route_starting_time")
	public Date startingTime;
	@Column(name = "route_budget")
	public float budget;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_transport_type")
	public TransportType transportType;
	@JsonIgnore
	@OneToMany(mappedBy = "route", cascade=CascadeType.PERSIST)
	public List<PointList> pointList;
	@JsonIgnore
	@OneToMany(mappedBy = "route")
	public List<FavouriteRoute> favouriteRoutes;

	public JsonNode asJson() {
		return Json.toJson(this);
	}

	public static RouteDTO schedule(List<Point> points, PrefferencesDTO prefferences) {
		RouteFinder finder = new GoogleRouteFinder();
		int summaryDistanceInMeters = 0;
		int summaryDurationInSeconds = 0;
		int currentDistanceInMeters = 0;

		RouteDTO routeDTO = new RouteDTO();
		routeDTO.summary = new SummaryData();
		routeDTO.summary.distance = new DistanceData();
		routeDTO.summary.duration = new DurationData();
		routeDTO.points = new ArrayList<RoutePointData>();
		routeDTO.routes = new ArrayList<RouteData>();
		routeDTO.pois = new HashSet<POI>();
		
		List<POI> pois;

		Point originPoint = points.get(0);
		String origin = originPoint.latitude+","+originPoint.longitude;

		RoutePointData originRoutePoint = createRoutePointData(originPoint);
		routeDTO.points.add(originRoutePoint);

		for (int i = 1; i < points.size(); i++) {
			Point destinationPoint = points.get(i);
			String destination = destinationPoint.latitude+","+destinationPoint.longitude;
			pois = getPOIs(destinationPoint);
			for (POI poi : pois) {
				routeDTO.pois.add(poi);
			}
			
			RouteData route = finder.getRoute(new PointsPairData(origin, destination));
			
			if(route.distance.value + summaryDistanceInMeters > prefferences.kmPerDay * 1000) {
				Point waypoint = finder.getAlternativeWaypoint(new PointsPairData(origin, destination), prefferences.kmPerDay * 1000 - currentDistanceInMeters);
				pois = getPOIs(waypoint);
				for (POI poi : pois) {
					routeDTO.pois.add(poi);
				}
				route = finder.getRoute(new PointsPairData(origin, waypoint.latitude+","+waypoint.longitude ));
				routeDTO.points.add(createRoutePointData(Point.getById(waypoint.id)));
				routeDTO.routes.add(route);
				currentDistanceInMeters += route.duration.value;
				summaryDurationInSeconds += route.duration.value;
				summaryDistanceInMeters += route.distance.value;
				route = finder.getRoute(new PointsPairData(waypoint.latitude+","+waypoint.longitude , destination ));
			}
			
			RoutePointData destinationRoutePoint = createRoutePointData(destinationPoint);
			routeDTO.points.add(destinationRoutePoint);
			
			routeDTO.routes.add(route);

			currentDistanceInMeters += route.duration.value;
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

	private static List<POI> getPOIs(Point originPoint) {
		List<Point> pois = Point.getPOIs(originPoint.latitude, originPoint.longitude, 20000, false);
		List<POI> poiList = new ArrayList<>();
		for (Point point : pois) {
			POI poi = new POI();
			poi.id = point.id;
			poiList.add(poi);
		}
		return poiList;
	}

	private static RoutePointData createRoutePointData(Point originPointAsJson) {
		RoutePointData pointData1 = new RoutePointData();
		pointData1.id = originPointAsJson.id;
		pointData1.type = originPointAsJson.type.name;
		pointData1.name = originPointAsJson.name;
		pointData1.lat = String.valueOf(originPointAsJson.latitude);
		pointData1.lng = String.valueOf(originPointAsJson.longitude);
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

	public static Route getById(long id) {
		return find.byId(id);
	}

	public String toString1() {
		return String.format("['id'->%d, 'name'->%s, 'start'->%s, 'transport'->%s, 'budget'->%f, 'points count'->%s]", this.id, this.name, this.startingTime, this.transportType.toString1(), this.budget, this.pointList.size());
	}
}
