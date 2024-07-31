package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.data.gt4.SeasonData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;
import com.vgsoftware.web.model.gt4.SeasonModel;

/**
 * Action for the event list page. This action takes all events for a season
 * and lists them on the page.
 * 
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class EventsAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int seasonId=-1;
			if(request.getParameter("season")!=null)
				seasonId=Integer.parseInt(request.getParameter("season"));
			else if(request.getAttribute("season")!=null)
				seasonId=Integer.parseInt(request.getAttribute("season").toString());
			SeasonData sd=SeasonModel.getSeason(seasonId,getDataSource(request));
			if(sd!=null)
			{
				request.setAttribute("season",sd);
				request.setAttribute("pastEvents",EventModel.getPastEvents(seasonId,getDataSource(request)));
				request.setAttribute("futureEvents",EventModel.getFutureEvents(seasonId,getDataSource(request)));			
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
