package models.dto;

import java.util.List;

import models.domain.RouteData;
import models.domain.RoutePointData;
import models.domain.SummaryData;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public class RouteDTO {
	public SummaryData summary;
	public List<RoutePointData> points;
	public List<RouteData> routes;

	public JsonNode asJson() {
		return Json.toJson(this);
	}

}
