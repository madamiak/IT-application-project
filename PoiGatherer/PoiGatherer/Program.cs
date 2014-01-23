using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.Net;
using System.Threading;
using System.IO;
using System.Text.RegularExpressions;
using System.Reflection;

namespace PoiGatherer
{
    class Program
    {
        private static List<Poi> pois = new List<Poi>();
        private static Int32 currentID = 0, beginID;

        private static Int32 threadCount = 10;
        private static Int32 repeats = 0;
        private static Int32 empty = 0;

        private static Object myLock = new Object();

        static void Main(string[] args)
        {
            Console.Out.Write("Write ID which should be first, or press Enter to skipp: ");
            String startID = Console.ReadLine();

            if (startID != "")
            {
                currentID = Convert.ToInt32(startID);
            }
            beginID = currentID;
            Console.WriteLine("---------------------------");

            for (int i = 0; i < threadCount; i++)
            {
                ThreadStart ts = new ThreadStart(downloadPois);
                Thread t = new Thread(ts);
                t.Start();
            }

            ThreadStart stats = new ThreadStart(timer);
            Thread stat = new Thread(stats);
            stat.Start();
        }

        private static String getHtml(String url)
        {
            HttpWebRequest myWebRequest = (HttpWebRequest)HttpWebRequest.Create(url);
            myWebRequest.PreAuthenticate = true;
            myWebRequest.Method = "GET";
            HttpWebResponse myWebResponse = null;
            while (true)
            {
                Boolean canExit = false;
                try
                {
                    myWebResponse = (HttpWebResponse)myWebRequest.GetResponse();
                }
                catch (Exception e)
                {
                    lock (myLock)
                    {
                        repeats++;
                    }
                }
                finally {
                    canExit = true;
                }
                if (canExit)
                    break;
            }
            Encoding enc = Encoding.GetEncoding("iso-8859-2");
            StreamReader myWebSource = new StreamReader(myWebResponse.GetResponseStream(), enc);
            String myPageSource = String.Empty;
            myPageSource = myWebSource.ReadToEnd();
            myWebResponse.Close();
            return myPageSource;
        }

        private static void timer()
        {
            int nextNumber = 0, savedOnSecond = 0;
            Stopwatch stopwatch = Stopwatch.StartNew();
            while (true)
            {
                Console.CursorLeft = 0;
                Int32 _currID, _beginID, _rep, _emp;
                lock (myLock)
                {
                    _currID = currentID;
                    _beginID = beginID;
                    _rep = repeats;
                    _emp = empty;
                }
                Int32 seconds = (int)(stopwatch.ElapsedMilliseconds / 1000);
                Int32 downloaded = (_currID - _beginID);

                Console.Write("Requests: " + downloaded + ", Repeats: " + _rep + ", Found/Empty: " + (downloaded - _emp - threadCount) + "/" + _emp + ", Threads: " + threadCount + ", Time: " + seconds + "sec");
                Thread.Sleep(100);

                lock (myLock)
                {
                    if (seconds % 120 == 0 && savedOnSecond != seconds)
                    {
                        savedOnSecond = seconds;

                        String path = Path.GetDirectoryName(Assembly.GetEntryAssembly().Location);
                        using (StreamWriter outfile = new StreamWriter(path + @"\Backup_" + (100000 + (nextNumber++)) + ".txt"))
                        {
                            outfile.BaseStream.Seek(0, SeekOrigin.Begin);
                            foreach (Poi poi in pois)
                            {
                                outfile.WriteLine(poi.ToString());
                            }
                            outfile.Close();
                        }
                    }
                }
            }
        }

        private static void downloadPois()
        {
            while (true)
            {
                int workingID = 0;
                lock (myLock)
                {
                    workingID = currentID;
                    currentID++;

                    if (currentID > 180000)
                    {
                        break;
                    }
                }

                String body = "";
                while (true)
                {
                    Boolean canExit = false;
                    try
                    {
                        body = getHtml("http://www.poipoint.pl/point.php?poi_id=" + workingID);
                    }
                    catch (Exception e)
                    {
                        lock (myLock)
                        {
                            repeats++;
                        }
                    }
                    finally
                    {
                        if (body.Contains("<html"))
                        {
                            canExit = true;
                        }
                    }
                    if (canExit)
                        break;
                }

                try
                {
                    Poi poi = new Poi();
                    poi.Id = workingID;

                    //Category
                    Match match = Regex.Match(body, @"namen[^\?]+[^>]+.([^<]+)", RegexOptions.Multiline);
                    if (match.Success)
                    {
                        poi.Category = match.Groups[1].Value;
                    }

                    if (poi.IsEmpty())
                    {
                        lock (myLock)
                        {
                            empty++;
                        }
                        continue;
                    }

                    //Name
                    match = Regex.Match(body, "F56404[^>]+.([^<]+)</div>(.*?)<br>(.*?)<br>(.*?)<br>(.*?)<br>(.*?)<div", RegexOptions.IgnoreCase | RegexOptions.Multiline | RegexOptions.Singleline);    //.*?<div[^>]+.([^<]+)
                    if (match.Success)
                    {
                        poi.Name = match.Groups[1].Value;
                        poi.Address = match.Groups[2].Value.Trim() +
                            (match.Groups[3].Value.Trim() != "" ? ", " + match.Groups[3].Value.Trim() : "") +
                            (match.Groups[4].Value.Trim() != "" ? ", " + match.Groups[4].Value.Trim() : "");
                        poi.Description = match.Groups[5].Value.Trim() + " " + match.Groups[6].Value.Trim();

                        if (poi.Address.Contains("<div") || poi.Address.Contains("<a"))
                        {
                            poi.Address = "";
                        }
                        if (poi.Description.Contains("<div") || poi.Description.Contains("<a"))
                        {
                            poi.Description = "";
                        }
                    }

                    //Latitude Longtitude
                    match = Regex.Match(body, @"LatLng.([^,]+),([^\)]+)");
                    if (match.Success)
                    {
                        poi.Lat = match.Groups[1].Value;
                        poi.Lng = match.Groups[2].Value;
                    }
                    
                    //Checking by google if it's from Poland
                    String isPolandBody = "";
                    while (true)
                    {
                        Boolean canExit = false;
                        try
                        {
                            isPolandBody = getHtml("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + poi.Lat + "," + poi.Lng + "&sensor=true");
                        }
                        catch (Exception e)
                        {
                            lock (myLock)
                            {
                                repeats++;
                            }
                        }
                        finally
                        {
                            if (isPolandBody.Contains("results"))
                            {
                                canExit = true;
                            }
                        }
                        if (canExit)
                            break;
                    }

                    if (isPolandBody.IndexOf(@"Poland") > 0)
                    {
                        lock (myLock)
                        {
                            pois.Add(poi);
                        }
                    }
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
