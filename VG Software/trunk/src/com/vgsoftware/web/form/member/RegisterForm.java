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

public class RegisterForm extends ActionForm
{
	private String username=null;
	private String password=null;
	private String password2=null;
	private String email=null;
	private String email2=null;

	public String getEmail()
	{
		return(email);
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getEmail2()
	{
		return(email2);
	}

	public void setEmail2(String email2)
	{
		this.email2=email2;
	}

	public String getPassword()
	{
		return(password);
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public String getPassword2()
	{
		return(password2);
	}

	public void setPassword2(String password2)
	{
		this.password2=password2;
	}

	public String getUsername()
	{
		return(username);
	}

	public void setUsername(String username)
	{
		this.username=username;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((username==null)||(username.trim().length()<3))
			errors.add("username",new ActionMessage("error.username.required"));
		if((password==null)||(password.trim().length()<6))
			errors.add("password",new ActionMessage("error.password.required"));
		else if((password2==null)||(!password2.equals(password)))
			errors.add("password_match",new ActionMessage("error.password.match"));
		if((email==null)||(email.trim().length()<7))
			errors.add("email",new ActionMessage("error.email.required"));
		else if((email2==null)||(!email2.equals(email)))
			errors.add("email_match",new ActionMessage("error.email.match"));

		request.setAttribute("username",getUsername());
		request.setAttribute("email",getEmail());
		request.setAttribute("email2",getEmail2());

		return(errors);
	}
}
