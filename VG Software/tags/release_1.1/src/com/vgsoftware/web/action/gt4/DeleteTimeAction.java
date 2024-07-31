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
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;
import com.vgsoftware.web.model.gt4.TimeModel;

public class DeleteTimeAction extends Action
{
	private static Log log=LogFactory.getLog(DeleteTimeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			int eventId=-1;
			if(request.getParameter("event")!=null)
				eventId=Integer.parseInt(request.getParameter("event"));
			int timeId=-1;
			if(request.getParameter("time")!=null)
				timeId=Integer.parseInt(request.getParameter("time"));

			MemberData user=null;
			if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
				user=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
			if(user!=null)
			{
				EventData event=EventModel.getEvent(eventId,getDataSource(request));
				if((event.getStart().before(new Date(System.currentTimeMillis())))&&(event.getEnd().after(new Date(System.currentTimeMillis()))))
					TimeModel.removeTime(timeId,user.getId(),getDataSource(request));					
				request.setAttribute("driver",user.getId());
			}
			request.setAttribute("event",eventId);
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			log.error("SQL Exception when removing a time.",sql);
		}
		log.warn("Unable to remove a time");
		ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		addErrors(request,ae);
		return(mapping.findForward(Constants.ERROR));
	}
}
