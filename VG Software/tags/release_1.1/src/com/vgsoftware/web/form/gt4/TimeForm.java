package com.vgsoftware.web.form.gt4;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class TimeForm extends ActionForm
{
	private String time=null;
	private int eventId=-1;
	private int controlType=-1;

	/**
	 * @return Returns the controlType.
	 */
	public int getControlType()
	{
		return controlType;
	}
	/**
	 * @param controlType The controlType to set.
	 */
	public void setControlType(int controlType)
	{
		this.controlType = controlType;
	}
	/**
	 * @return Returns the eventId.
	 */
	public int getEventId()
	{
		return eventId;
	}
	/**
	 * @param eventId The eventId to set.
	 */
	public void setEventId(int eventId)
	{
		this.eventId = eventId;
	}
	/**
	 * @return Returns the time.
	 */
	public String getTime()
	{
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((time.length()<8)||(!time.contains(":"))||(!time.contains(".")))
			errors.add("invalid_time",new ActionMessage("error.gt4.invalidtimeformat"));
		request.setAttribute("time",time);
		return(errors);
	}
}
