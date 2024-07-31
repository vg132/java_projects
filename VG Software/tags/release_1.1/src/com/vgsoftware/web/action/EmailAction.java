package com.vgsoftware.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import com.vgsoftware.web.form.EmailForm;
import com.vgsoftware.web.helper.Constants;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-17 - Class created.
 */
public class EmailAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
	  EmailForm ef=(EmailForm)form;
	  MessageResources mr=getResources(request);
	  try
		{
	  	SimpleEmail se=new SimpleEmail();
	  	se.setHostName(mr.getMessage("email.host"));
	  	se.addTo(mr.getMessage("email.contact"));
	  	se.setFrom(ef.getEmail());
	  	se.setSubject(ef.getSubject());
	  	se.setMsg(ef.getMessage());
	  	se.send();
	  	
	  	request.setAttribute("headline",mr.getMessage("info.contact.headline"));
	  	request.setAttribute("title",mr.getMessage("info.contact.title"));
	  	request.setAttribute("message",mr.getMessage("info.contact.message"));
	  	return(mapping.findForward(Constants.INFO));
		}
	  catch(EmailException ee)
		{
	  	ee.printStackTrace(System.err);
		}

		ActionErrors ae=new ActionErrors();
		ae.add("error_unknown",new ActionMessage("error.unknown"));
		addErrors(request,ae);
		
		request.setAttribute("email",ef.getEmail());
		request.setAttribute("subject",ef.getSubject());
		request.setAttribute("message",ef.getMessage());

    return(mapping.findForward(Constants.FAILURE));
	}
}
