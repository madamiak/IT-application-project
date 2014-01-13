package pl.travelscheduler.mobile.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.helpers.FilesHelper;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;

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
	
	private static ArrayList<Point> pois;
	
	public static void LoadPois(Activity activity)
	{
		pois = (ArrayList<Point>) FilesHelper.loadObject(activity, AppParameters.FILE_POIS, 
				AppParameters.FOLDER_POIS);
		if(pois == null)
		{
			pois = new ArrayList<Point>();
		}
	}
	
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
		List<String> imgList = new ArrayList<String>();
		Point wroclaw = new Point(9001, "Wroc³aw", 17.038333, 51.107778, 1, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point katowice = new Point(9002, "Katowice", 19.0, 50.25, 2, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point krakow = new Point(9003, "Kraków", 19.938333, 50.061389, 3, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point poznan = new Point(9004, "Poznañ", 16.934278, 52.4085, 4, PointType.CITY, imgList);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
		List<Point> pointList;
		
		pointList = new ArrayList<Point>();
		pointList.add(wroclaw);
		pointList.add(katowice);
		pointList.add(krakow);
		Travel t1 = new Travel(1, pointList, Rating.MEDIUM, firstStartingDate, 
				TransportType.CAR, 280, 666.55, "My first trip");
		addOnlineTravel(t1);
		pointList = new ArrayList<Point>();
		wroclaw.setNumber(1);
		poznan.setNumber(2);
		pointList.add(wroclaw);
		pointList.add(poznan);
		Travel t2 = new Travel(2, pointList, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180, 333.12, "My second trip");
		addOnlineTravel(t2);
		pointList = new ArrayList<Point>();
		krakow.setNumber(1);
		poznan.setNumber(2);
		pointList.add(krakow);
		pointList.add(poznan);
		Travel t3 = new Travel(3, pointList, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350, 333.12, "My third trip");
		addOnlineTravel(t3);
		pointList = new ArrayList<Point>();
		krakow.setNumber(1);
		katowice.setNumber(2);
		poznan.setNumber(3);
		pointList.add(wroclaw);
		pointList.add(katowice);
		pointList.add(krakow);
		Travel t4 = new Travel(4, pointList,
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280, 333.12, "Wro-Kato-Krk");
		addOnlineTravel(t4);
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
		
		List<String> imgList = new ArrayList<String>();
		Point wroclaw = new Point(9101, "Wroc³aw", 17.038333, 51.107778, 1, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point katowice = new Point(9102, "Katowice", 19.0, 50.25, 2, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point krakow = new Point(9103, "Kraków", 19.938333, 50.061389, 3, PointType.CITY, imgList);
		imgList = new ArrayList<String>();
		Point poznan = new Point(9104, "Poznañ", 16.934278, 52.4085, 4, PointType.CITY, imgList);
		
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);

		List<Point> pointList;
		
		pointList = new ArrayList<Point>();
		pointList.add(wroclaw);
		pointList.add(katowice);
		pointList.add(krakow);
		Travel t5 = new Travel(1, pointList, Rating.MEDIUM, firstStartingDate, 
				TransportType.CAR, 280, 666.55, "My first trip");
		addRankingOnlineTravel(t5);
		pointList = new ArrayList<Point>();
		wroclaw.setNumber(1);
		poznan.setNumber(2);
		pointList.add(wroclaw);
		pointList.add(poznan);
		Travel t2 = new Travel(2, pointList, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180, 333.12, "My second trip");
		addRankingOnlineTravel(t2);
		pointList = new ArrayList<Point>();
		krakow.setNumber(1);
		poznan.setNumber(2);
		pointList.add(krakow);
		pointList.add(poznan);
		Travel t3 = new Travel(3, pointList, Rating.LOW,
				firstStartingDate, TransportType.CAR, 350, 333.12, "My second trip");
		addRankingOnlineTravel(t3);
		pointList = new ArrayList<Point>();
		krakow.setNumber(1);
		katowice.setNumber(2);
		poznan.setNumber(3);
		pointList.add(wroclaw);
		pointList.add(katowice);
		pointList.add(krakow);
		Travel t4 = new Travel(4, pointList,
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280, 333.12, "Wro-Kato-Krk");
		addRankingOnlineTravel(t4);
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
	
	public static boolean addPoi(Point p)
	{
		if(pois == null)
		{
			pois = new ArrayList<Point>();
		}
		if(!pois.contains(p))
		{
			pois.add(p);
			return true;
		}
		return false;
	}
	
	public static boolean containsPoiWithId(int id)
	{
		if(pois == null)
		{
			return false;
		}
		for(Point point : pois)
		{
			if(point.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void savePois(Activity activity)
	{
		FilesHelper.saveObject(activity, pois, AppParameters.FILE_POIS, 
				AppParameters.FOLDER_POIS);
	}
	
	public static ArrayList<Point> getSavedPoints(Activity activity)
	{
		LoadPois(activity);
		return pois;
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
