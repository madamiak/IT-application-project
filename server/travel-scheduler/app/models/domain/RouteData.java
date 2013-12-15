package models.domain;

import play.libs.Json;

public class RouteData {

	public DurationData duration;
	public DistanceData distance;
	public String polyline;
	
	@Override
	public String toString() {
		return Json.toJson(this).toString();
	}

}
