package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.data.gt4.EventData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;
import com.vgsoftware.web.model.gt4.TimeModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class TimesAction extends Action
{
	private static Log log=LogFactory.getLog(TimesAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int eventId=-1;
			if(request.getParameter("event")!=null)
				eventId=Integer.parseInt(request.getParameter("event"));
			else
				eventId=Integer.parseInt(request.getAttribute("event").toString());
			int driverId=-1;
			if(request.getParameter("driver")!=null)
				driverId=Integer.parseInt(request.getParameter("driver"));
			else
				driverId=Integer.parseInt(request.getAttribute("driver").toString());
			EventData event=EventModel.getEvent(eventId,getDataSource(request));
			if((event.getStart().before(new Date(System.currentTimeMillis())))&&(event.getEnd().after(new Date(System.currentTimeMillis()))))
				request.setAttribute("eventActive","active");
			request.setAttribute("times",TimeModel.getTimes(eventId,driverId,getDataSource(request)));
			request.setAttribute("driverid",driverId);
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			log.error("SQL Exception when listing times for driver.",sql);
		}
		log.warn("Unable to list times");
		ActionErrors ae=new ActionErrors();
		ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		addErrors(request,ae);
		return(mapping.findForward(Constants.ERROR));
	}
}
