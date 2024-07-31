package com.vgsoftware.web.action.pm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import com.vgsoftware.vgutil.misc.WebHelp;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.pm.MessageData;
import com.vgsoftware.web.form.pm.MessageForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;
import com.vgsoftware.web.model.pm.MessageModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-04 - Added email sending and error messages.
 * 2005-sep-27 - Rewritten to use model class.
 * 2005-jan-01 - Class created.
 */
public class SendAction extends Action
{
	private static Log log=LogFactory.getLog(SendAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		MessageForm mf=(MessageForm)form;
		MemberData user=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			user=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		if(user!=null)
		{
			try
			{	
				MemberData recipient=MemberModel.getMember(mf.getRecipient(),getDataSource(request));
				if(recipient!=null)
				{
					MessageData md=new MessageData();
					md.setSenderId(user.getId());
					md.setTitle(WebHelp.toSafeHTML(mf.getTitle()));
					md.setContent(WebHelp.toSafeHTML(mf.getMessage()));
					if(MessageModel.addMessage(recipient.getId(),md,getDataSource(request)))
					{
						MessageResources mr=getResources(request);
						try
						{
							SimpleEmail se=new SimpleEmail();
							se.setHostName(mr.getMessage("email.host"));
							se.addTo(recipient.getEmail());
							se.setFrom(mr.getMessage("email.contact"));
							se.setSubject(mr.getMessage("email.pm.subject"));

							String content="";
							String line="";
							BufferedReader br=new BufferedReader(new FileReader(getServlet().getServletContext().getRealPath(mr.getMessage("email.pm.content"))));
							while((line=br.readLine())!=null)
								content+=line+"\n";
							content=content.replace("<!--sender-->",user.getName());
							content=content.replace("<!--recipient-->",recipient.getName());
							content=content.replace("<!--password-->",user.getName());
							content=content.replace("<!--title-->",md.getTitle());
							content=content.replace("<!--url-->",mr.getMessage("baseurl"));
							se.setMsg(content);
							se.send();						
						}
						catch(EmailException ee)
						{
							log.error("EmailException when sending email to "+recipient.getName()+".",ee);
						}
						return(mapping.findForward(Constants.SUCCESS));
					}
					else
					{
						log.error("Unable to send PM to "+recipient.getName()+" from "+user.getName()+".");
						ae.add("unknown_error",new ActionMessage("error.pm.unabletosend"));
					}
				}
				else
				{
					ae.add("recipientnotfound",new ActionMessage("error.pm.recipientnotfound"));
				}
			}
			catch(SQLException sql)
			{
				log.error("SQLException when trying to send a message.",sql);
			}
		}
		request.setAttribute("message",mf.getMessage());
		request.setAttribute("title",mf.getTitle());
		request.setAttribute("recipient",mf.getRecipient());
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}
}
