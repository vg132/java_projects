/*
 * Created on 2004-nov-28 by viktor
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

public class LoginForm extends ActionForm
{
	private String username=null;
	private String password=null;
	private String autologin=null;

	public String getPassword()
	{
		return(password);
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public String getUsername()
	{
		return(username);
	}

	public void setUsername(String username)
	{
		this.username=username;
	}
	
	public String getAutologin()
	{
		return(autologin);
	}

	public void setAutologin(String autologin)
	{
		this.autologin=autologin;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((username==null)||(username.trim().equals("")))
			errors.add("no_username",new ActionMessage("error.username.required"));
		if((password==null)||(password.trim().equals("")))
			errors.add("no_password",new ActionMessage("error.password.required"));
		request.setAttribute("username",getUsername());
		return(errors);
	}
}
