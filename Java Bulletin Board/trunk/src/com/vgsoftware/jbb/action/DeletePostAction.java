package com.vgsoftware.jbb.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.jbb.ForwardParameters;
import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.data.MemberData;
import com.vgsoftware.jbb.model.PostModel;

public class DeletePostAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		int postId=-1;
		int topicId=-1;
		int forumId=-1;
		try
		{
			postId=Integer.parseInt(request.getParameter("postId"));
			topicId=Integer.parseInt(request.getParameter("topicId"));
			forumId=Integer.parseInt(request.getParameter("forumId"));
			if(request.getSession().getAttribute(Globals.USER_KEY)!=null)
			{
				MemberData md=(MemberData)request.getSession().getAttribute(Globals.USER_KEY);
				PostModel.deletePost(getDataSource(request),md.getId(),postId);
			}
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace(System.err);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ForwardParameters fp=new ForwardParameters();
		fp.add("forumId",""+forumId);
		fp.add("topicId",""+topicId);
		return(fp.forward(mapping.findForward(Globals.SUCCESS)));
	}
}
