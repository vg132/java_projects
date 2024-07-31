package com.vgsoftware.jbb.data;

import java.util.List;


/**
 * Holds information about a category. Mapps agains the <code>categorys</code> table in the database.
 */
public class CategoryData
{
	private int id=-1;
	private String name=null;
	private List<ForumData> forums=null;

	public CategoryData()
	{
	}

	public CategoryData(int id, String name)
	{
		this.id=id;
		this.name=name;
	}

	/**
	 * Gets the id.
	 * @return Returns the id.
	 */
	public int getId()
	{
		return(id);
	}

	/**
	 * Sets the id.
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}

	/**
	 * Gets the name.
	 * @return Returns the name.
	 */
	public String getName()
	{
		return(name);
	}

	/**
	 * Sets the name.
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}

	/**
	 * Gets the forums.
	 * @return Returns the forums.
	 */
	public List<ForumData> getForums()
	{
		return (forums);
	}

	/**
	 * Sets the forums.
	 * @param forums The forums to set.
	 */
	public void setForums(List<ForumData> forums)
	{
		this.forums=forums;
	}

}
