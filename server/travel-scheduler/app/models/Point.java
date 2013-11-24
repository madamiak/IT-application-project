package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Point extends Model
{
	private static final long serialVersionUID = 1L;
	private static final String DESTINATION_HAS_BEEN_ADDED = "destination has been added";
	private static final String DESTINATION_ALREADY_EXISTS = "destination already exists";

	public static final Finder<Long, Point> find = new Finder<Long, Point>(Long.class, Point.class);

	@Id
	public int idpoints;
	public String point_name;
	public float point_longitude;
	public float point_langitude;
	public int point_type;

	public static JsonNode getByName(String name)
	{
		List<Point> points=find.where().icontains("point_name", name).findList();
		
		DestinationsDTO dto = new DestinationsDTO();
		dto.destinations = new ArrayList<Destination>();
		for(int i=0; i<points.size(); i++){
			Destination dest = new Destination();
			dest.id=points.get(i).idpoints;
			dest.value=points.get(i).point_name;
			dto.destinations.add(dest);
		}
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static Response add(Point point)
	{
		if (find.where().eq("point_name", point.point_name).findUnique() == null)
		{
			point.save();
			return addOk(point);
		}
		return addFailed(point);
	}

	public static void delete(Point destination)
	{
		final Point found = find.where().eq("idpoints", destination.idpoints).findUnique();
		if (found != null)
		{
			found.delete();
		}
	}

	@Override
	public String toString()
	{
		return String.format("[idpoints: %s, point_name: %s, point_longitude: %s, point_langitude: %s, point_type: %s]", idpoints, point_name, point_longitude, point_langitude, point_type);
	}

	public JsonNode toJson()
	{
		return Json.toJson(this);
	}

	private static Response addOk(Point destination)
	{
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = DESTINATION_HAS_BEEN_ADDED;
		return response;
	}

	private static Response addFailed(Point destination)
	{
		final Response response = new Response();
		response.code = ResponseCode.FAILED;
		response.message = DESTINATION_ALREADY_EXISTS;
		return response;
	}

}
