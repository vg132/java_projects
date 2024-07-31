package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.CountryData;

public class CountryModel
{
	public static List<CountryData> getCountrys(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CountryData> countrys=new ArrayList<CountryData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name FROM country ORDER BY name");
			rs=ps.executeQuery();
			while(rs.next())
				countrys.add(new CountryData(rs.getInt("id"),rs.getString("name")));
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
		return(countrys);
	}
}
