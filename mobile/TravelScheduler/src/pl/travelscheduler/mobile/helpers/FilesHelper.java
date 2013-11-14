package pl.travelscheduler.mobile.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class FilesHelper
{
	public static Object loadObject(Activity currentActivity, String fileName, String folderName)
	{
		File file = new File(currentActivity.getDir(folderName, Context.MODE_PRIVATE), fileName);
		if ( file.exists())
		{
			try 
			{
				FileInputStream fis = new FileInputStream(file.getPath());
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object retObject = ois.readObject();
				fis.close();
				return retObject;
			} 
			catch (FileNotFoundException e) 
			{
				Toast.makeText(currentActivity, "Config loading error: file not found", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
			catch (StreamCorruptedException e) 
			{
				Toast.makeText(currentActivity, "Config loading error: stream corrupted", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				Toast.makeText(currentActivity, "Config loading error: I/O error", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				Toast.makeText(currentActivity, "Config loading error: class not found", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean saveObject(Activity currentActivity, Object objectToSave, String fileName, String folderName)
	{
		File file = new File(currentActivity.getDir(folderName, Context.MODE_PRIVATE), fileName);
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) 
			{
				Toast.makeText(currentActivity, "Create new file: I/O error", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				return false;
			}
		}
		try 
		{
			FileOutputStream fos = new FileOutputStream(file.getPath());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToSave);
			oos.close();
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			Toast.makeText(currentActivity, "Saving file: file not found", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			Toast.makeText(currentActivity, "Saving file: I/O error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return false;
	}
	
	public static void deleteFile(Activity currentActivity, String fileName, String folderName)
	{
		File file = new File(currentActivity.getDir(folderName, Context.MODE_PRIVATE), fileName);
		if ( file.exists())
		{
			boolean deleted = file.delete(); //TODO: file is not deleting...
			if(!deleted)
			{
				file.delete();
			}
		}
	}
}
