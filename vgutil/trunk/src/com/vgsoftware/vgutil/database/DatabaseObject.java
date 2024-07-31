package com.vgsoftware.vgutil.database;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A base class for objects that map to database tables
 *
 * @author Mark Wutka
 * @author Viktor Gars
 * @version 1.1
 */
public abstract class DatabaseObject implements IDBObject
{
	// Abstract functions

	public abstract String getInsertStatement();
	public abstract void prepareInsertStatement(PreparedStatement s)
	throws SQLException;

	public abstract String getUpdateStatement();
	public abstract void prepareUpdateStatement(PreparedStatement s)
	throws SQLException;

	public abstract String getDeleteStatement();
	public abstract void prepareDeleteStatement(PreparedStatement s)
	throws SQLException;

	public abstract String getTableName();

	public abstract DatabaseObject createInstance(ResultSet results)
	throws SQLException;

	public abstract String getFieldList();

	//Implemented functions

	protected Map data=null;

	public Map getData()
	{
		return(data);
	}
	
	public void setData(Object key, Object data)
	{
		this.data.put(key,data);
	}
	
	public Object getData(Object key)
	{
		return(data.get(key));
	}

	public Object getFormattedData(Object key, String format)
	{
		return(data.get(key));	
	}

	public Object getFirst(Connection conn)
	throws SQLException
	{
		return(getFirst(conn,""));
	}

