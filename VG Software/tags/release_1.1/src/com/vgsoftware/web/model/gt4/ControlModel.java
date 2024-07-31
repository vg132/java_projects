package com.vgsoftware.web.model.gt4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gt4.ControlData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class ControlModel
{
	public static List getControllers(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ControlData cd=null;
		List controllers=new ArrayList();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name FROM gt4_control ORDER BY name");
			rs=ps.executeQuery();
			while(rs.next())
			{
				cd=new ControlData();
				cd.setId(rs.getInt("id"));
				cd.setName(rs.getString("name"));
				controllers.add(cd);
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
		return(controllers);
	}
}
