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

import com.vgsoftware.vgutil.misc.WebHelp;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.news.CommentData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.CommentModel;

public class PreEditCommentAction extends Action
{
	private static Log log=LogFactory.getLog(PreEditCommentAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		MemberData member=null;
		int commentId=-1;
		if(request.getParameter("commentId")!=null)
			commentId=Integer.parseInt(request.getParameter("commentId"));
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			member=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if((member!=null)&&(commentId!=-1))
		{
			try
			{
				CommentData cd=CommentModel.getComment(commentId,getDataSource(request));
				if(cd!=null)
				{
					request.setAttribute("newsId",cd.getNewsId());
					if((!member.isAdmin())&&(cd.getMemberId()!=member.getId()))
						return(mapping.findForward(Constants.FAILURE));
					request.setAttribute("comment",WebHelp.fromSafeHTML(cd.getComment()));
					request.setAttribute("commentId",cd.getId());
					return(mapping.findForward(Constants.SUCCESS));
				}
			}
			catch(SQLException sql)
			{
				log.error("Unable to find comment.",sql);
			}
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}
