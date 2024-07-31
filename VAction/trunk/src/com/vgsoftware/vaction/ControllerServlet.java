package com.vgsoftware.vaction;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.vgsoftware.vaction.data.ActionData;
import com.vgsoftware.vaction.data.Globals;
import com.vgsoftware.vaction.data.ViewData;
import com.vgsoftware.vaction.handler.VactionConfigHandler;

public class ControllerServlet extends HttpServlet
{
	private Map<String,ActionData> actions=null;
	private Map<String,String> globals=null;
	private DataSource dataSource=null;
	private Action defaultAction=null;

	public void init()
	throws ServletException
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			//Load the mapping xml file
			VactionConfigHandler handler=new VactionConfigHandler();
			saxParser.parse(getServletContext().getRealPath("/")+"/WEB-INF/vaction-config.xml",handler);

			if(handler.getDataSourceData()!=null)
				dataSource=handler.getDataSourceData().getDataSource();
			globals=handler.getGlobals();
			defaultAction=handler.getDefaultAction();
			actions=handler.getActions();
		}
		catch(SAXException sax)
		{
			sax.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		doGet(request,response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String uri=request.getRequestURI();
		if(!request.getContextPath().equals("/"))
			uri=uri.replaceFirst(request.getContextPath(),"");
		if(actions.containsKey(uri))
		{
			//add config and datasource to the request context.
			request.setAttribute(Globals.DATA_SOURCE,dataSource);

			//if there is a default action run it
			if(defaultAction!=null)
				defaultAction.execute(request,response);

			//get the action data for this request
			ActionData ad=actions.get(uri);
			if(ad!=null)
			{
				String view=null;
				if(ad.getAction()!=null)
					view=ad.getAction().execute(request,response);
				ViewData vd=ad.getView(view);
				if(vd!=null)
				{
					if(vd.isRedirect())
						response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(vd.getUrl()));
					else
						request.getRequestDispatcher(vd.getUrl()).forward(request,response);
				}
				else if(globals.containsKey(view))
				{
					request.getRequestDispatcher((String)globals.get(view)).forward(request,response);
				}
			}
		}
	}
}
