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
public class RemoveEventAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		try
		{
			EventModel.removeEvent(Integer.parseInt(request.getParameter("event")),getDataSource(request));
			request.setAttribute("season",request.getParameter("season"));
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
