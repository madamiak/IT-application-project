package services;

import static org.junit.Assert.*;
import models.domain.PointsPairData;
import models.domain.RouteData;

import org.junit.Test;

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

}
