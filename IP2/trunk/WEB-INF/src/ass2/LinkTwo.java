/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass2;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple GET parameter example part 2. 
 */
public class LinkTwo extends HttpServlet
{
	/**
	 * Show the parameters sent to this servlet.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		ServletOutputStream out=response.getOutputStream();
		response.setContentType("text/plain");

		Enumeration e=request.getParameterNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println("The value of "+name+" is: \""+request.getParameter(name)+"\"");
		}
	}
}
