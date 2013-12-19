package pl.travelscheduler.mobile.model;

import java.io.Serializable;
import java.util.List;

public class Point implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2996273372607201071L;
	private String name;
	private double lon;
	private double lat;
	private int number;
	private PointType type;
	private List<String> images;
	
	
	public Point(String pointName, double longitude, double latitude, int pointNumber,PointType pointType, List<String> pointImages)
	{
		name = pointName;
		lon = longitude;
		lat = latitude;
		number = pointNumber;
		type = pointType;
		images = pointImages;
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
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int num) {
		number = num;
	}
	
	public PointType getType() {
		return type;
	}
	
	public List<String> getImages() {
		return images;
	}
}
