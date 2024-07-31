package com.vgsoftware.web.model.gt4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gt4.PointData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class PointModel
{
	public static Map getPoints(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Map points=new HashMap();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, pos, points FROM gt4_points");
			rs=ps.executeQuery();
			while(rs.next())
				points.put(rs.getString("pos"),new PointData(rs.getInt("id"),rs.getInt("pos"),rs.getInt("points")));
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
		return(points);
	}
}
