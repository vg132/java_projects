package com.vgsoftware.web.action.news;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
import com.vgsoftware.web.form.news.CommentForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.CommentModel;

public class EditCommentAction extends Action
{
	private static Log log=LogFactory.getLog(EditCommentAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		MemberData member=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			member=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if((member!=null)||(form!=null))
		{
			CommentForm cf=(CommentForm)form;
			try
			{
				CommentData cd=CommentModel.getComment(cf.getCommentId(),getDataSource(request));
				if(cd!=null)
				{
					request.setAttribute("newsId",cf.getNewsId());
					if((!member.isAdmin())&&(cd.getMemberId()!=member.getId()))
						return(mapping.findForward(Constants.FAILURE));
					cd.setComment(WebHelp.toSafeHTML(cf.getComment()));
					cd.setEditDate(new Timestamp(System.currentTimeMillis()));
					CommentModel.updateComment(cd,getDataSource(request));
					return(mapping.findForward(Constants.SUCCESS));
				}
			}
			catch(SQLException sql)
			{
				log.error("Unable to add comment.",sql);
			}
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}
