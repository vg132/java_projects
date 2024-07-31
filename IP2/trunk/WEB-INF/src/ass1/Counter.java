/*
 * Created on: 2003-okt-20
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-20 Created by Viktor
 */
package ass1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple text base counter. Saves the visiter statistics to a file on the server.
 */
public class Counter extends HttpServlet
{
	private File file=null;

	/**
	 * Get filename form web.xml config file. If no filename is specified
	 * use default, "counter.txt".
	 * 
	 * @param config the servlet configuration.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		if(config.getInitParameter("file")!=null)
			file=new File(config.getInitParameter("file"));
		else
			file=new File(getServletContext().getRealPath("counter.txt"));
		try
		{
			if(!file.exists())
				file.createNewFile();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		super.init(config);
	}

	/**
	 * Handle the requst from the user.
	 * 
	 * @param request the user request object.
	 * @param response the user response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		int visiters=read();
		write(++visiters);
		response.setContentType("text/plain");
		ServletOutputStream out=response.getOutputStream();
		out.println(visiters);
	}

	/**
	 * Write a int to the selected file.
	 * 
	 * @param nr the total nr of visiters, this will be written to the file.
	 */
	public synchronized void write(int nr)
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(file));
			dos.writeInt(nr);
			dos.close();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * Read a int from the selected file.
	 * 
	 * @return the total nr of visiters.
	 */
	public synchronized int read()
	{
		int nr=-1;
		try
		{
			DataInputStream dis=new DataInputStream(new FileInputStream(file));
			nr=dis.readInt();
			dis.close();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(nr);
	} 
}