	public Object getFirst(Connection conn, String whereClause)
	throws SQLException
	{
		Statement stmnt = null;
		try
		{
			stmnt = conn.createStatement();
			String query = "SELECT "+getFieldList()+" FROM "+getTableName();
			if (whereClause != null)
				query+=" "+whereClause;
			ResultSet rs = stmnt.executeQuery(query);
			if(rs.next())
				return(createInstance(rs));
			else
				return(null);
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	/**
	 * Gets all the objects in a table
	 *
	 * @param Connection A database connection
	 *
	 * @return List A list with all the found elements
	 *
	 * @throws SQLException
	 */
	public List getAll(Connection conn)
	throws SQLException
	{
		return(getAll(conn, null, null));
	}

	/**
	 * Gets all the objects in a table matching a specific where clause
	 *
	 * @param Connection A database connection
	 * @param whereClause The where clause to use for this query
	 *
	 * @return List A list with all the found elements
	 *
	 * @throws SQLException
	 */
	public List getAll(Connection conn, String whereClause)
	throws SQLException
	{
		return getAll(conn, whereClause, null);
	}

	/**
	 * Gets all the objects in a table matching a specific where clause
	 * and allows you to specify additional tables that are used in the
	 * where clause.
	 *
	 * @param Connection A database connection
	 * @param whereClause The where clause to use for this query
	 * @param additinalTables Additinal tables for use with this query
	 *
	 * @return List A list with all the found elements
	 *
	 * @throws SQLException
	 */
	public List getAll(Connection conn, String whereClause, String additionalTables)
	throws SQLException
	{
		Statement stmnt = null;
		try
		{
			stmnt = conn.createStatement();
			List l = new ArrayList();

			// Build the query. The basic query is "select <fields> from table"
			String query = "SELECT "+getFieldList()+" FROM "+getTableName();

			// Add the additional tables if needed
			if(additionalTables != null)
				query = query + "," + additionalTables;

			// Add the where clause if needed
			if(whereClause != null)
				query+=" "+whereClause;

			// Perform the query
			ResultSet results=stmnt.executeQuery(query);

			// Create a list of the results
			while (results.next())
			{
				l.add(createInstance(results));
			}

			return(l);
		}
		finally
		{
			if (stmnt != null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	public List executeQuery(Connection conn, String select, String where)
	throws SQLException
	{
		Statement stmnt = null;
		try
		{
			stmnt = conn.createStatement();
			List list = new ArrayList();
			// Execute the query
			ResultSet results=null;
			if(where==null)
				results=stmnt.executeQuery(select);
			else
				results=stmnt.executeQuery(select+" "+where);
			// Create a list containing the results
			while(results.next())
			{
				list.add(createInstance(results));
			}
			return(list);
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	public List executeQuery(Connection conn)
	throws SQLException
	{
		return(executeQuery(conn,null));
	}

	/**
	 * Executes an arbitrary query string that should return all the
	 * fields in the table just like the other queries.
	 *
	 * @param conn A database connection
	 * @param where Extra where statement to be added at the end of the query
	 *
	 * @return List A list with all the found elements
	 *
	 * @throws SQLException
	 */
	public List executeQuery(Connection conn, String where)
	throws SQLException
	{
		Statement stmnt = null;
		try
		{
			stmnt = conn.createStatement();
			List list = new ArrayList();
			// Execute the query
			ResultSet results=null;
			String query=getQuery();
			if((query==null)||(query.equals("")))
				return(list);
			if(where==null)
				results=stmnt.executeQuery(getQuery());
			else
				results=stmnt.executeQuery(getQuery()+" "+where);
			// Create a list containing the results
			while(results.next())
				list.add(createInstance(results));
			return(list);
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	/**
	 * Inserts an object into the database
	 *
	 * @param conn A database connection
	 *
	 * @return int Nr of inserts made, 0=error, 1=ok
	 *
	 * @throws SQLException
	 */
	public int insert(Connection conn)
	throws SQLException
	{
		PreparedStatement stmnt = null;
		try
		{
			// Ask this object to create its own insert statement
			stmnt = conn.prepareStatement(getInsertStatement());
			// Populate the insert statement with the data values
			prepareInsertStatement(stmnt);
			// Perform the insert
			return(stmnt.executeUpdate());
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	/**
	 * Returns a list of fields in the table while specifying a specific
	 * table for the field names, aliasing the table.field names back to the
	 * original name. For example, you might want to query from table A and
	 * table B, each containing a field F. If you do "select F from A,B ..." you
	 * might get an error because F is ambiguous. You need to do
	 * "select A.F as F from A,B ...". This routine takes the original field
	 * list and creates a list of such aliases.
	 */
	public String getFieldList(String tableName)
	{
		// Create a tokenizer to parse through the original list
		StringTokenizer fieldList = new StringTokenizer(getFieldList(), ",");

		// Create the string buffer to hold the resulting list
		StringBuffer newList = new StringBuffer();
		boolean first = true;

		while (fieldList.hasMoreTokens())
		{
			String field = fieldList.nextToken();
			if (!first)
				newList.append(',');
			first = false;
			newList.append(tableName);
			newList.append('.');
			newList.append(field);
			newList.append(" AS ");
			newList.append(field);
		}
		return newList.toString();
	}

	/**
	 * Updates the database row containing this object
	 *
	 * @param conn A database connection
	 *
	 * @return int Nr of updated rows in the database
	 *
	 * @throws SQLException
	 */
	public int update(Connection conn)
	throws SQLException
	{
		PreparedStatement stmnt = null;
		try
		{
			stmnt = conn.prepareStatement(getUpdateStatement());
			prepareUpdateStatement(stmnt);
			return(stmnt.executeUpdate());
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	/**
	 * Deletes this object from the database
	 *
	 * @param conn A database connection
	 *
	 * @return int Nr of deleted items from the database
	 *
	 * @throws SQLException
	 */
	public int delete(Connection conn)
	throws SQLException
	{
		PreparedStatement stmnt = null;
		try
		{
			stmnt=conn.prepareStatement(getDeleteStatement());
			prepareDeleteStatement(stmnt);
			return(stmnt.executeUpdate());
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	/** Returns the sequence number used to insert this object. This method
		* is very database-dependent. This version is geared towards the
		* mySQL database.
		*
		* @param conn Database connection
		*
		* @return int The sequence number found
		*
		* @throws SQLException
		*/
	public int getSequenceNumber(Connection conn)
	throws SQLException
	{
		Statement stmnt = null;
		try
		{
			stmnt = conn.createStatement();
			ResultSet results = stmnt.executeQuery("SELECT last_insert_id()");
			if(results.next())
				return(results.getInt(1));
			else
				throw(new SQLException("Unable to generate sequence number"));
		}
		finally
		{
			if (stmnt!=null)
			{
				try
				{
					stmnt.close();
				}
				catch(Exception ignore)
				{
				}
			}
		}
	}

	public String getSequenceGenerator()
	{
		return(null);
	}

	public String getQuery()
	{
		return("");
	}

	public static Object getObject(ResultSet rs, int pos)
	throws SQLException
	{
		switch (rs.getMetaData().getColumnType(pos))
		{
			case Types.BIGINT:
				 return(new Long(rs.getLong(pos)));
			case Types.BINARY:
				 return(rs.getBytes(pos));
			case Types.BIT:
				 return(new Boolean(rs.getBoolean(pos)));
			case Types.CHAR:
				 return(rs.getString(pos));
			case Types.DATE:
				 return(rs.getDate(pos));
			case Types.DECIMAL:
				return(rs.getBigDecimal(pos));
			case Types.DOUBLE:
				 return(new Double(rs.getDouble(pos)));
			case Types.FLOAT:
				 return(new Double(rs.getDouble(pos)));
			case Types.INTEGER:
				 return(new Integer(rs.getInt(pos)));
			case Types.LONGVARBINARY:
				 return(rs.getBytes(pos));
			case Types.LONGVARCHAR:
				 return(rs.getString(pos));
			case Types.NULL:
				 return("null");
			case Types.NUMERIC:
				return(rs.getBigDecimal(pos));
			case Types.OTHER:
				 return(rs.getObject(pos));
			case Types.REAL:
				 return(new Float(rs.getFloat(pos)));
			case Types.SMALLINT:
				 return(new Short(rs.getShort(pos)));
			case Types.TIME:
				 return(rs.getTime(pos));
			case Types.TIMESTAMP:
				 return(rs.getTimestamp(pos));
			case Types.TINYINT:
				 return(new Byte(rs.getByte(pos)));
			case Types.VARBINARY:
				 return(rs.getBytes(pos));
			case Types.VARCHAR:
				 return(rs.getString(pos));
		}
		return(null);
	}
}