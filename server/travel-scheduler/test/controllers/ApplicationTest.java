package controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//	@Ignore
	@Test
	public void shouldReturnAllUserTrips() throws Exception {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				Ebean.beginTransaction();
				
				FakeRequest fakeReq = Helpers.fakeRequest();
				fakeReq.withHeader("Content-Type", "application/json");
				String tripAsJson = "{"
						+ "    \"login\" : \"maciej@gmail.com\","
						+ "    \"route\" : {"
						+ "        \"name\" : \"first trip\","
						+ "        \"start_at\": \"2013-12-19 20:00\","
						+ "        \"budget\" : \"250.00\","
						+ "        \"points\" : ["
						+ "            {"
						+ "                \"id\" : \"11975\""
						+ "            },"
						+ "            {"
						+ "                \"id\" : \"11978\""
						+ "            }"
						+ "        ]"
						+ "    }"
						+ "}";
				fakeReq.withJsonBody(Json.parse(tripAsJson ));
				Result result = Helpers.callAction(controllers.routes.ref.Application.saveTrip(), fakeReq );
				System.out.println(Helpers.contentAsString(result));
				
				fakeReq = Helpers.fakeRequest();
				fakeReq.withHeader("Content-Type", "application/json");
				tripAsJson = "{"
						+ "    \"login\" : \"maciej@gmail.com\","
						+ "    \"route\" : {"
						+ "        \"name\" : \"first trip\","
						+ "        \"start_at\": \"2013-12-19 20:00\","
						+ "        \"budget\" : \"250.00\","
						+ "        \"points\" : ["
						+ "            {"
						+ "                \"id\" : \"11979\""
						+ "            },"
						+ "            {"
						+ "                \"id\" : \"11980\""
						+ "            }"
						+ "        ]"
						+ "    }"
						+ "}";
				fakeReq.withJsonBody(Json.parse(tripAsJson ));
				result = Helpers.callAction(controllers.routes.ref.Application.saveTrip(), fakeReq );
				System.out.println(Helpers.contentAsString(result));
				
				result = Helpers.callAction(controllers.routes.ref.Application.getAllTripsByUserId(User.getByLogin("maciej@gmail.com").id));
				System.out.println(Helpers.contentAsString(result));
				
				Ebean.rollbackTransaction();
			}
		});
		Helpers.stop(fakeApplication);
	}
	
	@Ignore
	@Test
	public void shouldAuthenticateUserAfterRegistering() throws Exception {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				Ebean.beginTransaction();
				FakeRequest fakeRequest = new FakeRequest();
				fakeRequest.withHeader("Content-Type", "application/json");
				User user = new User();
				user.name = "D";
				user.surname = "Z";
				user.email = "D@Z.pl";
				user.password = "DFGHJK";
				user.group = Groups.find.byId(64L);
				fakeRequest.withJsonBody(Json.toJson(user));
				Helpers.callAction(controllers.routes.ref.Application.register(), fakeRequest);
				
				fakeRequest = new FakeRequest();
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", user.email);
				params.put("password", user.password);
				fakeRequest.withFormUrlEncodedBody(params );
				Result result = Helpers.callAction(controllers.routes.ref.Application.authenticate(), fakeRequest);
				System.out.println(Helpers.contentAsString(result));
				
				Ebean.rollbackTransaction();
			}
		});
		Helpers.stop(fakeApplication);
	}

	@Ignore
	@Test
	public void controllerShouldSaveTrip() {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest();
				fakeRequest.withHeader("Content-Type", "application/json");
				JsonNode json = Json
						.parse("{\"login\" : \"maciej@gmail.com\", \"route\" : { \"name\" : \"route name\", \"start_at\" : \"2013-12-17 22:50\",	\"budget\" : 250.00, \"points\" : [{ \"id\" : 9993}]	}}}");
				fakeRequest.withJsonBody(json);
				Result result = Helpers.callAction(
						controllers.routes.ref.Application.saveTrip(),
						fakeRequest);
				System.out.println(Helpers.contentAsString(result));
			}
		});
		Helpers.stop(fakeApplication);
	}

	@Ignore
	@Test
	public void controllerShouldReturnTripsForUser() {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest();
				Result result = Helpers.callAction(
						controllers.routes.ref.Application
								.getAllTripsByUserId(67), fakeRequest);
				System.out.println(Helpers.contentAsString(result));
			}
		});
		Helpers.stop(fakeApplication);
	}

	@Ignore
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

	@Ignore
	@Test
	public void shouldStoreRouteManualScenario() throws Exception {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				Ebean.beginTransaction();
				User user = new User();
				user.email = "marcel@sobon.pl";
				user.group = new Groups();
				user.group.name = "testing group";
				user.password = "passwd";
				user.name = "marcel";
				user.surname = "sobon";
				user.save();
				assertNotNull("user was not saved", user.id);

				Point point = new Point();
				point.latitude = 53.803108f;
				point.longitude = 21.556000f;
				point.name = "dziura";
				point.type = new PointType();
				point.type.name = "nothing";
				point.type.save();
				point.save();
				assertNotNull("point1 was not saved", point.id);

				Point point2 = new Point();
				point2.latitude = 52.226173f;
				point2.longitude = 21.014576f;
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

				PointList point1 = new PointList();
				point1.number = 1;
				point1.point = Point.getById(point2.id);

				route.pointList.add(point0);
				route.pointList.add(point1);

				favRoute.route = route;
				favRoute.save();

				FavouriteRoute result = FavouriteRoute.getById(favRoute.id);
				assertNotNull("favourite route was not saved", result);
				assertNotNull("route was not saved", route.id);
				assertNotNull("point0 was not saved", point0.id);
				assertNotNull("point1 was not saved", point1.id);

				List<FavouriteRoute> trips = FavouriteRoute
						.getAllByUserId(user.id);
				assertEquals(result, trips.get(0));
				System.out.println(result);
				Ebean.rollbackTransaction();
			}
		});
		Helpers.stop(fakeApplication);
	}

}
