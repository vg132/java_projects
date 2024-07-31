/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import mixer.Mixer;

/**
 * File upload service.
 */
public class Upload extends HttpServlet
{
	private String htmlString=null;
	private String infoString=null;
	private String uploadDirectory=null;

	/**
	 * Load templates and upload directory.
	 * 
	 * @param config
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("template")));
		infoString=Mixer.getContent(new File(config.getInitParameter("infotemplate")));
		uploadDirectory=config.getInitParameter("uploaddirectory");
		super.init(config);
	}
	
	/**
	 * List all files currently uploaded to the server.
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
		
		File[] fileList=new File(getServletContext().getRealPath(uploadDirectory)).listFiles();
		for(int i=0;i<fileList.length;i++)
		{
			mix.add("<!!--file-->","<!--name-->",fileList[i].getName());
			mix.add("<!!--file-->","<!--type-->",fileList[i].getName().substring(fileList[i].getName().indexOf(".")));
			mix.add("<!!--file-->","<!--size-->",""+fileList[i].length());
			mix.add("<!!--file-->","<!--url-->","/dsv"+uploadDirectory+fileList[i].getName());
		}
		response.getOutputStream().println(mix.getMix());
	}
	
	/**
	 * Save the file and show information about the file.
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
		Mixer mix=new Mixer(infoString);
		
		MultipartRequest multi = new MultipartRequest(request,getServletContext().getRealPath(uploadDirectory),1 * 1024 * 1024);
		Enumeration files = multi.getFileNames();

		while(files.hasMoreElements())
		{
			String name=(String)files.nextElement();
			File file=multi.getFile(name);
			mix.add("<!--file-name-->",multi.getFilesystemName(name));
			mix.add("<!--mime-->",multi.getContentType(name));
			mix.add("<!--size-->",""+file.length());
			if(multi.getContentType(name).startsWith("text"))
			{
				BufferedReader br=new BufferedReader(new FileReader(file));
				String line=null;
				String text="";
				while((line=br.readLine())!=null)
				{
					text+="\n"+line;
				}
				mix.add("<!!--text-preview-->","<!--txt-->",text);
				mix.removeContext("<!!--img-preview-->");
			}
			else if(multi.getContentType(name).startsWith("image"))
			{
				mix.add("<!!--img-preview-->","<!--img-->","/dsv"+uploadDirectory+file.getName());
				mix.removeContext("<!!--text-preview-->");
			}
		}
		response.getOutputStream().print(mix.getMix());
	}
}