package controllers;

import models.User;
import play.mvc.*;

import views.html.*;

public class Application extends Controller
{

	public static Result index()
	{
		return ok(index.render("Your new application is ready."));
	}
	
	public static Result authenticate(String login, String password) {
		return ok(User.authenticate(login, password).toJson());
	}
	
	public static Result register(String userData) {
		return ok();
	}

	public static Result getAllUsers() {
		return ok("size: " + User.find.all().size());
	}
	
	public static Result getUserById(int id) {
		return ok(User.find.where().eq("id", id).findUnique().toString());
	}
}
