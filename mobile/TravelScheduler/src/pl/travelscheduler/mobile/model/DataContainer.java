package pl.travelscheduler.mobile.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.helpers.FilesHelper;

public class DataContainer
{
	public enum SOURCE
	{
		MY_TRAVELS, RANKING;
	}
	
	private static ArrayList<Travel> localTravels;
	private static ArrayList<Travel> onlineTravels;
	
	private static ArrayList<Travel> rankingLocalTravels;
	private static ArrayList<Travel> rankingOnlineTravels;
	
	public static void LoadTravels(Activity activity)
	{
		localTravels = (ArrayList<Travel>) FilesHelper.loadObject(activity, AppParameters.FILE_MY_TRAVELS, 
				AppParameters.FOLDER_TRAVELS);
		if(localTravels == null)
		{
			localTravels = new ArrayList<Travel>();
		}
	}
	
	public static void LoadOnlineTravels()
	{		
		if(onlineTravels == null)
		{
			onlineTravels = new ArrayList<Travel>();
		}
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
		
		Travel t1 = new Travel(1, wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280);
		addOnlineTravel(t1);
		Travel t2 = new Travel(2, wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180);
		addOnlineTravel(t2);
		Travel t3 = new Travel(3, krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350);
		addOnlineTravel(t3);
		Travel t4 = new Travel(4, wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280);
		addOnlineTravel(t4);
		Travel t5 = new Travel(5, wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180);
		addOnlineTravel(t5);
		Travel t6 = new Travel(6, krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350);
		addOnlineTravel(t6);
		Travel t7 = new Travel(7, wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180);
		addOnlineTravel(t7);
		Travel t8 = new Travel(8, wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280);
		addOnlineTravel(t8);
	}
	
	public static void LoadRankingTravels(Activity activity)
	{
		rankingLocalTravels = (ArrayList<Travel>) FilesHelper.loadObject(activity, AppParameters.FILE_RANKING, 
				AppParameters.FOLDER_TRAVELS);
		if(rankingLocalTravels == null)
		{
			rankingLocalTravels = new ArrayList<Travel>();
		}
	}
	
	public static void LoadRankingOnlineTravels()
	{		
		if(rankingOnlineTravels == null)
		{
			rankingOnlineTravels = new ArrayList<Travel>();
		}
		
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);

		Travel t9 = new Travel(9, wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280);
		addRankingOnlineTravel(t9);
		Travel t10 = new Travel(10, wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180);
		addRankingOnlineTravel(t10);
		Travel t11 = new Travel(11, krakow, poznan, null, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350);
		addRankingOnlineTravel(t11);		
		Travel t12 = new Travel(12, wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180);
		addRankingOnlineTravel(t12);
		Travel t13 = new Travel(13, wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280);
		addRankingOnlineTravel(t13);
	}
	
	private static void addRankingOnlineTravel(Travel t)
	{
		if(!rankingLocalTravels.contains(t) && !rankingOnlineTravels.contains(t))
		{
			rankingOnlineTravels.add(t);
		}
	}
	
	public static void addRankingLocalTravel(Travel t, Activity activity)
	{
		if(!rankingLocalTravels.contains(t))
		{
			rankingLocalTravels.add(t);
			saveRankingTravels(activity);
		}
	}
	
	private static void addOnlineTravel(Travel t)
	{
		if(!localTravels.contains(t) && !onlineTravels.contains(t))
		{
			onlineTravels.add(t);
		}
	}
	
	public static void addLocalTravel(Travel t, Activity activity)
	{
		if(!localTravels.contains(t))
		{
			localTravels.add(t);
			saveTravels(activity);
		}
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
	
	public static Travel getRankingOnlineTravel(int index)
	{
		return rankingOnlineTravels.get(index);
	}

	public static void clearOnlineTravels()
	{
		if(onlineTravels != null)
		{
			onlineTravels.clear();
		}
	}

	public static Travel getOnlineTravel(int position)
	{
		return onlineTravels.get(position);
	}

	public static void addTravelToLocal(Travel selectedTravel, Activity activity)
	{
		addLocalTravel(selectedTravel, activity);
		onlineTravels.remove(selectedTravel);
	}

	public static void addRankingTravelToLocal(Travel selectedTravel, Activity activity)
	{
		addRankingLocalTravel(selectedTravel, activity);
		rankingOnlineTravels.remove(selectedTravel);
	}
	
	private static void saveTravels(Activity activity)
	{
		FilesHelper.saveObject(activity, localTravels, AppParameters.FILE_MY_TRAVELS, 
				AppParameters.FOLDER_TRAVELS);
	}
	
	private static void saveRankingTravels(Activity activity)
	{
		FilesHelper.saveObject(activity, rankingLocalTravels, AppParameters.FILE_RANKING, 
				AppParameters.FOLDER_TRAVELS);
	}

	public static void removeTravel(Travel selectedTravel, FragmentActivity activity)
	{
		localTravels.remove(selectedTravel);
		saveTravels(activity);
	}

	public static void removeRankingTravel(Travel selectedTravel, FragmentActivity activity)
	{
		rankingLocalTravels.remove(selectedTravel);
		saveTravels(activity);
	}
}
