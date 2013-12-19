package pl.travelscheduler.mobile.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Travel implements Serializable
{
	private static final long serialVersionUID = 7833443817581881429L;
	
	public class SortBasedOnNumber implements Comparator
	{
		public int compare(Object o1, Object o2) 
		{
	
		    Point dd1 = (Point)o1;
		    Point dd2 = (Point)o2;
		    return dd1.getNumber()-dd2.getNumber();
		}

	}
	
	private int id;
	private String name;
	private List<Point> points;
	private Rating rating;
	private Calendar startingTime;
	private TransportType transportType;
	private double distance;
	private double budget;

	public Travel(int id, List<Point> pointList,
			Rating rating, Calendar startingTime, TransportType transportType,
			double distance, double budget, String name) 
	{
		this.id = id;
		this.points = pointList;
		Collections.sort(this.points, new SortBasedOnNumber());
		this.rating = rating;
		this.startingTime = startingTime;
		this.transportType = transportType;
		this.distance = distance;
		this.budget = budget;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Travel)
		{
			Travel obj = (Travel)o;
			return this.id == obj.getId();	
		}
		else
		{
			return super.equals(o);
		}
	}
	
	public int getId()
	{
		return id;
	}

	public String getSource()
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return points.get(0).getName();
	}
	
	public String getDestination()
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return points.get(points.size()-1).getName();
	}

	public double getSrcLon() 
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return points.get(0).getLongitude();
	}

	public double getSrcLat() 
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return  points.get(0).getLatitude();
	}

	public double getDstLon() 
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return points.get(points.size()-1).getLongitude();
	}

	public double getDstLat() 
	{
		Collections.sort(this.points, new SortBasedOnNumber());
		return points.get(points.size()-1).getLatitude();
	}

	public boolean hasPois() 
	{
		return points.size()>2;
	}
	
	public List<Point> getPoints()
	{
		return points;
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

	public String getName()
	{
		return name;
	}

	public double getBudget() {
		return budget;
	}
}
