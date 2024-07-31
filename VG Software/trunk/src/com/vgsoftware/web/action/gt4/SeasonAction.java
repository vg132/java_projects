package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;

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

import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.SeasonModel;

/**
 * Action for the season list page.
 * 
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class SeasonAction extends Action
{
	private static Log log=LogFactory.getLog(SeasonAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			request.setAttribute("seasons",SeasonModel.getSeasons(getDataSource(request)));
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			log.error("SQL exception in SeasonAction.",sql);
		}
		log.error("Unable to list seasons.");
		ActionErrors ae=new ActionErrors();
		ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		addErrors(request,ae);
		return(mapping.findForward(Constants.ERROR));
	}
}
