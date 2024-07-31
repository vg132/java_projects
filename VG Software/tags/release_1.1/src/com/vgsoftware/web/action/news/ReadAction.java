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

import com.vgsoftware.web.data.news.NewsData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.CommentModel;
import com.vgsoftware.web.model.news.NewsModel;

public class ReadAction extends Action
{
	private static Log log=LogFactory.getLog(ReadAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int newsId=-1;
			if(request.getParameter("newsId")!=null)
				newsId=Integer.parseInt(request.getParameter("newsId"));
			else if(request.getAttribute("newsId")!=null)
				newsId=Integer.parseInt(request.getParameter("newsId"));
			NewsData nd=NewsModel.getNews(newsId,getDataSource(request));
			if(nd!=null)
			{
				request.setAttribute("comments",CommentModel.getComments(nd.getId(),getDataSource(request)));
				request.setAttribute("news",nd);
				return(mapping.findForward(Constants.SUCCESS));
			}
		}
		catch(NumberFormatException nfe)
		{
			log.error("Unable to parse news id parameter, '"+request.getParameter("newsId")+"'.",nfe);
		}
		catch(SQLException sql)
		{
			log.error("Unable to get news item or comments.",sql);
		}
		return(mapping.findForward(Constants.INDEX));
	}
}
