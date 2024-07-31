/*
 * Created on 2004-nov-28 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.web.action.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
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

import com.vgsoftware.vgutil.misc.CookieHelper;
import com.vgsoftware.vgutil.misc.MD5;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.form.member.LoginForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberExtraModel;
import com.vgsoftware.web.model.member.MemberModel;
import com.vgsoftware.web.model.member.MemberServiceModel;

public class LoginAction extends Action
{
	private static Log log=LogFactory.getLog(LoginAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		CookieHelper ch=new CookieHelper(request,response);
		LoginForm lf=(LoginForm)form;
		Cookie c=null;
		try
		{
			MemberData md=MemberModel.getMember(lf.getUsername(),lf.getPassword(),getDataSource(request));
			if(md!=null)
			{
				md.addServices(MemberServiceModel.getServices(md.getId(),getDataSource(request)));
				md.addExtras(MemberExtraModel.getExtra(md.getId(),getDataSource(request)));

				request.getSession().setAttribute(Constants.USER_KEY,md);

				if(lf.getAutologin()!=null)
					ch.createCookie("vgsoftware","/",315360000,md.getName()+"|"+MD5.parse(md.getJoinDate().toString()+md.getName()));
				return(mapping.findForward(Constants.SUCCESS));
			}
			else
			{
				log.debug("Login faild.");
				ae=new ActionErrors();
				ae.add("error_login",new ActionMessage("error.login"));
				addErrors(request,ae);
			}
		}
		catch(SQLException sql)
		{
			log.error("Unable to login.",sql);
		}

		if(ae==null)
		{
			ae=new ActionErrors();
			ae.add("error_unknown",new ActionMessage("error.unknown"));
			addErrors(request,ae);
		}
		request.setAttribute("username",lf.getUsername());
		return(mapping.findForward(Constants.FAILURE));
	}
}
