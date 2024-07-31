/*
 * Created on: 2003-okt-22
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-22 Created by Viktor
 */
package ass6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Client side refresh and counter.
 */
public class Refresh extends HttpServlet
{
	private String htmlString=null;
	private File file=new File("recounter.txt");
	
	/**
	 * Get filename form web.xml config file. If no filename is specified
	 * use default, "counter.txt". Also get the html template used to
	 * display the counter. 
	 * 
	 * @param config the servlet configuration.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("template")));
		if(config.getInitParameter("file")!=null)
			file=new File(config.getInitParameter("file"));
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
	 * Adds the refresh header to the response and creates the html response text.
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
		Mixer mix=new Mixer(htmlString);
		int visiters=read();
		write(++visiters);
		response.setContentType("text/html");
		response.setHeader("Refresh","4; URL=/dsv/servlet/ass6a");
		mix.add("<!--hits-->",""+visiters);
		mix.add("<!--time-->",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		response.getOutputStream().println(mix.getMix());
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
