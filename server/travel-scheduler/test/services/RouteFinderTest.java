package services;

import static org.junit.Assert.*;
import models.Point;
import models.domain.PointsPairData;
import models.domain.RouteData;

import org.junit.Ignore;
import org.junit.Test;

import play.test.FakeApplication;
import play.test.Helpers;

public class RouteFinderTest {

	@Test
	public void test() {
		RouteFinder finder = new GoogleRouteFinder();
		String origin = "Krakow";
		String destination = "Warszawa";
		RouteData route = finder.getRoute(new PointsPairData(origin, destination));
		assertNotNull(route);
		assertEquals("3 hours 45 mins", route.duration.text);
		assertTrue(13000 < route.duration.value);
		assertEquals("292 km", route.distance.text);
		assertTrue(292000 < route.distance.value);
		assertNotNull(route.polyline);
	}
	
	@Test
	public void testName() throws Exception {
		FakeApplication app = Helpers.fakeApplication();
		Helpers.running(app, new Runnable() {
			
			@Override
			public void run() {
				RouteFinder finder = new GoogleRouteFinder();
				String origin = "Krakow";
				String destination = "Warszawa";
				System.out.println(finder.getAlternativeWaypoint(new PointsPairData(origin, destination), 230*1000));
			}
		});
		Helpers.stop(app);
	}

}
