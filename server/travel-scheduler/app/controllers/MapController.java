package controllers;

import play.mvc.*;

import views.html.*;

public class MapController extends Controller
{
	public static Result index() {
		return ok(map.render());
	}
}
