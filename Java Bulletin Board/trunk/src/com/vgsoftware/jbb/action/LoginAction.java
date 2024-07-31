package com.vgsoftware.jbb.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.data.MemberData;
import com.vgsoftware.jbb.form.LoginForm;
import com.vgsoftware.jbb.model.MemberModel;

public class LoginAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		LoginForm lf=(LoginForm)form;
		try
		{
			MemberData md=MemberModel.login(getDataSource(request),lf.getUsername(),lf.getPassword());
			if(md!=null)
			{
				request.getSession().setAttribute(Globals.USER_KEY,md);
				return(mapping.findForward(Globals.SUCCESS));
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ActionErrors ae=new ActionErrors();
		ae.add("login_error",new ActionMessage("errors.login.failure"));
		addErrors(request,ae);
		return(mapping.findForward(Globals.FAILURE));
	}
}
