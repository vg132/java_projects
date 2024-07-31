package com.vgsoftware.web.action.member;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.helper.Constants;

public class LogoutAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		Cookie c=new Cookie("vgsoftware",null);
		c.setPath("/");
		c.setMaxAge(0);
		response.addCookie(c);
		request.getSession().invalidate();
		return(mapping.findForward(Constants.SUCCESS));
	}
}
