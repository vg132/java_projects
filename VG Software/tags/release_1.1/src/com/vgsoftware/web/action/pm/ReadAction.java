package com.vgsoftware.web.action.pm;

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

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.pm.MessageData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.pm.MessageModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-04 - Fixed code.
 * 2005-sep-27 - Rewritten to use model class.
 * 2005-jan-01 - Class created.
 */
public class ReadAction extends Action
{
	private static Log log=LogFactory.getLog(ReadAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		int messageId=Integer.parseInt(request.getParameter("messageId"));
		MemberData user=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			user=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if(user!=null)
		{
			try
			{
				MessageData md=MessageModel.getMessage(messageId,user.getId(),getDataSource(request));
				if(md!=null)
				{
					request.setAttribute("title",md.getTitle());
					request.setAttribute("content",md.getContent());
					request.setAttribute("from",md.getSenderName());
					return(mapping.findForward(Constants.SUCCESS));
				}
				ae.add("error.pm.notfound",new ActionMessage("error.pm.notfound"));
			}
			catch(SQLException sql)
			{
				ae.add("error.pm.notfound",new ActionMessage("error.pm.notfound"));
				log.error("SQLException when trying to read a message.",sql);
			}
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}
}
