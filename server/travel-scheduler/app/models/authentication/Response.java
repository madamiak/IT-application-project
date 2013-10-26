package models.authentication;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

public class Response
{
	public ResponseCode code;
	public String message;
	public ResponseData data;

	public JsonNode toJson()
	{
		return Json.toJson(this);
	}
}
