package com.vgsoftware.vgutil.database;

import java.sql.SQLException;
import javax.servlet.ServletContext;

/**
 * DBConnect is used to create a new ConnectionPool for a servlet context.
 * By default it will look for context parameters in the current context's web.xml
 * <br>The parameters are:
 * <ul>
 * <li>dbURL
 * <li>dbDriver
 * <li>dbUser
 * <li>dbPassword
 * <li>dbConnections
 * <li>dbMaxConnections
 * <li>connectionTimeout
 * </ul>
 * dbURL, here you specifie the URL to the database to witch the connection will be made.
 * <br>dbDrvier, specifie the database driver to use when connection to the database.
 * <br>dbUser, specifie the user to use when logging into the database.
 * <br>dbPassword, specifie the password to use with the spcified user.
 * <br>dbConnections, specifie the nr of connections to open.
 * <br>dbMaxConnections, specifie the maximum nr of connections that can be made to the database.
 * <br>connectionTimeout, specifie the timeout for a connection. After the timeout the connection is closed and removed from the pool and a new connection is created.
 *
 * <p>You can also include the parameters to one of the constructors if you dont want to specifie them in the web.xml file.
 * <p>You can also specifie a prefix to the context parametrs.
 *
 * @author 	Viktor Gars
 * @version 1.0
 */
public class DBConnect
{
	private String dbUrl=null;
	private String dbDriver=null;
	private String dbUser=null;
	private String dbPassword=null;
	private int dbConnections=3;
	private int dbMaxConnections=3;
	private int connectionTimeout=300;
	private String configPrefix=null;

	/**
   * Default constructor.
   */
	public DBConnect()
	{
		configPrefix="";
	}

	/**
	 * Class constructor that takes config file prefix
	 *
	 * @param configPrefix  The start of the database context parameter specified in web.xml.
	 */
	public DBConnect(String configPrefix)
	{
		if(configPrefix==null)
			configPrefix="";
		this.configPrefix=configPrefix;
	}

	/**
	 * Class constructor that takes database information as parameters
	 *
	 * @param dbUrl  The url to the database that will be used.
	 * @param dbDriver  Database driver for the database.
	 * @param dbUser  Databas user.
	 * @param dbPassword  Database user password
	 * @param dbConnections  The nr of connections that will be created to the database.
	 */
	public DBConnect(String dbUrl, String dbDriver, String dbUser, String dbPassword, int dbConnections)
	{
		this.dbUrl=dbUrl;
		this.dbDriver=dbDriver;
		this.dbUser=dbUser;
		this.dbPassword=dbPassword;
		this.dbConnections=dbConnections;
	}

	/**
	 * Read database connection information from the current context's web.xml file.
	 *
	 * @param context The current context
	 */
	private void initData(ServletContext context)
	{
		dbUrl=context.getInitParameter(configPrefix+"dbURL");
		dbDriver=context.getInitParameter(configPrefix+"dbDriver");
		dbUser=context.getInitParameter(configPrefix+"dbUser");
		dbPassword=context.getInitParameter(configPrefix+"dbPassword");
		if(context.getInitParameter(configPrefix+"dbConnections")!=null)
			dbConnections=Integer.parseInt(context.getInitParameter(configPrefix+"dbConnections"));
		if(context.getInitParameter(configPrefix+"dbMaxConnections")!=null)
			dbMaxConnections=Integer.parseInt(context.getInitParameter(configPrefix+"dbMaxConnections"));
		if(context.getInitParameter(configPrefix+"connectionTimeout")!=null)
			connectionTimeout=Integer.parseInt(context.getInitParameter(configPrefix+"connectionTimeout"));
	}

	/**
	 * Create a ConnectionPool and save the ConnectionPool into the current context.
	 */
	private boolean connect(ServletContext context, String attributeName)
	throws SQLException
	{
		synchronized(this)
		{
			try
			{
				Class.forName(dbDriver).newInstance();
				PoolManager pool=new PoolManager(dbUrl,dbDriver,dbUser, dbPassword,dbConnections,dbMaxConnections,connectionTimeout);
				context.setAttribute(attributeName, pool);
				return(true);
			}
			catch(Exception exc)
			{
				context.log("Error loading JDBC driver", exc);
				throw new SQLException("Error loading JDBC driver2.");
			}
		}
	}

	/**
	 * Get a new ConnectionPool and save it in the current context with a spcified name.
	 *
	 * @param configPrefix  The start of the database context parameter specified in web.xml.
	 * @param context The current context.
	 * @param attributeName The attribute name that the ConnectionPool will be saved under.
	 *
	 * @return ConnectionPool A new ConnectionPool
	 *
	 * @throws SQLException Thrown if there is a error with the database connection.
	 */
	public PoolManager getConnectionPool(String configPrefix, ServletContext context, String attributeName)
	throws SQLException
	{
		initData(context);
		PoolManager pool = (PoolManager)context.getAttribute(attributeName);
		if(pool==null)
		{
			if(connect(context,attributeName)==true)
			{
				pool = (PoolManager)context.getAttribute(attributeName);
			}
			else
			{
				throw new SQLException("Database Connection Faild.");
			}
		}
		return(pool);
	}


	/**
	 * Get a new ConnectionPool and save it in the current context with a spcified name.
	 *
	 * @param context The current context.
	 * @param attributeName The attribute name that the ConnectionPool will be saved under.
	 *
	 * @return ConnectionPool A new ConnectionPool
	 *
	 * @throws SQLException Thrown if there is a error with the database connection.
	 */
	public PoolManager getConnectionPool(ServletContext context, String attributeName)
	throws SQLException
	{
		initData(context);
		PoolManager pool = (PoolManager)context.getAttribute(attributeName);
		if(pool==null)
		{
			if(connect(context,attributeName)==true)
			{
				pool = (PoolManager)context.getAttribute(attributeName);
			}
			else
			{
				throw new SQLException("Database Connection Faild.");
			}
		}
		return(pool);
	}
}
