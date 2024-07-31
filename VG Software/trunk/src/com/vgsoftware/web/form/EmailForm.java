package com.vgsoftware.web.form;

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
public class EmailForm extends ActionForm
{
  private String email=null;
  private String subject=null;
  private String message=null;

  /**
   * @return Returns the email address.
   */
  public String getEmail()
  {
    return(email);
  }

  /**
   * @param email The email address to set.
   */
  public void setEmail(String email)
  {
    this.email=email;
  }

  /**
   * @return Returns the message.
   */
  public String getMessage()
  {
    return(message);
  }

  /**
   * @param message The message to set.
   */
  public void setMessage(String message)
  {
    this.message=message;
  }

  /**
   * @return Returns the subject.
   */
  public String getSubject()
  {
    return(subject);
  }

  /**
   * @param subject The subject to set.
   */
  public void setSubject(String subject)
  {
    this.subject=subject;
  }

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((email==null)||(email.trim().equals("")))
			errors.add("no_email",new ActionMessage("error.email.required"));
		else if((subject==null)||(subject.trim().equals("")))
			errors.add("no_subject",new ActionMessage("error.email.subject"));
		else if((message==null)||(message.trim().equals("")))
		  errors.add("no_message",new ActionMessage("error.email.message"));
		request.setAttribute("email",getEmail());
		request.setAttribute("subject",getSubject());
		request.setAttribute("message",getMessage());
		return(errors);
	}
}
