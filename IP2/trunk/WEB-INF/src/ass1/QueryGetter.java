/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass1;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prints out both post and get parameters (name and value) sent to this servlet.
 */
public class QueryGetter extends HttpServlet
{
	/**
	 * Read the parameters and present the result.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
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

	/**
	 * Redirect to doPost.
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
		doPost(request,response);
	}
}
