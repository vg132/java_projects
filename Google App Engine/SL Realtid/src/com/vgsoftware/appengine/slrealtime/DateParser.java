package com.vgsoftware.appengine.slrealtime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser
{
	private static final SimpleDateFormat[] DateFormats =
		{
			new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z"),
			new SimpleDateFormat("EEE, d MMM yy HH:mm z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm z"),
			new SimpleDateFormat("d MMM yy HH:mm z"),
			new SimpleDateFormat("d MMM yy HH:mm:ss z"),
			new SimpleDateFormat("d MMM yyyy HH:mm z"),
			new SimpleDateFormat("d MMM yyyy HH:mm:ss z"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
			new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z"),
			new SimpleDateFormat("EEE MMM  d kk:mm:ss zzz yyyy"),
			new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0"),
			new SimpleDateFormat("-yy-MM"),
			new SimpleDateFormat("-yyMM"),
			new SimpleDateFormat("yy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM"),
			new SimpleDateFormat("yyyy-D"),
			new SimpleDateFormat("-yyMM"),
			new SimpleDateFormat("yyyyMMdd"),
			new SimpleDateFormat("yyMMdd"),
			new SimpleDateFormat("yyyy"),
			new SimpleDateFormat("yyD")
		};

	public static Date parse(String dateString)
	{
		Date date = null;
		SimpleDateFormat dateFormatter = null;
		for (int i = 0; i < DateFormats.length; i++)
		{
			try
			{
				dateFormatter = DateFormats[i];
				date = dateFormatter.parse(dateString);
				break;
			}
			catch (Exception e)
			{
			}
		}
		return date;
	}
}
