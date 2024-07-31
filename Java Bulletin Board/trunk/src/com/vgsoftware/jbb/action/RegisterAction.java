package com.vgsoftware.jbb.action;

import java.io.IOException;
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
import com.vgsoftware.jbb.form.RegisterForm;
import com.vgsoftware.jbb.model.MemberModel;

public class RegisterAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		RegisterForm rf=(RegisterForm)form;
		try
		{
			MemberData md=new MemberData();
			md.setEmail(rf.getEmail());
			md.setPassword(rf.getPassword());
			md.setUsername(rf.getUsername());
			md.setMemberGroup(2);
			
			if(MemberModel.getMember(getDataSource(request),rf.getUsername())!=null)
			{
				ae.add("in_use",new ActionMessage("errors.register.username_in_use"));
				addErrors(request,ae);
				request.setAttribute("username",rf.getUsername());
				request.setAttribute("email",rf.getEmail());
				return(mapping.findForward(Globals.FAILURE));
			}
			if(MemberModel.addMember(getDataSource(request),md)!=-1)
			{
				ae.add("created",new ActionMessage("info.register.created"));
				addErrors(request,ae);
				return(mapping.findForward(Globals.SUCCESS));
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ae.add("unknown",new ActionMessage("errors.unknown"));
		addErrors(request,ae);
		request.setAttribute("username",rf.getUsername());
		request.setAttribute("email",rf.getEmail());
		return(mapping.findForward(Globals.FAILURE));
	}
}
