package com.vgsoftware.web.form.member;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-17 - Class created.
 */
public class RecoverPasswordForm extends ActionForm
{
  private String username=null;
  private String email=null;

	/**
	 * @return Returns the email.
	 */
	public String getEmail()
	{
		return(email);
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername()
	{
		return(username);
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username=username;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((email==null)||(email.trim().equals("")))
			errors.add("no_email",new ActionMessage("error.email.required"));
		else if((username==null)||(username.trim().equals("")))
			errors.add("no_username",new ActionMessage("error.username.required"));
		request.setAttribute("email",getEmail());
		request.setAttribute("username",getUsername());
		return(errors);
	}
}
