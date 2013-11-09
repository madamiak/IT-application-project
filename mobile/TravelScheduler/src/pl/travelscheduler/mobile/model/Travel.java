package pl.travelscheduler.mobile.model;

import java.io.Serializable;
import java.util.Calendar;

public class Travel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7833443817581881429L;
	private Point source;
	private Point destination;
	private Point[] middlePoints;
	private Rating rating;
	private Calendar startingTime;
	private TransportType transportType;
	private double distance;

	public Travel(Point startingPoint, Point finishPont, Point[] middlePoints,
			Rating rating, Calendar startingTime, TransportType transportType,
			double distance) 
	{
		this.source = startingPoint;
		this.destination = finishPont;
		this.middlePoints = middlePoints;
		this.rating = rating;
		this.startingTime = startingTime;
		this.transportType = transportType;
		this.distance = distance;
	}

	public String getSource() 
	{
		return source.getName();
	}

	public String getDestination() 
	{
		return destination.getName();
	}

	public double getSrcLon() 
	{
		return source.getLongitude();
	}

	public double getSrcLat() 
	{
		return source.getLatitude();
	}

	public double getDstLon() 
	{
		return destination.getLongitude();
	}

	public double getDstLat() 
	{
		return destination.getLatitude();
	}

	public boolean hasPois() 
	{
		return middlePoints != null && middlePoints.length > 0;
	}
	
	public Point[] getMiddlePoints()
	{
		return middlePoints;
	}
	
	public Rating getRating()
	{
		return rating;
	}
	
	public Calendar getStartingTime()
	{
		return startingTime;
	}
	
	public TransportType getTransportType()
	{
		return transportType;
	}
	
	public double getDistance()
	{
		return distance;
	}
}
