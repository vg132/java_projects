/*
 * Created on 2004-dec-06 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.observer;

import java.util.Vector;

/**
 * Subject is one part of the implementation of the Observer Pattern.
 * 
 * Subject is extended by the class that want to add ability to send information
 * to clients on events.
 * 
 * @author Viktor
 * @version 1.0
 */
public abstract class Subject
{
	private Vector observerList=new Vector();

	/**
	 * Attach a observer to this subject.
	 * 
	 * @param observer the observer to add
	 */
	public void attach(Observer observer)
	{
		observerList.addElement(observer);
	}
	
	/**
	 * Detach a observer from this subject.
	 * 
	 * @param observer the observer to remove
	 */
	public void detach(Observer observer)
	{
		observerList.removeElement(observer);
	}
	
	/**
	 * Sends a notification to all observers with a event id and event data.
	 * 
	 * @param event a unique event id for this event
	 * @param data data needed by the client to process the event
	 */
	public void notify(int event, Object data)
	{
		for(int i=0;i<observerList.size();i++)
			((Observer)observerList.elementAt(0)).update(this,event,data);
	}
}
