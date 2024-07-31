package com.vgsoftware.web.action.member;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

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
import com.vgsoftware.web.form.member.RecoverPasswordForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-17 - Class created.
 */
public class RecoverPasswordAction extends Action
{
	private static Log log=LogFactory.getLog(RecoverPasswordAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		RecoverPasswordForm rpf=(RecoverPasswordForm)form;

		try
		{
			MemberData md=MemberModel.getMember(rpf.getUsername(),getDataSource(request));
			if((md!=null)&&(md.getEmail().equals(rpf.getEmail())))
			{
				StringBuffer password=new StringBuffer();
				Random rand=new Random(System.currentTimeMillis());
				for(int i=0;i<10;i++)
					password.append(rand.nextInt(100));
				if(MemberModel.updatePassword(md.getId(),password.toString(),getDataSource(request)))
				{
					MessageResources mr=getResources(request);
					request.setAttribute("headline",mr.getMessage("info.recovery.headline"));
					request.setAttribute("title",mr.getMessage("info.recovery.title"));
					request.setAttribute("message",mr.getMessage("info.recovery.message"));
					try
					{
						SimpleEmail se=new SimpleEmail();
						se.setHostName(mr.getMessage("email.host"));
						se.addTo(rpf.getEmail());
						se.setFrom(mr.getMessage("email.contact"));
						se.setSubject(mr.getMessage("email.recovery.subject"));
					
						String content="";
						String line="";
					
						BufferedReader br=new BufferedReader(new FileReader(getServlet().getServletContext().getRealPath(mr.getMessage("email.recovery.content"))));
						while((line=br.readLine())!=null)
							content+=line+"\n";
					
						content=content.replace("<!--username-->",rpf.getUsername());
						content=content.replace("<!--password-->",password.toString());
					
						se.setMsg(content);
						se.send();
						return(mapping.findForward(Constants.INFO));
					}
					catch(EmailException ee)
					{
						log.error("unable to send email to "+md.getEmail()+".",ee);
						ae.add("unabletosendemail",new ActionMessage("error.recover.email"));
					}
				}
				else
				{
					ae.add("unabletoupdate",new ActionMessage("error.unknown.database"));
				}
			}
			else
			{
				ae.add("wrongemailorname",new ActionMessage("error.recover.wrong"));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to recover password",sql);
		}
		addErrors(request,ae);
		request.setAttribute("username",rpf.getUsername());
		request.setAttribute("email",rpf.getEmail());
		return(mapping.findForward(Constants.FAILURE));
	}
}