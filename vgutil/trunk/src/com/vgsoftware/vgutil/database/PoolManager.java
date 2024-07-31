package com.vgsoftware.vgutil.database;

import java.sql.SQLException;
import java.sql.Connection;

/**
 * A connection pool that creates a fixed pool of connections at
 * startup and waits for connections if they aren't available or
 * creates a new connection if the max nr of connections is not retched.
 *
 * @author 	Viktor Gars
 * @version 1.0
 */
public class PoolManager implements IConnectionPool
{
	private DBConnectionPool dbPool=null;

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
	 * @param timeOut The nr of seconds the connection pool waits for a connection to be released.
	 *
	 * @throws SQLException
	 *
	 */
	public PoolManager(String dbUrl, String dbDriver, String dbUser, String dbPassword, int initConns, int maxConns, long timeOut)
	throws SQLException, ClassNotFoundException
	{
		dbPool=new DBConnectionPool(dbUrl,dbDriver,dbUser,dbPassword,initConns,maxConns,timeOut);
	}

	public synchronized Connection getConnection()
	throws SQLException
	{
		return(dbPool.getConnection());
	}

	public synchronized Connection getConnection(long timeout)
	throws SQLException
	{
		return(dbPool.getConnection(timeout));
	}

	public synchronized void releaseConnection(Connection conn)
	throws SQLException
	{
		dbPool.freeConnection(conn);
	}

	public synchronized void shutdown()
	throws SQLException
	{
		dbPool.shutdown();
	}
}