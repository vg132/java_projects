/*
 * Created on 2004-nov-29 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.web.form.member;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class SettingsForm extends ActionForm
{
	private String email=null;
	private String currentPassword=null;
	private String newPassword=null;
	private String newPassword2=null;

	public String getCurrentPassword()
	{
		return (currentPassword);
	}

	public void setCurrentPassword(String currentPassword)
	{
		this.currentPassword=currentPassword;
	}

	public String getEmail()
	{
		return (email);
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getNewPassword()
	{
		return (newPassword);
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword=newPassword;
	}

	public String getNewPassword2()
	{
		return (newPassword2);
	}

	public void setNewPassword2(String newPassword2)
	{
		this.newPassword2=newPassword2;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((currentPassword==null)||(currentPassword.trim().equals("")))
			errors.add("no_password",new ActionMessage("error.password.required"));
		else if((newPassword!=null)&&(!newPassword.equals(newPassword2)))
			errors.add("password_match",new ActionMessage("error.password.match"));
		if((email==null)||(email.trim().length()<7))
			errors.add("email_invalid",new ActionMessage("error.email.required"));
		return(errors);
	}
}
