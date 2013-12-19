package controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import models.FavouriteRoute;
import models.Groups;
import models.Point;
import models.PointList;
import models.PointType;
import models.Route;
import models.TransportType;
import models.User;

import org.junit.Ignore;
import org.junit.Test;

import play.mvc.*;
import play.test.*;
import play.libs.Json;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

public class ApplicationTest {

	@Ignore
	@Test
	public void test() {
		FakeRequest fakeRequest = new FakeRequest();
		fakeRequest.withHeader("Content-Type", "application/json");
		JsonNode json = Json
				.parse("{\"login\" : \"maciej@gmail.com\", \"route\" : { \"name\" : \"route name\", \"start_at\" : \"2013-12-17 22:50\",	\"budget\" : 250.00, \"points\" : [{ \"id\" : 527}]	}}}");
		fakeRequest.withJsonBody(json);
		Result result = Helpers.callAction(
				controllers.routes.ref.Application.saveTrip(), fakeRequest);
		System.out.println(Helpers.contentAsString(result));
	}
	
	@Test
	public void shouldStoreUser() throws Exception {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				Ebean.beginTransaction();
				User user = new User();
				user.email = "marcel@asd.asd";
				user.group = new Groups();
				user.group.name = "test group";
				user.password = "passwd";
				user.name = "marcel";
				user.surname = "sobon";
				user.save();
				assertNotNull(User.getByLogin("marcel@asd.asd"));
				Ebean.rollbackTransaction();
			}
		});
		Helpers.stop(fakeApplication);
	}
	
	@Test
	public void shouldStoreRoute() throws Exception {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				Ebean.beginTransaction();
				User user = new User();
				user.email = "marcel@asd.asd";
				user.group = new Groups();
				user.group.name = "test group";
				user.password = "passwd";
				user.name = "marcel";
				user.surname = "sobon";
				user.save();
				assertNotNull("user was not saved", user.id);
				
				Point point = new Point();
				point.latitude = 15;
				point.longitude = 15;
				point.name = "dziura";
				point.type = new PointType();
				point.type.name = "nothing";
				point.type.save();
				point.save();
				assertNotNull("point1 was not saved", point.id);
				
				Point point2 = new Point();
				point2.latitude = 15;
				point2.longitude = 15;
				point2.name = "dziura";
				point2.type = new PointType();
				point2.type.name = "nothing";
				point2.type.save();
				point2.save();
				assertNotNull("point2 was not saved", point2.id);
				
				FavouriteRoute favRoute = new FavouriteRoute();
				favRoute.user = User.getByLogin(user.email);
				
				Route route = new Route();
				route.budget = 250.00f;
				route.name = "my trip";
				route.startingTime = new Date();
				route.transportType = new TransportType();
				route.transportType.name = "ciapong";
				route.transportType.save();
				route.pointList = new ArrayList<PointList>();
				
				PointList point0 = new PointList();
				point0.number = 0;
				point0.point = Point.getById(point.id);
//				point0.save();
				
				PointList point1 = new PointList();
				point1.number = 1;
				point1.point = Point.getById(point2.id);
//				point1.save();
				
				route.pointList.add(point0);
				route.pointList.add(point1);
//				route.save();
				
				favRoute.route = route;
				favRoute.save();
				
				assertNotNull("favourite route was not saved", FavouriteRoute.getById(favRoute.id));
				assertNotNull("route was not saved", route.id);
				assertNotNull("ppoint0 was not saved", point0.id);
				assertNotNull("point1 was not saved", point1.id);
				Ebean.rollbackTransaction();
			}
		});
		Helpers.stop(fakeApplication);
	}

}
