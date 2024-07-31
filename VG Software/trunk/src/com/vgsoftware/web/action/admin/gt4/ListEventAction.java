package com.vgsoftware.web.action.admin.gt4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class ListEventAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		try
		{
			int seasonId=-1;
			if(request.getParameter("season")!=null)
				seasonId=Integer.parseInt(request.getParameter("season"));
			else
				seasonId=Integer.parseInt(request.getAttribute("season").toString());
			request.setAttribute("season",""+seasonId);
			request.setAttribute("events",EventModel.getEvents(seasonId,getDataSource(request)));
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
			addErrors(request,ae);
			sql.printStackTrace(System.err);
		}
		return(mapping.findForward(Constants.ERROR));
	}
}
