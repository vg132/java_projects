package com.vgsoftware.web.action.gt4;

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
import com.vgsoftware.web.model.gt4.ControlModel;
import com.vgsoftware.web.model.gt4.EventModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class ShowAddTimeAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		try
		{
			request.setAttribute("events",EventModel.getActiveEvents(getDataSource(request)));
			request.setAttribute("controllers",ControlModel.getControllers(getDataSource(request)));
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
			sql.printStackTrace(System.err);
		}
		if(ae==null)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}
}
