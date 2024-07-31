/*
 * Created on 2004-nov-29 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 * 2005-sep-28 - Updated to use model classes.
 */
package com.vgsoftware.web.action.member;

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
import org.apache.struts.util.MessageResources;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.form.member.SettingsForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;


public class SettingsAction extends Action
{
	private static Log log=LogFactory.getLog(SettingsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		MemberData member=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			member=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if((member!=null)&&(form!=null))
		{
			SettingsForm sf=(SettingsForm)form;
			try
			{
				if(MemberModel.checkPassword(member.getId(),sf.getCurrentPassword(),getDataSource(request)))
				{
					member.setEmail(sf.getEmail());
					if(MemberModel.updateMember(member,getDataSource(request)))
					{
						if(!sf.getNewPassword().equals(""))
						{
							if(!MemberModel.updatePassword(member.getId(),sf.getNewPassword(),getDataSource(request)))
							{
								log.info("Unable to update password.");
								ActionErrors ae=new ActionErrors();
								ae.add("unknown_error",new ActionMessage("error.unknown"));
								addErrors(request,ae);
								return(mapping.findForward(Constants.FAILURE));
							}
						}
						request.getSession().setAttribute(Constants.USER_KEY,member);
						MessageResources mr=getResources(request);
						request.setAttribute("headline",mr.getMessage("info.settings.headline"));
						request.setAttribute("title",mr.getMessage("info.settings.title"));
						request.setAttribute("message",mr.getMessage("info.settings.message"));
						return(mapping.findForward(Constants.INFO));
					}
				}
				else
				{
					log.info("Wrong password.");
					ActionErrors ae=new ActionErrors();
					ae.add("password_error",new ActionMessage("error.settings.wrong_password"));
					addErrors(request,ae);
					return(mapping.findForward(Constants.FAILURE));
				}
			}
			catch(SQLException sql)
			{
				log.error("SQLException when trying to change email address and/or password.",sql);
			}
		}
		ActionErrors ae=new ActionErrors();
		ae.add("unknown_error",new ActionMessage("error.unknown"));
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}
}
