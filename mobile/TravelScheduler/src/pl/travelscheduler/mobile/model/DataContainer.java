package pl.travelscheduler.mobile.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DataContainer
{
	public enum SOURCE
	{
		MY_TRAVELS, RANKING;
	}
	
	private static List<Travel> localTravels;
	private static List<Travel> onlineTravels;
	
	private static List<Travel> rankingLocalTravels;
	private static List<Travel> rankingOnlineTravels;
	
	public static void LoadTravels()
	{
		if(localTravels == null)
		{
			localTravels = new ArrayList<Travel>();
		}
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
		
		localTravels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
		localTravels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		localTravels.add(new Travel(krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350));		
		localTravels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
		localTravels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		localTravels.add(new Travel(krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350));
	}
	
	public static void LoadOnlineTravels()
	{		
		if(onlineTravels == null)
		{
			onlineTravels = new ArrayList<Travel>();
		}
		
		//don't add duplicate items
		//TODO: check if item exist in local or online travels list
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);

		onlineTravels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		onlineTravels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
	}
	
	public static void LoadRankingTravels()
	{
		if(rankingLocalTravels == null)
		{
			rankingLocalTravels = new ArrayList<Travel>();
		}
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
		
		rankingLocalTravels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
		rankingLocalTravels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		rankingLocalTravels.add(new Travel(krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350));
	}
	
	public static void LoadRankingOnlineTravels()
	{		
		if(rankingOnlineTravels == null)
		{
			rankingOnlineTravels = new ArrayList<Travel>();
		}
		
		//don't add duplicate items
		//TODO: check if item exist in local or online travels list
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);

		rankingOnlineTravels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		rankingOnlineTravels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
	}
	
	public static List<Travel> getLocalTravels()
	{
		return localTravels;
	}
	
	public static List<Travel> getOnlineTravels()
	{
		return onlineTravels;
	}

	public static Travel getLocalTravel(int index)
	{
		return localTravels.get(index);
	}
	
	public static List<Travel> getRankingLocalTravels()
	{
		return rankingLocalTravels;
	}
	
	public static List<Travel> getRankingOnlineTravels()
	{
		return rankingOnlineTravels;
	}

	public static Travel getRankingLocalTravel(int index)
	{
		return rankingLocalTravels.get(index);
	}

	public static void clearOnlineTravels()
	{
		if(onlineTravels != null)
		{
			onlineTravels.clear();
		}
	}
}
