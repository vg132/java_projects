/*
 * Created on 2004-nov-28 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 * 2005-okt-05 - Updated to use model classes.
 * 
 */
package com.vgsoftware.web.action.member;

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

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.form.member.RegisterForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;


public class RegisterAction extends Action
{
	private static Log log=LogFactory.getLog(RegisterAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		RegisterForm rf=(RegisterForm)form;
		try
		{
			MemberData md=new MemberData();
			md.setEmail(rf.getEmail());
			md.setName(rf.getUsername());
			md.setPassword(rf.getPassword());
			md.setGroupId(2);
			if(MemberModel.getMember(rf.getUsername(),getDataSource(request))==null)
			{
				if(MemberModel.addMember(md,getDataSource(request)))
				{
					MessageResources mr=getResources(request);
					request.setAttribute("headline",mr.getMessage("info.registration.headline"));
					request.setAttribute("title",mr.getMessage("info.registration.title"));
					request.setAttribute("message",mr.getMessage("info.registration.message"));
					try
					{
						SimpleEmail se=new SimpleEmail();
						se.setHostName(mr.getMessage("email.host"));
						se.addTo(rf.getEmail());
						se.setFrom(mr.getMessage("email.contact"));
						se.setSubject(mr.getMessage("email.registration.subject"));

						// Read the mail template
						String content="";
						String line="";
						BufferedReader br=new BufferedReader(new FileReader(getServlet().getServletContext().getRealPath(mr.getMessage("email.registration.content"))));
						while((line=br.readLine())!=null)
							content+=line+"\n";
						content=content.replace("<!--username-->",rf.getUsername());
						content=content.replace("<!--password-->",rf.getPassword());

						se.setMsg(content);
						se.send();
					}
					catch(EmailException ee)
					{
						log.error("Unable to send email to "+rf.getEmail()+".",ee);
					}
					return(mapping.findForward(Constants.INFO));
				}
			}
			else
			{
				ae.add("memberinuse",new ActionMessage("error.register.userexist"));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when adding a new member",sql);
			ae.add("dberror",new ActionMessage("error.unknown.database"));
		}
		request.setAttribute("username",rf.getUsername());
		request.setAttribute("email",rf.getEmail());
		request.setAttribute("email2",rf.getEmail2());
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}
}
