package com.vgsoftware.web.action.admin;

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

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.news.NewsData;
import com.vgsoftware.web.form.news.NewsForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.NewsModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-07 - Updated to use model classes.
 * 2004-dec-18 - Class created.
 */
public class NewsAction extends Action
{
	private static Log log=LogFactory.getLog(NewsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		NewsForm nf=(NewsForm)form;
		try
		{
			MemberData md=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
			if(md.isAdmin())
			{
				NewsData nd=new NewsData();
				nd.setAuthorId(md.getId());
				nd.setAuthorName(md.getName());
				nd.setComment(nf.getComment());
				nd.setComments(0);
				nd.setContent(nf.getContent().replace("\n","<br/>"));
				nd.setTitle(nf.getTitle());
				nd.setDate(new Timestamp(System.currentTimeMillis()));
				if(NewsModel.addNews(nd,getDataSource(request)))
					return(mapping.findForward(Constants.SUCCESS));
				ae.add("unknown_db",new ActionMessage("error.unknown.database"));
			}
			else
			{
				return(mapping.findForward(Constants.LOGIN));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when adding news story.",sql);
		}
		addErrors(request,ae);
		request.setAttribute("title",nf.getTitle());
		request.setAttribute("content",nf.getContent());
		return(mapping.findForward(Constants.FAILURE));
	}
}
