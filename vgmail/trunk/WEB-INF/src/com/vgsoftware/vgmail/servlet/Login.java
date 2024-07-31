/*
 * Created on: 2003-okt-24
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-24 Created by Viktor
 */
package com.vgsoftware.vgmail.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.vgmail.misc.MailUser;

/**
 * Servlet that handles loggins to the mail server.
 */
public class Login extends HttpServlet
{
	/**
	 * When user want to loggin we create a new MailUser object for this user and
	 * send in the username, password and a reference to the  server configuration 
	 * for easy access in other parts of the system. Then save the mailuser object in
	 * this users session and redirect to the pagebuilder servlet.
	 * No username/password check is done here, this only preperes the user for later
	 * login.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String username=request.getParameter("username");
		String password=request.getParameter("password");

		MailUser mu=new MailUser((Map)getServletContext().getAttribute("config"),username,password);

		request.getSession().setAttribute("mailuser",mu);
		request.getSession().removeAttribute("folderlist");
		response.sendRedirect(request.getContextPath()+"/servlet/pagebuilder");
	}
}
