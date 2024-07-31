/*
 * Created on 2004-dec-06 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.observer;

/**
 * Observer is one part of the implementation of the Observer Pattern.
 * Observer is implemented by classes that want to be notified about
 * events in a subject. 
 * 
 * @author Viktor
 * @version 1.0
 */
public interface Observer
{
  /**
   * Reciveds a notification from a subject about an event.
   * 
   * @param subject the subject that sends this event
   * @param event the unique event id
   * @param data the data assosiated with the event
   */
	public void update(Subject subject, int event, Object data);
}
