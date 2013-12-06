/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package controllers;

import models.Point;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller for testing connection with DB
 */
public class TestController extends Controller {
	
	public static Result test(String req) {
		System.out.println("Hello"+req);
		return ok(Point.getPOIs());
	}

}
