package com.vgsoftware.jbb.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.model.ForumModel;
import com.vgsoftware.jbb.model.PostModel;
import com.vgsoftware.jbb.model.TopicModel;

public class TopicIndexAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int forumId=Integer.parseInt(request.getParameter("forumId"));
			int topicId=Integer.parseInt(request.getParameter("topicId"));
			request.setAttribute("forum",ForumModel.getForum(getDataSource(request),forumId));
			request.setAttribute("topic",TopicModel.getTopic(getDataSource(request),topicId));
			request.setAttribute("posts",PostModel.getPosts(getDataSource(request),topicId));
			return(mapping.findForward(Globals.SUCCESS));
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace(System.err);
		}
		return(mapping.findForward(Globals.FAILURE));
	}
}
