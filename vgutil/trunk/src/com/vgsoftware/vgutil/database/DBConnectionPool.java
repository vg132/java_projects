package com.vgsoftware.vgutil.database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A connection pool that creates a fixed pool of connections at
 * startup and waits for connections if they aren't available or
 * creates a new connection if the max nr of connections is not retched.
 *
 * @author Viktor Gars
 * @version 1.0
 */
public class DBConnectionPool
{
	private String dbUrl="";
	private String dbDriver="";
	private String dbUser="";
	private String dbPassword="";
	private int initConns=0;
	private int maxConns=0;
	private long cleanInterval=300000;
	private Cleaner cleaner=null;

	private List freeConnections = new LinkedList();
	private HashMap usedConnections = new HashMap();

	/**
	 * Creates a new connection pool that creates connections using
	 * the specified URL, user name and password
	 *
	 * @param dbUrl  The url to the database that will be used.
	 * @param dbDriver  Database driver for the database.
	 * @param dbUser  Databas user.
	 * @param dbPassword  Database user password
	 * @param initConns  The nr of connections that will be created to the database at startup.
	 * @param maxConns The max connections that will be created to the database.
	 * @param cleanInterval The nr of milliseconds the connection pool waits for a connection to be released.
	 *
	 * @throws SQLException
	 *
	 */
	public DBConnectionPool(String dbUrl, String dbDriver, String dbUser, String dbPassword, int initConns, int maxConns, long cleanInterval)
	throws SQLException, ClassNotFoundException
	{
		this.dbUrl = dbUrl;
		this.dbDriver=dbDriver;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.initConns=initConns;
		this.maxConns=maxConns;
		this.cleanInterval=cleanInterval;
		Class.forName(dbDriver);
		for(int i=0;i<initConns;i++)
		{
			freeConnections.add(newConnection());
		}
		cleaner=new Cleaner(cleanInterval,this);
		cleaner.start();
	}

	/**
	 * Returns a connection to the calling function.
	 *
	 * @return Connection
	 */
	public synchronized Connection getConnection()
	throws SQLException
	{
		return(getConnection(1));
	}

	/**
	 * Close all database connections.
	 * 
	 * @throws SQLException
	 *
	 */
	public synchronized void shutdown()
	throws SQLException
	{
		cleaner.interrupt();
		release();
	}

	/**
	 * Returns a connection to the calling function. If ther is no free connactions the function will wait for timeout and try again to get a connection.
	 *
	 * @param timeout The nr of milliseconds to wait for a free connection if there is no free connections. If timeout is zero wait until there is a free connection.
	 *
	 * @return Connaction
	 * 
	 * @throws SQLException
	 */
	public synchronized Connection getConnection(long timeout)
	throws SQLException
	{
		long startTime = System.currentTimeMillis();
		long remaining = timeout;
		Connection conn = null;
		while((conn=getPooledConnection())==null)
		{
			try
			{
				wait(remaining);
			}
			catch(InterruptedException e)
			{
			}
			remaining = timeout - (System.currentTimeMillis() - startTime);
			if(remaining<=0)
			{
				throw(new SQLException("getConnection(long timeout) timed-out"));
			}
		}
		if(!isConnectionOK(conn))
		{
			clean();
			return(getConnection(remaining));
		}
		return(conn);
	}

	/**
	 * Test if a connection is still working.
	 *
	 * @param conn The connection that will be tested for functionality.
	 *
	 * @return boolean
	 */
	private boolean isConnectionOK(Connection conn)
	{
		try
		{
			if(conn.getAutoCommit()==true)
			{
				conn.setAutoCommit(false);
				conn.commit();
				conn.setAutoCommit(true);
			}
			else
			{
				conn.commit();
			}
		}
		catch(SQLException e)
		{
			return(false);
		}
		return(true);
	}


