package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import models.FavouriteRoute;
import models.Point;
import models.PointList;
import models.Route;
import models.TransportType;
import models.User;

import org.json.JSONArray;
import org.json.JSONException;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.index;
import actions.CorsAction;

import com.fasterxml.jackson.databind.JsonNode;

@With(CorsAction.class)
public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result enableCors(String all) {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Allow", "*");
		response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
		return ok();
	}

	public static Result authenticate() {
		String username;
		String password;
		DynamicForm requestData = Form.form().bindFromRequest();
		username = requestData.get("username");
		password = requestData.get("password");
		System.out.println(username+password);
		return ok(User.authenticate(username, password).toJson());
	}

	public static Result register() {
		User user = Json.fromJson(request().body().asJson(), User.class);
		return ok(User.register(user).toJson());
	}

	public static Result saveTrip() throws ParseException, NumberFormatException, JSONException {
		JsonNode json = request().body().asJson();
		FavouriteRoute favouriteRoute = new FavouriteRoute();
		favouriteRoute.user = User.getByLogin(json.findValue("login").asText());
		Route route = new Route();
		route.budget = Float.parseFloat(json.findPath("route").findValuesAsText("budget").get(0));
		route.name = json.findPath("route").findValuesAsText("name").get(0);
		route.startingTime = new SimpleDateFormat("YYYY-MM-DD HH:mm", Locale.ENGLISH).parse(json.findPath("route").findValuesAsText("start_at").get(0));
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
		return ok(Json.toJson(FavouriteRoute.toDTO(FavouriteRoute.getAllByUserId(userId))));
	}
}
