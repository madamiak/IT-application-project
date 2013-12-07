package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.dto.DestinationDTO;
import models.dto.DestinationsDTO;
import models.dto.POIDTO;
import models.dto.POIsDTO;
import models.user.Response;
import models.user.ResponseCode;
import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Point extends Model {
	private static final long serialVersionUID = 1L;
	private static final String DESTINATION_HAS_BEEN_ADDED = "destination has been added";
	private static final String DESTINATION_ALREADY_EXISTS = "destination already exists";

	public static final Finder<Long, Point> find = new Finder<Long, Point>(
			Long.class, Point.class);

	@Id
	@Column(name = "idpoints")
	public int id;
	@Column(name = "point_name")
	public String name;
	@Column(name = "point_longitude")
	public float longitude;
	@Column(name = "point_langitude")
	public float langitude;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "point_type")
	public PointType type;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "point_image")
	public Set<Image> images = new HashSet<>();

	public static JsonNode getById(long id) {
		return Json.toJson(find.where().eq("id", id).findUnique());
	}

	public static JsonNode getDestinationsByName(String name) {
		List<Point> points = find.where().icontains("point_name", name)
				.findList();

		DestinationsDTO dto = new DestinationsDTO();
		dto.destinations = new ArrayList<Destination>();
		for (int i = 0; i < points.size(); i++) {
			Destination dest = new Destination();
			dest.id = points.get(i).id;
			dest.value = points.get(i).name;
			dto.destinations.add(dest);
		}
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static JsonNode getPOIs() {
		PointType pt = PointType.getByName("POI");
		List<Point> points = find.where().eq("point_type", pt.id).findList();

		POIsDTO dto = new POIsDTO();
		dto.pois = new ArrayList<POI>();
		for (int i = 0; i < points.size(); i++) {
			POI dest = new POI();
			dest.id = points.get(i).id;
			dto.pois.add(dest);
		}
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static JsonNode getDestinationById(int id) {
		Point p = find.where().eq("idpoints", id).findUnique();

		DestinationDTO dto = new DestinationDTO();
		dto.id = p.id;
		dto.value = p.name;
		dto.details = "";
		dto.longn = p.longitude;
		dto.latt = p.langitude;
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static JsonNode getPOIById(int id) {
		Point p = find.where().eq("idpoints", id).findUnique();

		POIDTO dto = new POIDTO();
		dto.id = p.id;
		dto.value = p.name;
		dto.description = "";
		dto.longn = p.longitude;
		dto.latt = p.langitude;
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static Response add(Point point) {
		if (find.where().eq("point_name", point.name).findUnique() == null) {
			point.save();
			return addOk(point);
		}
		return addFailed(point);
	}

	public static void delete(Point destination) {
		final Point found = find.where().eq("idpoints", destination.id)
				.findUnique();
		if (found != null) {
			found.delete();
		}
	}

	@Override
	public String toString() {
		return String
				.format("[idpoints: %s, point_name: %s, point_longitude: %s, point_langitude: %s, point_type: %s]",
						id, name, longitude, langitude, type);
	}

	public JsonNode toJson() {
		return Json.toJson(this);
	}

	private static Response addOk(Point destination) {
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = DESTINATION_HAS_BEEN_ADDED;
		return response;
	}

	private static Response addFailed(Point destination) {
		final Response response = new Response();
		response.code = ResponseCode.FAILED;
		response.message = DESTINATION_ALREADY_EXISTS;
		return response;
	}

}
