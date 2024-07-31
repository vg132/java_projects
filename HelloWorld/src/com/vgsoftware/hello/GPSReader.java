package com.vgsoftware.hello;

import java.io.InputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class GPSReader implements Runnable
{

	private String url = "";

	// This is the data retreived from the GPS
	public static double SPEED_KMH = 0;
	public static String SPEED_KMH_STR = "";
	public static int NUM_SATELITES = 0;
	public static String NUM_SATELITES_STR = "";

	private String[] result = new String[25];

	private Vector speedVector = new Vector();

	public static String TYPE = ""; // Used for debugging.
	private String exc = ""; // Used for debugging.

	// Array used to interpolate the speed.
	private String[] sp = new String[9];
	private int spIndex = 0;

	/*
	 * The url to the bluetooth device is the String parameter.
	 */
	public GPSReader(String u)
	{
		url = u; // Connection string to the bluetooth device.
		Thread t = new Thread(this);
		t.start();
	}

	public void run()
	{

		StreamConnection conn = null;
		InputStream is = null;
		String err = ""; // used for debugging

		try
		{
			conn = (StreamConnection) Connector.open(url);
			is = conn.openInputStream();

			int i = 0;
			char c;
			String s = "";
			String[] data;
			String DATA_STRING;

			// Start reading the data from the GPS
			do
			{
				i = is.read(); // read one byte at the time.
				c = (char) i;
				s += c;
				if (i == 36)
				{ // Every sentence starts with a '$'
					if (s.length() > 5)
					{
						DATA_STRING = s.substring(5, s.length());
						TYPE = s.substring(2, 5);

						// Check the gps data type and convert the information to a more
						// readable format.
						if (s.substring(0, 5).compareTo("GPGGA") == 0)
						{
							try
							{
								data = splitString(DATA_STRING);

								NUM_SATELITES_STR = data[6];
								NUM_SATELITES = Integer.parseInt(data[6]);
								exc = "";
							} catch (Exception e)
							{
								exc = "<GGA>" + e.getMessage();
								NUM_SATELITES = 0;
							}

						} else if (s.substring(0, 5).compareTo("GPVTG") == 0)
						{
							try
							{
								data = splitString(DATA_STRING);

								SPEED_KMH_STR = data[6];

								speedVector.addElement(data[6]);
								spIndex = spIndex == 8 ? 0 : spIndex + 1;

								if (speedVector.size() == 10)
								{
									speedVector.removeElementAt(0);
								}
								double tot = 0;
								String tmp = "";

								for (int n = 0; n < speedVector.size(); n++)
								{
									tmp = (String) speedVector.elementAt(n);
									exc = "";
									try
									{
										tot += Double.parseDouble(tmp);
									} catch (Exception e)
									{
										exc = "<VTG>" + e.getMessage();
										tot += 0;
									}
								}
								SPEED_KMH = tot / (double) speedVector.size();

							} catch (Exception e)
							{
								SPEED_KMH = 0;
							}
						}
					}

					TYPE += ":" + exc;
					s = "";
				}
			} while (i != -1);

		} catch (Exception e)
		{
			err = e.toString();
			System.out.println(e);
		}
		TYPE = "EXITED!! : " + err;
	}

	/*
	 * Split the gps data string and return a string array. Example of GGA string:
	 * 123519,4807.038,N,01131.324,E,1,08,0.9,545.4,M,46.9,M, , *42
	 */
	private String[] splitString(String s) throws Exception
	{
		int i = 0;
		int pos = 0;
		int nextPos = 0;

		// check how big the array is.
		while (pos > -1)
		{
			pos = s.indexOf(",", pos);
			if (pos < 0)
			{
				continue;
			}
			pos++;
			i++;
		}

		if (i > 25)
		{
			throw new Exception("to big:" + i);
		}

		i = 0;
		pos = 0;

		// Start splitting the string, search for each ','
		while (pos > -1)
		{
			pos = s.indexOf(",", pos);
			if (pos < 0)
			{
				continue;
			}

			nextPos = s.indexOf(",", pos + 1);
			if (nextPos < 0)
			{
				nextPos = s.length();
			}

			result[i] = s.substring(pos + 1, nextPos);
			i++;
			if (pos > -1)
			{
				pos++;
			}
		}

		return result;
	}
}
