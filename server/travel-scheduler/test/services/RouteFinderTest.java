package services;

import static org.junit.Assert.*;
import models.domain.PointsPairData;
import models.domain.RouteData;

import org.junit.Ignore;
import org.junit.Test;

import play.test.FakeApplication;
import play.test.Helpers;

public class RouteFinderTest {

	@Ignore
	@Test
	public void shouldFindRouteFromCracowToWarsaw() {
		RouteFinder finder = new GoogleRouteFinder();
		String origin = "Krakow";
		String destination = "Warszawa";
		RouteData route = finder.getRoute(new PointsPairData(origin, destination));
		assertNotNull(route);
		assertTrue(route.duration.text.contains("3 hours"));
		assertTrue(13000 < route.duration.value);
		assertEquals("292 km", route.distance.text);
		assertTrue(292000 < route.distance.value);
		assertNotNull(route.polyline);
	}

	@Ignore
	@Test
	public void controllerShouldFindRouteFromCracowToWarsaw() throws Exception {
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
