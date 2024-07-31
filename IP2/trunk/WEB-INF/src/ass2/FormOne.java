/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass2;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Simple post parameter example.
 */
public class FormOne extends HttpServlet
{
	private String htmlString=null;
	
	/**
	 * Load html template.
	 * 
	 * @param config
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("template")));
		super.init(config);
	}	
	
	/**
	 * Write parameter name in the responding html documnets hidden field. 
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest reqeust, HttpServletResponse response)
	throws ServletException, IOException
	{
		Mixer mix=new Mixer(htmlString);
		ServletOutputStream out=response.getOutputStream();
		response.setContentType("text/html");
		mix.clearAll();
		String name=reqeust.getParameter("name");
		mix.add("<!--data-->",name);
		out.println(mix.getMix());
	}
}
