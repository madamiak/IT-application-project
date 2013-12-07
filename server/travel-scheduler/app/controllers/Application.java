package controllers;

import models.Image;
import models.Point;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller
{

	public static Result index()
	{
		return ok(index.render("Your new application is ready."));
	}
	
	public static Result authenticate(String login, String password) {
		return ok(User.authenticate(login, password).toJson());
	}
	
	public static Result register() {
		User user = Json.fromJson(request().body().asJson(), User.class);
		return ok(User.register(user).toJson());
	}
	
	public static Result getAllUsers() {
		return ok("size: " + User.find.all().size());
	}
	
	public static Result getUserById(int id) {
		return ok(User.find.where().eq("id", id).findUnique().toString());
	}
	
	public static Result getPointById(int id) {
		return ok(Point.getById(id));
	}
	
	public static Result getImageById(int id) {
		return ok(Image.getById(id));
	}

}
