package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public class GoogleDirectionsAPI {
	public static JsonNode getRoute(String origin, String destination) {
		try {
			if (origin.contains("("))
				origin = origin.substring(0, origin.indexOf("(")-1);
			if (destination.contains("("))
				destination = destination.substring(0, origin.indexOf("(")-1);
			StringBuilder result = new StringBuilder();
			// get URL content
			String query = "http://maps.googleapis.com/maps/api/directions/json?"
					+ "origin="
					+ origin
					+ "&destination="
					+ destination
					+ "&sensor=false";
			query = URLDecoder.decode(query, "UTF-8");
			URL url = new URL(query);
			System.out.println(url);
			System.out.println("Executing query " + url.getQuery());
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;

			while ((inputLine = br.readLine()) != null) {
				result.append(inputLine + "\n");
			}

			JsonNode jsonNode = Json.parse(result.toString());
			return jsonNode;
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}

	}
}
