package pl.travelscheduler.mobile.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpHelper
{
	public static HttpResponse get(String url)
	{
		HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 6000);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(url);
		try 
		{
		    return httpclient.execute(request);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public static HttpResponse post(String url, HttpEntity data)
	{
		HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 6000);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpPost request = new HttpPost(url);
		try 
		{
		    if(data != null)
		    {
		    	request.setEntity(data);
		    }
		    return httpclient.execute(request);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	public static String getStringFromResponse(HttpResponse response)
	{
		HttpEntity entity = response.getEntity();
		if(entity!=null)
		{
			try
			{
				//read the content stream
				InputStream instream = entity.getContent();
				// convert content stream to a String
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try 
				{
					while ((line = reader.readLine()) != null) 
					{
						sb.append(line + "\n");
					}
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				} 
				finally 
				{
					try 
					{
						instream.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				String resultString = sb.toString();
			    return resultString.substring(0,resultString.length()-1);
			}
			catch (IllegalStateException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		return null;
	}
}
