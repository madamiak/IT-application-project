package services;

import models.Point;
import models.domain.PointsPairData;
import models.domain.RouteData;

public interface RouteFinder {
	
	RouteData getRoute(PointsPairData points);

	Point getAlternativeWaypoint(PointsPairData points, int meters);
	
}
