package services;

import models.domain.PointsPairData;
import models.domain.RouteData;

public interface RouteFinder {
	
	RouteData getRoute(PointsPairData points);
	
}
