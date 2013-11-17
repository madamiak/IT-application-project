package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Destination extends Model
{
	private static final long serialVersionUID = 1L;
	private static final String DESTINATION_HAS_BEEN_ADDED = "destination has been added";
	private static final String DESTINATION_ALREADY_EXISTS = "destination already exists";

	public static final Finder<Long, Destination> find = new Finder<Long, Destination>(Long.class, Destination.class);

	@Id
	public long id;
	public String value;

	public static JsonNode getByName(String name)
	{
		DestinationsDTO dto = new DestinationsDTO();
		dto.destinations=find.where().icontains("value", name).findList();
		JsonNode json = Json.toJson(dto);
		return json;
	}

	public static Response add(Destination destination)
	{
		if (find.where().eq("value", destination.value).findUnique() == null)
		{
			destination.save();
			return addOk(destination);
		}
		return addFailed(destination);
	}

	public static void delete(Destination destination)
	{
		final Destination found = find.where().eq("id", destination.id).findUnique();
		if (found != null)
		{
			found.delete();
		}
	}

	@Override
	public String toString()
	{
		return String.format("[id: %s, value: %s]", id, value);
	}

	public JsonNode toJson()
	{
		return Json.toJson(this);
	}

	private static Response addOk(Destination destination)
	{
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = DESTINATION_HAS_BEEN_ADDED;
		return response;
	}

	private static Response addFailed(Destination destination)
	{
		final Response response = new Response();
		response.code = ResponseCode.FAILED;
		response.message = DESTINATION_ALREADY_EXISTS;
		return response;
	}

}
