package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.vgutil.misc.MD5;

public class CustomerModel
{
	public static boolean updateCustomer(DataSource dataSource, CustomerData cd)
	throws SQLException 
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE customer SET name=?, address=?, city=?, state=?, post_code=?, country_id=?, currency=? WHERE id=?");
			ps.setString(1,cd.getName());
			ps.setString(2,cd.getAddress());
			ps.setString(3,cd.getCity());
			ps.setString(4,cd.getState());
			ps.setString(5,cd.getPostCode());
			ps.setInt(6,cd.getCountryId());
			ps.setString(7,cd.getCurrency());
			ps.setInt(8,cd.getId());
			if(ps.executeUpdate()==1)
				return(true);
		}
		finally
		{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(false);
	}
	
	public static boolean addCustomer(DataSource dataSource, String idTable, CustomerData cd)
	throws SQLException 
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			cd.setId(DBCounter.getId(dataSource,idTable));
			if(cd.getId()!=-1)
			{
				
				conn=dataSource.getConnection();
				ps=conn.prepareStatement("INSERT INTO customer(id,email,password,name,address,city,state,post_code,country_id,customer_type,currency) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1,cd.getId());
				ps.setString(2,cd.getEmail());
				ps.setString(3,MD5.parse(cd.getPassword()));
				ps.setString(4,cd.getName());
				ps.setString(5,cd.getAddress());
				ps.setString(6,cd.getCity());
				ps.setString(7,cd.getState());
				ps.setString(8,cd.getPostCode());
				ps.setInt(9,cd.getCountryId());
				ps.setInt(10,cd.getCustomerType());
				ps.setString(11,cd.getCurrency());
				if(ps.executeUpdate()==1)
					return(true);
			}
		}
		finally
		{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(false);
	}
	
	public static String getCookiePassword(DataSource dataSource, int customerId)
	throws SQLException 
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CustomerData cd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT email, password FROM customer WHERE id=?");
			ps.setInt(1,customerId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return(MD5.parse(rs.getString("email")+rs.getString("password")));
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
		return(null);
	}
	
	public static CustomerData getCookieCustomer(DataSource dataSource, String email, String cookiePassword)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CustomerData cd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.email, c.password, c.name, c.address, c.city, c.state, c.post_code, c.country_id, country.name AS country, c.customer_type, c.currency FROM customer c, country WHERE c.country_id=country.id AND c.email=?");
			ps.setString(1,email);
			rs=ps.executeQuery();
			if(rs.next())
			{
				if(MD5.parse(rs.getString("email")+rs.getString("password")).equals(cookiePassword))
				{
					cd=new CustomerData();
					cd.setId(rs.getInt("id"));
					cd.setEmail(rs.getString("email"));
					cd.setName(rs.getString("name"));
					cd.setAddress(rs.getString("address"));
					cd.setCity(rs.getString("city"));
					cd.setState(rs.getString("state"));
					cd.setPostCode(rs.getString("post_code"));
					cd.setCountryId(rs.getInt("country_id"));
					cd.setCountry(rs.getString("country"));
					cd.setCustomerType(rs.getInt("customer_type"));
					cd.setCurrency(rs.getString("currency"));
				}
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
		return(cd);
	}
	
	public static CustomerData getCustomer(DataSource dataSource, String email, String password)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CustomerData cd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.email, c.name, c.address, c.city, c.state, c.post_code, c.country_id, country.name AS country, c.customer_type, c.currency FROM customer c, country WHERE c.country_id=country.id AND c.email=? AND c.password=?");
			ps.setString(1,email);
			ps.setString(2,MD5.parse(password));
			rs=ps.executeQuery();
			if(rs.next())
			{
				cd=new CustomerData();
				cd.setId(rs.getInt("id"));
				cd.setEmail(rs.getString("email"));
				cd.setName(rs.getString("name"));
				cd.setAddress(rs.getString("address"));
				cd.setCity(rs.getString("city"));
				cd.setState(rs.getString("state"));
				cd.setPostCode(rs.getString("post_code"));
				cd.setCountryId(rs.getInt("country_id"));
				cd.setCountry(rs.getString("country"));
				cd.setCustomerType(rs.getInt("customer_type"));
				cd.setCurrency(rs.getString("currency"));
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
		return(cd);
	}
	
	public static CustomerData getCustomer(DataSource dataSource, int customerId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CustomerData cd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.email, c.name, c.address, c.city, c.state, c.post_code, c.country_id, country.name AS country, c.customer_type, c.currency FROM customer c, country WHERE c.country_id=country.id AND c.id=?");
			ps.setInt(1,customerId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				cd=new CustomerData();
				cd.setId(rs.getInt("id"));
				cd.setEmail(rs.getString("email"));
				cd.setName(rs.getString("name"));
				cd.setAddress(rs.getString("address"));
				cd.setCity(rs.getString("city"));
				cd.setState(rs.getString("state"));
				cd.setPostCode(rs.getString("post_code"));
				cd.setCountryId(rs.getInt("country_id"));
				cd.setCountry(rs.getString("country"));
				cd.setCustomerType(rs.getInt("customer_type"));
				cd.setCurrency(rs.getString("currency"));
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
		return(cd);
	}
}
