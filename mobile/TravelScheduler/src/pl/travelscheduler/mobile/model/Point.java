package pl.travelscheduler.mobile.model;

import java.io.Serializable;

public class Point implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2996273372607201071L;
	private String name;
	private double lon;
	private double lat;
	
	public Point(String pointName, double longitude, double latitude)
	{
		name = pointName;
		lon = longitude;
		lat = latitude;
	}

	public String getName() {
		return name;
	}

	public double getLongitude() {
		return lon;
	}

	public double getLatitude() {
		return lat;
	}
}
