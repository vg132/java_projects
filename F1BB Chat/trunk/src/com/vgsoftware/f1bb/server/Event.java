/**
 * Document History
 * Created on 2004-sep-08
 * 
 * @author Viktor
 * @version 1.0
 */
package com.vgsoftware.f1bb.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Event
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm");
	private Calendar open=Calendar.getInstance();
	private Calendar close=Calendar.getInstance();
	private String type=null;
	private String event=null;
	private String session=null;

	public Event(String type, String event, String session, long open, long close)
	{
		this.type=type;
		this.event=event;
		this.session=session;
		this.open.setTimeInMillis(open);
		this.close.setTimeInMillis(close);
	}

	public String getOpenDate()
	{
		return(sdf.format(this.close.getTime()));
	}
	
	public long getEventCode()
	{
		return(open.getTimeInMillis()/1000);
	}

	public boolean isOpen(Calendar rightNow)
	{
		if((rightNow.getTimeInMillis()>open.getTimeInMillis())&&(rightNow.getTimeInMillis()<close.getTimeInMillis()))
			return(true);
		else
			return(false);
	}

	public long timeToEvent(Calendar rightNow)
	{
		if((open.getTimeInMillis()-rightNow.getTimeInMillis())>0)
			return(open.getTimeInMillis()-rightNow.getTimeInMillis());
		else
			return(close.getTimeInMillis()-rightNow.getTimeInMillis());
	}

	public String getEvent()
	{
		return(event);
	}

	public String getSession()
	{
		return(session);
	}

	public String getType()
	{
		return(type);
	}

	public String getClose()
	{
		return(sdf.format(new Date(close.getTimeInMillis())));
	}

	public String getOpen()
	{
		return(sdf.format(new Date(open.getTimeInMillis())));
	}
}
