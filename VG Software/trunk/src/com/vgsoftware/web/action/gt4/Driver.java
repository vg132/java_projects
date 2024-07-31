package com.vgsoftware.web.action.gt4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver implements Comparable
{
	final static int[] points=new int[]{20,16,14,12,10,8,6,5,4,3,2,1}; 
	List<Event> events=new ArrayList<Event>();
	String name=null;
	int limit=-1;

	public Driver(String name, int limit)
	{
		this.name=name;
		this.limit=limit;
	}
	
	public int compareTo(Object obj)
	{
		if(obj instanceof Driver)
			return(((Driver)obj).getPoints()-this.getPoints());
		return(-1);
	}
	
	public void addResult(int event, int pos, int time)
	{
		events.add(new Event(event,pos,time));
	}

	public String getName()
	{
		return(name);
	}
	
	public int getPoints()
	{
		int driverPoints=0;
		Collections.sort((List)events);
		int size=(events.size()>limit?limit:events.size());
		for(int i=0;i<size;i++)
		{
			int pos=events.get(i).getPos();
			if(pos<=Driver.points.length)
				driverPoints+=points[pos-1];
		}
		return(driverPoints);
	}
}
