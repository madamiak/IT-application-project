package pl.travelscheduler.mobile;

public class AppParameters
{
	public static String SERVER_URL = "http://192.168.0.101:9000";
	public static String AUTHENTICATE_SERVICE = "/user/authenticate";	
	public static String POIS_SERVICE = "/places/pois";	
	public static String POI_DATA_SERVICE = "/places/poi";
	public static int POIS_RADIUS = 5000;
	
	public static String FOLDER_CACHE = "ts_cache";
	public static String FILE_LAST_LOGIN = "last_login.tsc";
	public static String FILE_USERNAMES = "user_names.tsc";
	
	public static String FOLDER_TRAVELS = "ts_travels";
	public static String FILE_MY_TRAVELS = "my_travels.tst";
	public static String FILE_RANKING = "ranking.tst";
	
	public static String FOLDER_POIS = "ts_tpois";
	public static String FILE_POIS = "pois.tsp";
}
