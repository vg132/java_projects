package com.vgsoftware.jbb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginForm extends ActionForm
{
	private String username=null;
	private String password=null;

	/**
	 * Gets the password.
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return (password);
	}

	/**
	 * Sets the password.
	 * @param password The password to set.
	 */
	public void setPassword(String password)
	{
		this.password=password.trim();
	}

	/**
	 * Gets the username.
	 * @return Returns the username.
	 */
	public String getUsername()
	{
		return (username);
	}

	/**
	 * Sets the username.
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username=username.trim();
	}
	
	/**
	 * Validate the values of this form.
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((username==null)||(username.equals("")))
			errors.add("login_error",new ActionMessage("errors.login.failure"));
		else if((password==null)||(password.equals("")))
			errors.add("login_error", new ActionMessage("errors.login.failure"));
		request.setAttribute("username",username);
		return(errors);
	}
}
