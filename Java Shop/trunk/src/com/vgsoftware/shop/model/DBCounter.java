package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DBCounter
{
	public static synchronized int getId(DataSource dataSource, String idTable)
	throws SQLException
	{
		return(getId(dataSource,idTable,1));
	}

	public static synchronized int getId(DataSource dataSource, String idTable, int quantity)
	throws SQLException
	{
		PreparedStatement ps=null;
		Connection conn=null;
		ResultSet rs=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id FROM "+idTable+"_seq");
			rs=ps.executeQuery();
			if(rs.next())
			{
				id=rs.getInt("id");
				ps=conn.prepareStatement("UPDATE "+idTable+"_seq SET id=?");
				ps.setInt(1,id+quantity);
				if(ps.executeUpdate()==1)
					return(id+1);
			}
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
