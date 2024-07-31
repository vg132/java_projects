package com.vgsoftware.web.action.news;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.news.NewsModel;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-sep-27 - Updates to use model classes.
 * 2004-dec-18 - Class created.
 */
public class IndexAction extends Action
{
	private static Log log=LogFactory.getLog(IndexAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			request.setAttribute("news",NewsModel.getNews(getDataSource(request)));
		}
		catch(SQLException sql)
		{
			log.error("Unable to get news for index page.",sql);
		}
		return(mapping.findForward(Constants.SUCCESS));
	}
}
