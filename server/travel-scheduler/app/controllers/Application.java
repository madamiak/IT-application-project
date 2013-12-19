package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import models.FavouriteRoute;
import models.Point;
import models.PointList;
import models.Route;
import models.TransportType;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result authenticate(String login, String password) {
		return ok(User.authenticate(login, password).toJson());
	}

	public static Result register() {
		User user = Json.fromJson(request().body().asJson(), User.class);
		return ok(User.register(user).toJson());
	}

	public static Result saveTrip() throws ParseException,
			NumberFormatException, JSONException {
		JsonNode json = request().body().asJson();
		FavouriteRoute favouriteRoute = new FavouriteRoute();
		favouriteRoute.user = User.getByLogin(json.findValue("login").asText());
		Route route = new Route();
		route.budget = Float.parseFloat(json.findPath("route")
				.findValuesAsText("budget").get(0));
		route.name = json.findPath("route").findValuesAsText("name").get(0);
		route.startingTime = new SimpleDateFormat("YYYY-MM-DD HH:mm",
				Locale.ENGLISH).parse(json.findPath("route")
				.findValuesAsText("start_at").get(0));
		route.pointList = new ArrayList<PointList>();
		route.transportType = TransportType.getByName("driving");
		
		JSONArray idArray = new JSONArray(json.findValue("points").toString());
		int[] pointIds = new int[idArray.length()];
		for (int i = 0; i < idArray.length(); i++) {
			String string = idArray.getJSONObject(i).get("id").toString();
			pointIds[i] = Integer.parseInt(string);
			PointList point = new PointList();
			point.number = i;
			point.point = Point.getById(pointIds[i]);
			route.pointList.add(point);
		}
		favouriteRoute.route = route;
		favouriteRoute.save();
		return ok(favouriteRoute.asJson());
	}
	
	public static Result getAllTripsByUserId(int userId) {
		return ok(Json.toJson(FavouriteRoute.getAllByUserId(userId)));
	}
}
