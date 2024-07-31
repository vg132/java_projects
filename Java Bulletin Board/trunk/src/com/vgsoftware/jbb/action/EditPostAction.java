package com.vgsoftware.jbb.action;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.jbb.ForwardParameters;
import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.data.MemberData;
import com.vgsoftware.jbb.data.PostData;
import com.vgsoftware.jbb.form.EditForm;
import com.vgsoftware.jbb.model.PostModel;

public class EditPostAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		EditForm ef=(EditForm)form;
		try
		{
			if(request.getSession().getAttribute(Globals.USER_KEY)!=null)
			{
				MemberData md=(MemberData)request.getSession().getAttribute(Globals.USER_KEY);
				String content=request.getParameter("content");
				PostData pd=PostModel.getPost(getDataSource(request),ef.getPostId());
				if(pd.getMemberId()==md.getId())
				{
					pd.setContent(content);
					pd.setEditDate(new Timestamp(System.currentTimeMillis()));
					PostModel.updatePost(getDataSource(request),pd);
				}
			}
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace(System.err);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ForwardParameters fp=new ForwardParameters();
		fp.add("forumId",""+ef.getForumId());
		fp.add("topicId",""+ef.getTopicId());
		return(fp.forward(mapping.findForward(Globals.SUCCESS)));
	}
}
