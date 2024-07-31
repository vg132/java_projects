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
import com.vgsoftware.jbb.data.TopicData;
import com.vgsoftware.jbb.form.TopicForm;
import com.vgsoftware.jbb.model.ForumModel;
import com.vgsoftware.jbb.model.PostModel;
import com.vgsoftware.jbb.model.TopicModel;

public class AddTopicAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			if(request.getSession().getAttribute(Globals.USER_KEY)!=null)
			{
				Timestamp postDate=new Timestamp(System.currentTimeMillis());
				MemberData md=(MemberData)request.getSession().getAttribute(Globals.USER_KEY);
				TopicForm tf=(TopicForm)form;
				TopicData td=new TopicData();
				td.setTitle(tf.getTitle());
				td.setPostDate(postDate);
				td.setForumId(tf.getForumId());
				td.setMemberId(md.getId());
				td.setReplies(0);
				int topicId=TopicModel.addTopic(getDataSource(request),td);
				if(topicId!=-1)
				{
					PostData pd=new PostData();
					pd.setMemberId(md.getId());
					pd.setPostDate(postDate);
					pd.setContent(tf.getContent());
					pd.setTopicId(topicId);
					if(PostModel.addPost(getDataSource(request),pd)!=-1)
					{
						ForumModel.addTopic(getDataSource(request),tf.getForumId());
						
						ForwardParameters fp=new ForwardParameters();
						fp.add("forumId",""+tf.getForumId());
						fp.add("topicId",""+pd.getTopicId());
						return(fp.forward(mapping.findForward(Globals.SUCCESS)));
					}
					else
						TopicModel.deleteTopic(getDataSource(request),topicId);
				}
			}
			else
			{
				ActionErrors ae=new ActionErrors();
				ae.add("login_error",new ActionMessage("errors.login"));
				addErrors(request,ae);
				request.setAttribute("content",((TopicForm)form).getContent());
				request.setAttribute("title",((TopicForm)form).getTitle());
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
