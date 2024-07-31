package com.vgsoftware.jbb.action;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.jbb.ForwardParameters;
import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.data.MemberData;
import com.vgsoftware.jbb.data.PostData;
import com.vgsoftware.jbb.form.ReplyForm;
import com.vgsoftware.jbb.model.ForumModel;
import com.vgsoftware.jbb.model.PostModel;
import com.vgsoftware.jbb.model.TopicModel;

public class AddReplyAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			if(request.getSession().getAttribute(Globals.USER_KEY)!=null)
			{
				MemberData md=(MemberData)request.getSession().getAttribute(Globals.USER_KEY);
				ReplyForm rf=(ReplyForm)form;
				PostData pd=new PostData();
				pd.setContent(rf.getContent());
				pd.setTopicId(rf.getTopicId());
				pd.setPostDate(new Timestamp(System.currentTimeMillis()));
				pd.setMemberId(md.getId());
				if(PostModel.addPost(getDataSource(request),pd)!=-1)
				{
					TopicModel.addReply(getDataSource(request),pd.getTopicId());
					ForumModel.addReply(getDataSource(request),rf.getForumId());

					ForwardParameters fp=new ForwardParameters();
					fp.add("forumId",""+rf.getForumId());
					fp.add("topicId",""+rf.getTopicId());
					return(fp.forward(mapping.findForward(Globals.SUCCESS)));
				}
			}
			else
			{
				ActionErrors ae=new ActionErrors();
				ae.add("login_error",new ActionMessage("errors.login"));
				addErrors(request,ae);
				request.setAttribute("content",((ReplyForm)form).getContent());
				return(mapping.findForward(Globals.FAILURE));
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(mapping.findForward(Globals.FAILURE));
	}
}
