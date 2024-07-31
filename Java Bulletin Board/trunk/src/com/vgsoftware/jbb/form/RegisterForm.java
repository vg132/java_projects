package com.vgsoftware.jbb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class RegisterForm extends ActionForm
{
	private String username=null;
	private String password=null;
	private String email=null;

	/**
	 * Gets the email.
	 * @return Returns the email.
	 */
	public String getEmail()
	{
		return(email);
	}

	/**
	 * Sets the email.
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}

	/**
	 * Gets the password.
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return(password);
	}

	/**
	 * Sets the password.
	 * @param password The password to set.
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}

	/**
	 * Gets the username.
	 * @return Returns the username.
	 */
	public String getUsername()
	{
		return(username);
	}

	/**
	 * Sets the username.
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username=username;
	}

	/**
	 * Validate the values of this form.
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((username==null)||(username.equals(""))||(username.length()<6))
			errors.add("register_error",new ActionMessage("errors.register.invalid_username"));
		else if((password==null)||(password.equals(""))||(password.length()<6))
			errors.add("register_error", new ActionMessage("errors.register.invalid_password"));
		else if((email==null)||(email.matches(".+@.+\\.[a-z]+")==false))
			errors.add("register_error",new ActionMessage("errors.register.invalid_email"));
		request.setAttribute("username",username);
		request.setAttribute("email",email);
		return(errors);
	}	
}
