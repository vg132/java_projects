package com.vgsoftware.appengine.polisenrss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser
{
	private static final SimpleDateFormat[] DateFormats =
		{
			new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.ENGLISH),
			new SimpleDateFormat("d MMM yy HH:mm z", Locale.ENGLISH),
			new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.ENGLISH),
			new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH),
			new SimpleDateFormat("EEE MMM  d kk:mm:ss zzz yyyy", Locale.ENGLISH),
			new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0", Locale.ENGLISH),
			new SimpleDateFormat("-yy-MM", Locale.ENGLISH),
			new SimpleDateFormat("-yyMM", Locale.ENGLISH),
			new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-MM", Locale.ENGLISH),
			new SimpleDateFormat("yyyy-D", Locale.ENGLISH),
			new SimpleDateFormat("-yyMM", Locale.ENGLISH),
			new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH),
			new SimpleDateFormat("yyMMdd", Locale.ENGLISH),
			new SimpleDateFormat("yyyy", Locale.ENGLISH),
			new SimpleDateFormat("yyD", Locale.ENGLISH)
		};

	public static Date parse(String dateString)
	{
		Date date = null;
		SimpleDateFormat dateFormatter=null;
		for (int i = 0; i < DateFormats.length; i++)
		{
			try
			{
				dateFormatter=DateFormats[i];
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
