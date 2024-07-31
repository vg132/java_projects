package com.vgsoftware.vaction.data;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceData
{
	private String driverClass=null;
	private String url=null;
	private String username=null;
	private String password=null;

	public DataSource getDataSource()
	{
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return(dataSource);
	}
	
	public String getDriverClass()
	{
		return driverClass;
	}
	public void setDriverClass(String driverClass)
	{
		this.driverClass=driverClass;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}

	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url=url;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
}
