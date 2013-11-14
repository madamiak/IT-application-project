package pl.travelscheduler.mobile.helpers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.widget.ArrayAdapter;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.model.SuccessfulLogin;
import pl.travelscheduler.mobile.model.UsedUserNames;
import pl.travelscheduler.mobile.tasks.LoginTask;

public class SessionHelper
{
	private static boolean userLoggedIn;
	private static String currentUserId;
	
	private static UsedUserNames userNames;
	
	public static boolean isUserLoggedIn()
	{
		return userLoggedIn;
	}
	
	public static int logIn(Activity activity, String userName, String password)
	{
		try
		{
			LoginTask task = new LoginTask();
			Integer result = task.execute(userName, password).get();
			if(result == 1)
			{
				saveLoginFile(activity, userName, password);
				if(userNames == null)
				{
					userNames = new UsedUserNames();
				}
				userNames.addUserName(userName);
				saveUserNames(activity, userName);
			}
			return result;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}		
		return -2;
	}
	
	private static void saveUserNames(Activity activity, String userName)
	{
		if(userNames != null)
		{
			FilesHelper.saveObject(activity, userNames, AppParameters.FILE_USERNAMES, AppParameters.FOLDER_CACHE);
		}
	}

	private static void saveLoginFile(Activity activity, String userName,
			String password)
	{
		SuccessfulLogin data = new SuccessfulLogin();
		data.userName = userName;
		data.password = password;
		FilesHelper.saveObject(activity, data, AppParameters.FILE_LAST_LOGIN, AppParameters.FOLDER_CACHE);
	}

	public static void logOut()
	{
		userLoggedIn = false;
		currentUserId = null;
	}

	public static void setUserId(String userId)
	{
		currentUserId = userId;
		userLoggedIn = true;
	}
	
	public static String getUserId()
	{
		return currentUserId;
	}

	public static ArrayAdapter<String> getUserNamesAdapter(Activity activity)
	{
		if(userNames == null)
		{
			userNames = new UsedUserNames();
		}
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, 
				 android.R.layout.simple_dropdown_item_1line, userNames.getUserNames());
         return adapter;
	}

	public static void firstLogIn(Activity activity)
	{
		SuccessfulLogin data = (SuccessfulLogin)FilesHelper.loadObject(activity, 
				AppParameters.FILE_LAST_LOGIN, AppParameters.FOLDER_CACHE);
		if(data != null)
		{
			logIn(activity, data.userName, data.password);
		}
	}

	public static void loadUserNames(Activity activity)
	{
		userNames = (UsedUserNames)FilesHelper.loadObject(activity, 
				AppParameters.FILE_USERNAMES, AppParameters.FOLDER_CACHE);
	}
	
	public ArrayList<String> getUsedUserNames()
	{
		return userNames.getUserNames();
	}

	public static void clearLastLoginData(Activity activity)
	{
		FilesHelper.deleteFile(activity, AppParameters.FILE_USERNAMES, AppParameters.FOLDER_CACHE);
	}
}
