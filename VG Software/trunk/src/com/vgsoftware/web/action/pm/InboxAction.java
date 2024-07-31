package com.vgsoftware.web.action.pm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.pm.MessageData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.pm.MessageModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-sep-27 - Rewritten to use model class.
 * 2004-dec-19 - Class created.
 */
public class InboxAction extends Action
{
	private static Log log=LogFactory.getLog(InboxAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		MemberData user=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			user=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if(user!=null)
		{
			try
			{
				List<MessageData> messages=MessageModel.getMessages(user.getId(),getDataSource(request));
				request.setAttribute("mes",messages);
				return(mapping.findForward(Constants.SUCCESS));
			}
			catch(SQLException sql)
			{
				log.error("SQL Exception when getting users messages.",sql);
			}
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}
