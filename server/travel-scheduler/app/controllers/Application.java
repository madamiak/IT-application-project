package controllers;

import models.authentication.Response;
import play.mvc.*;
import services.authentication.AuthenticationService;

import views.html.*;

public class Application extends Controller
{

	public static Result index()
	{
		return ok(index.render("Your new application is ready."));
	}
	
	public static Result authenticate(String login, String password) {
		final AuthenticationService authenticationService = new AuthenticationService();
		final Response response = authenticationService.authenticate(login, password);
		return ok(response.toJson());
	}
	
	public static Result register(String userData) {
		return ok();
	}

}
