package pl.travelscheduler.mobile.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarHelper
{
	public static String ToString(Calendar date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		return sdf.format(date.getTime());
	}
}
