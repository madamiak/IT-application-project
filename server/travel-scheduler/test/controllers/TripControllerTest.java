package controllers;


import org.junit.Ignore;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import play.test.Helpers;

public class TripControllerTest {

	@Ignore
	@Test
	public void shouldReturnScheduledTrips() {
		FakeApplication fakeApplication = Helpers.fakeApplication();
		Helpers.running(fakeApplication, new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = new FakeRequest();
				String ids = "{"
						+ "\"ids\": [ "
						+ "{\"id\": \"11960\"}, " //Warszawa
						+ "{\"id\": \"11785\"}, " //Szczecin
						+ "{\"id\": \"10599\"}, " //Kielce
						+ "{\"id\": \"12117\"}, " //Zakopane
						+ "{\"id\": \"10871\"} "  //Lublin
						+ "]"
						+ "} ";
				String prefs = "{"
						+ "    \"numberOfPeople\" : \"2\","
						+ "    \"startDate\" : \"10/08/2013\","
						+ "    \"endDate\" : \"16/08/2013\","
						+ "    \"budget\" : \"0.8\","
						+ "    \"kmPerDay\" : \"12300\","
						+ "    \"suggest\" : \"true\""
						+ "}";
				Result result = Helpers.callAction(
						controllers.routes.ref.TripController.getScheduledTrip(ids, prefs),
						fakeRequest);
				System.out.println(Helpers.contentAsString(result));
			}
		});
		Helpers.stop(fakeApplication);
	}

}
