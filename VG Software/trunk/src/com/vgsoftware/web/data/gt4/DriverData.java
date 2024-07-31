package com.vgsoftware.web.data.gt4;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class DriverData
{
	private int id=-1;
	private String name=null;

	public DriverData()
	{
	}
	
	public DriverData(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return(id);
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return(name);
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}
}
