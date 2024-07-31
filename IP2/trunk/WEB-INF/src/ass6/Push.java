/*
 * Created on: 2003-okt-22
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-22 Created by Viktor
 */
package ass6;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartResponse;

/**
 * Serverside reload of webpage.
 */
public class Push extends HttpServlet
{
	/**
	 * Countdown from 10 and then display a text.
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
		ServletOutputStream out = response.getOutputStream();
		MultipartResponse multi=new MultipartResponse(response);
		for(int i=10;i>=0;i--)
		{
			multi.startResponse("text/plain");
			if(i>0)
				out.println(i);
			else
				out.println("You have reched the goal for this page, thank you for visiting and come back again.");
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie)
			{
			}
		}
		multi.finish();
	}
}