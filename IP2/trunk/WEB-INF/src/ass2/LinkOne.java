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
 * Simple GET parameter test.
 */
public class LinkOne extends HttpServlet
{
	private String htmlString=null;
	
	/**
	 * Load template.
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
	 * Get the parameter and append it to the link in the html document.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest reqeust, HttpServletResponse response)
	throws ServletException, IOException
	{
		Mixer mix=new Mixer(htmlString);
		ServletOutputStream out=response.getOutputStream();
		response.setContentType("text/html");
		mix.clearAll();
		String name=reqeust.getParameter("name");
		name="?name="+name+"&email=";
		mix.add("<!--query1-->",name+"kalle-anka@dsv.su.se");
		mix.add("<!--query2-->",name+"musse-pigg@dsv.su.se");
		out.println(mix.getMix());
	}
}
