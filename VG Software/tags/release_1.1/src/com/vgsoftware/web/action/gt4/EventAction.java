package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.data.gt4.EventData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;
import com.vgsoftware.web.model.gt4.TimeModel;

/**
 * Shows the times for the selected event, if the event is not open
 * redirects the user to the failure site.
 * 
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class EventAction extends Action
{
	private static Log log=LogFactory.getLog(EventAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int eventId=-1;
			if(request.getParameter("event")!=null)
				eventId=Integer.parseInt(request.getParameter("event"));
			else if(request.getAttribute("event")!=null)
				eventId=Integer.parseInt(request.getAttribute("event").toString());
			EventData ed=EventModel.getEvent(eventId,getDataSource(request));
			if((ed!=null)&&(ed.getStart().getTime()<System.currentTimeMillis()))
			{
				request.setAttribute("event",ed);
				request.setAttribute("times",TimeModel.getTimes(eventId,getDataSource(request)));
				return(mapping.findForward(Constants.SUCCESS));
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}
