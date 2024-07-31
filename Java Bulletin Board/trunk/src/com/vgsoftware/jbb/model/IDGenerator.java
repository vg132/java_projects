package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class IDGenerator
{
	/**
	 * Gets the next id for a table.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param tableName The name of the table
	 * 
	 * @return The next id or -1 if somthing went wrong.
	 */
	public static synchronized int getNextId(DataSource dataSource, String tableName)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id FROM jbb_id_table WHERE table_name=?");
			ps.setString(1,tableName);
			rs=ps.executeQuery();
			if(rs.next())
			{
				id=rs.getInt("id");
				ps=conn.prepareStatement("UPDATE jbb_id_table SET id=? WHERE table_name=?");
			}
			else
			{
				id=1;
				ps=conn.prepareStatement("INSERT INTO jbb_id_table(id,table_name) VALUES(?,?)");
			}
			ps.setInt(1,id+1);
			ps.setString(2,tableName);
			if(ps.executeUpdate()==1)
				return(id);
		}
		finally
		{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(-1);
	}
}
