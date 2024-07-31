package com.vgsoftware.web.action.news;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.news.CommentData;
import com.vgsoftware.web.data.news.NewsData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.CommentModel;
import com.vgsoftware.web.model.news.NewsModel;

public class DeleteCommentAction extends Action
{
	private static Log log=LogFactory.getLog(DeleteCommentAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		MemberData member=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			member=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if(member!=null)
		{
			try
			{
				int commentId=-1;
				int newsId=-1;
				if(request.getParameter("commentId")!=null)
					commentId=Integer.parseInt(request.getParameter("commentId"));
				if(request.getParameter("newsId")!=null)
					newsId=Integer.parseInt(request.getParameter("newsId"));
				if(member.isAdmin())
				{
					CommentData cd=CommentModel.getComment(commentId,getDataSource(request));
					if(cd!=null)
						CommentModel.removeComment(commentId,cd.getMemberId(),getDataSource(request));
				}
				else
				{
					CommentModel.removeComment(commentId,member.getId(),getDataSource(request));
				}
				
				NewsData nd=NewsModel.getNews(newsId,getDataSource(request));
				if(nd!=null)
				{
					nd.setComments(nd.getComments()-1);
					NewsModel.updateNews(nd,getDataSource(request));
				}
				request.setAttribute("newsId",newsId);
			}
			catch(NumberFormatException nfe)
			{
				log.error("Unable to parse news or comment id parameter, commentId: '"+request.getParameter("commentId")+"', newsId: '"+request.getParameter("newsId")+"'.",nfe);
			}
			catch(SQLException sql)
			{
				log.error("Unable to delete comment.",sql);
			}
		}
		return(mapping.findForward(Constants.CONTINUE));
	}
}