	/**
	 * Check if there are any free connections, if there are get the first one
	 * and set it as used, also return the connection to the calling function.
	 * If there are no more free connectons and we have not retched maxConns create a new connections and return it.
	 */
	private synchronized Connection getPooledConnection()
	throws SQLException
	{
		Connection conn=null;
		if(freeConnections.size()>0)
		{
			conn=(Connection)freeConnections.remove(0);
			usedConnections.put(conn,new ConnectionInfo(false));
		}
		else if(maxConns==0||usedConnections.size()<maxConns)
		{
			conn=newConnection();
			usedConnections.put(conn,new ConnectionInfo(true));
		}
		return(conn);
	}

	/**
	 * Create and return a new connection
	 *
	 * @return Connection
	 *
	 * @throws SQLException
	 */
	private synchronized Connection newConnection()
	throws SQLException
	{
		return(DriverManager.getConnection(dbUrl,dbUser, dbPassword));
	}

	/**
	 * Return a connection to the pool
	 *
	 * @param conn The connection that is returned to the pool
	 */
	public synchronized void freeConnection(Connection conn)
	{
		ConnectionInfo ci=(ConnectionInfo)usedConnections.remove(conn);
		if(ci!=null&&ci.getExtra()==false)
			freeConnections.add(conn);
		notifyAll();
	}

	/**
	 * Close all connections, even if thay are in use!
	 */
	public synchronized void release()
	{
		Connection conn;
		while(!freeConnections.isEmpty())
		{
			try
			{
				((Connection)freeConnections.remove(0)).close();
			}
			catch(SQLException sql)
			{
			}
		}

		Iterator iter=usedConnections.keySet().iterator(); 
		while(iter.hasNext())
		{
			try
			{
				conn=(Connection)iter.next();
				iter.remove();
				conn.close();
			}
			catch(SQLException sql)
			{
			}
		}
	}

	/**
	 * Returns information about connection pool.
	 *
	 * @return String
	 */
	public String toString()
	{
		return("Total connections: " + (freeConnections.size() + usedConnections.size()) + " Available: " + freeConnections.size() + " Checked-out: " + usedConnections.size());
	}

	/**
	 * Check if there are any connections that have been open to long.
	 * Set the time via the cleanInterval parameter in ConnectionPool().
	 */
	public void clean()
	{
		long curTime=System.currentTimeMillis();
		Connection conn=null;
		ConnectionInfo ci=null;
		Iterator iter=usedConnections.keySet().iterator();
		
		while(iter.hasNext())
		{
			conn=(Connection)iter.next(); 
			ci=(ConnectionInfo)usedConnections.get(conn);
			if((ci.getTime()+cleanInterval)<curTime)
			{
				try
				{
					iter.remove();
					if(ci.getExtra()==false)
						freeConnections.add(newConnection());
					conn.close();
				}
				catch(SQLException sql)
				{
				}
			}
		}
		int i=0;
		while(i<freeConnections.size())
		{
			try
			{
				conn=(Connection)freeConnections.get(i);
				if(!isConnectionOK(conn))
				{
					freeConnections.remove(i);
					freeConnections.add(newConnection());
					conn.close();
				}
			}
			catch(SQLException sql)
			{
				sql.printStackTrace(System.err);
			}
			finally
			{
				i++;
			}
		}
	}
}

class Cleaner extends Thread
{
	private long sleep=0;
	private DBConnectionPool cp=null;

	public Cleaner(long sleep, DBConnectionPool cp)
	{
		this.sleep=sleep;
		this.cp=cp;
	}

	public void run()
	{
		try
		{
			while(true)
			{
				sleep(sleep);
				cp.clean();
			}
		}
		catch(InterruptedException e)
		{
		}
	}

	public long getSleep()
	{
		return(sleep);
	}

	public void setSleep(long sleep)
	{
		this.sleep=sleep;
	}
}

class ConnectionInfo
{
	private long time=0;
	private boolean extra=false;

	public ConnectionInfo(boolean extra)
	{
		this.extra=extra;
		time=System.currentTimeMillis();
	}

	public long getTime()
	{
		return(time);
	}

	public boolean getExtra()
	{
		return(extra);
	}
}