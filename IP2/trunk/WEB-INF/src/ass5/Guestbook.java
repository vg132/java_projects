/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass5;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Guestbook servlet that lest users enter a message.
 */
public class Guestbook extends HttpServlet
{
	private String htmlString=null;
	private String dbDriver=null;
	private String dbURL=null;
	private String dbUser=null;
	private String dbPassword=null;
	
	/**
	 * Get database information and load template.
	 * 
	 * @param config Servlet configuration.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("template")));
		dbDriver=config.getInitParameter("dbDriver");
		dbURL=config.getInitParameter("dbURL");
		dbUser=config.getInitParameter("dbUser");
		dbPassword=config.getInitParameter("dbPassword");
		super.init(config);
	}
	
	/**
	 * Only used to display the guestbook messages.
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
		Mixer mix=new Mixer(htmlString);
		Connection conn=openConnection();
		try
		{
			showMessages(conn,mix);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
			response.setContentType("text/plain");
			out.println("SQL Exception - Unable to get messages from the database.");
			return;
		}
		response.setContentType("text/html");
		out.println(mix.getMix());
	}
	
	/**
	 * Only used when a new message is posted.
	 * 
	 * Get data from the parameters, open a database connection and update the database.
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
		Mixer mix=new Mixer(htmlString);
		String name=removeHTML(request.getParameter("name"));
		String email=removeHTML(request.getParameter("email"));
		String url=removeHTML(request.getParameter("url"));
		String message=removeHTML(request.getParameter("message"));
		
		Connection conn=openConnection();
		try
		{
			PreparedStatement ps=conn.prepareStatement("INSERT INTO ip2_guestbook (name,email,url,message,post_date) VALUES(?,?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2,email);
			ps.setString(3,url);
			ps.setString(4,message);
			ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
			ps.execute();
			showMessages(conn,mix);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
			response.setContentType("text/plain");
			out.println("SQL Exception - Unable to insert guestbook message and/or get message.");
			return;
		}
		response.setContentType("text/html");
		out.println(mix.getMix());
	}
	
	/**
	 * Loads all messages from the database and creates a html page based on the template.
	 * 
	 * @param conn A open database <code>Connection</code> object.
	 * @param mix The html template mixer.
	 */
	private void showMessages(Connection conn, Mixer mix)
	throws SQLException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Statement stmnt=conn.createStatement();
		ResultSet rs=stmnt.executeQuery("SELECT id, name, email, url, message, post_date FROM ip2_guestbook ORDER BY post_date DESC");
		if(rs.next())
		{
			do
			{
				mix.add("<!!--message-block-->","<!--nr-->",rs.getString("id"));
				mix.add("<!!--message-block-->","<!--email-->",rs.getString("email"));
				mix.add("<!!--message-block-->","<!--name-->",rs.getString("name"));
				mix.add("<!!--message-block-->","<!--url-->",rs.getString("url"));
				mix.add("<!!--message-block-->","<!--message-->",rs.getString("message"));
				mix.add("<!!--message-block-->","<!--date-->",sdf.format(rs.getTimestamp("post_date")));				
			}while(rs.next());	
		}
		else
		{
			mix.removeContext("<!!--message-block-->");
		}
	}

	/**
	 * Remove all HTML taggs from the string.
	 * 
	 * @param text The text to work on.
	 * @return Clean text where all HTML taggs are removed.
	 */
	private String removeHTML(String text)
	{
		Pattern p = Pattern.compile("<.*>"); 
		Matcher m=p.matcher(text);
		return(m.replaceAll(""));
	}

	/**
	 * Open a database connection to the sql server using the provieded information.
	 * 
	 * @return Returns a open <code>Connection</code> if successfull, otherwise <code>null</code>.
	 */
	private Connection openConnection()
	{
		Connection conn=null;
		try
		{
			Class.forName(dbDriver).newInstance();
			conn=DriverManager.getConnection(dbURL,dbUser,dbPassword);
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace(System.err);
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace(System.err);
		}
		catch(IllegalAccessException iae)
		{
			iae.printStackTrace(System.err);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(conn);
	}
}
