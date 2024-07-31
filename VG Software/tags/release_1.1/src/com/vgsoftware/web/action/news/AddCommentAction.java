package com.vgsoftware.web.action.news;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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

import com.vgsoftware.vgutil.misc.WebHelp;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.news.CommentData;
import com.vgsoftware.web.data.news.NewsData;
import com.vgsoftware.web.form.news.CommentForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.CommentModel;
import com.vgsoftware.web.model.news.NewsModel;

public class AddCommentAction extends Action
{
	private static Log log=LogFactory.getLog(AddCommentAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		CommentForm cf=(CommentForm)form;
		MemberData member=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			member=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if(member!=null)
		{
			if(cf.getComment().trim().equals(""))
			{
				ActionErrors ae=new ActionErrors();
				ae.add("login_to_comment",new ActionMessage("error.comment.nocomment"));
				addErrors(request,ae);
				return(mapping.findForward(Constants.CONTINUE));
			}
			try
			{
				CommentData cd=new CommentData();
				cd.setComment(WebHelp.toSafeHTML(cf.getComment()));
				cd.setMemberId(member.getId());
				cd.setNewsId(cf.getNewsId());
				cd.setPostDate(new Timestamp(System.currentTimeMillis()));
				cd.setEditDate(new Timestamp(System.currentTimeMillis()));
				if(CommentModel.addComment(cd,getDataSource(request)))
				{
					NewsData nd=NewsModel.getNews(cf.getNewsId(),getDataSource(request));
					if(nd!=null)
					{
						nd.setComments(nd.getComments()+1);
						NewsModel.updateNews(nd,getDataSource(request));
					}
					return(mapping.findForward(Constants.CONTINUE));
				}
			}
			catch(SQLException sql)
			{
				log.error("Unable to add comment.",sql);
			}
		}
		request.setAttribute("comment",cf.getComment());
		return(mapping.findForward(Constants.CONTINUE));
	}
}
