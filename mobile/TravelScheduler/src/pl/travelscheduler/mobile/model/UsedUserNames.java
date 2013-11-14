package pl.travelscheduler.mobile.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UsedUserNames implements Serializable
{
	private static final long serialVersionUID = 123183866218123650L;

	private ArrayList<String> userNames;
	
	public void addUserName(String userName)
	{
		if(userNames == null )
		{
			userNames = new ArrayList<String>();
		}
		if(userNames.contains(userName))
		{
			userNames.remove(userName);
		}
		else
		{
			if(userNames.size() >= 5)
			{
				userNames.remove(userNames.size() - 1);
			}
		}
		userNames.add(0, userName);
	}
	
	public ArrayList<String> getUserNames()
	{
		if(userNames == null)
		{
			userNames = new ArrayList<String>();
		}
		return userNames;
	}
}
