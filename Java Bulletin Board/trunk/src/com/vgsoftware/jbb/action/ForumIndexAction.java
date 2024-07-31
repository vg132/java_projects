package com.vgsoftware.jbb.action;

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

import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.model.ForumModel;
import com.vgsoftware.jbb.model.TopicModel;

public class ForumIndexAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int forumId=Integer.parseInt(request.getParameter("forumId"));
			request.setAttribute("forum",ForumModel.getForum(getDataSource(request),forumId));
			request.setAttribute("topics",TopicModel.getTopics(getDataSource(request),forumId));
			return(mapping.findForward(Globals.SUCCESS));
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace(System.err);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ActionErrors ae=new ActionErrors();
		ae.add("unknown_error",new ActionMessage("errors.unknown"));
		addErrors(request,ae);
		return(mapping.findForward(Globals.FAILURE));
	}
}
