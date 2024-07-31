package com.vgsoftware.web.action.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.vgutil.misc.CookieHelper;
import com.vgsoftware.vgutil.misc.MD5;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberExtraModel;
import com.vgsoftware.web.model.member.MemberModel;
import com.vgsoftware.web.model.member.MemberServiceModel;

public class AutoLoginAction extends Action
{
	private static Log log=LogFactory.getLog(AutoLoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		CookieHelper ch=new CookieHelper(request,response);
		Cookie c=(Cookie)request.getAttribute("auto_login");
		String forwardTo=(String)request.getAttribute("forward_to");
		request.removeAttribute("auto_login");
		request.removeAttribute("forward_to");
		
		StringTokenizer st=new StringTokenizer(c.getValue(),"|");
		try
		{
			MemberData md=MemberModel.getMember(st.nextToken(),getDataSource(request));
			if(md!=null)
			{
				String hash=MD5.parse(md.getJoinDate().toString()+md.getName());
				if(hash.equals(st.nextToken()))
				{
					md.addServices(MemberServiceModel.getServices(md.getId(),getDataSource(request)));
					md.addExtras(MemberExtraModel.getExtra(md.getId(),getDataSource(request)));

					request.getSession().setAttribute(Constants.USER_KEY,md);
					return(new ActionForward(forwardTo));
				}
				else
				{
					log.debug("Hash don't match.");
				}
			}
			else
			{
				log.debug("User not found.");
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to auto login.",sql);
		}
		ch.removeCookie("vgsoftware");
		return(mapping.findForward(Constants.LOGIN));
	}
}
