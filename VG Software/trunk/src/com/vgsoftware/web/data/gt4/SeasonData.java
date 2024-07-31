package com.vgsoftware.web.data.gt4;

/**
 * Data object for season
 * 
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class SeasonData
{
	private int id=-1;
	private int pointRounds=-1;
	private String name=null;

	public SeasonData()
	{
	}
	
	public SeasonData(int id, String name, int pointRounds)
	{
		this.id=id;
		this.name=name;
		this.pointRounds=pointRounds;
	}
	
	/**
	 * Gets the ID for the season.
	 * 
	 * @return Returns the id.
	 */
	public int getId()
	{
		return(id);
	}
	/**
	 * Sets the ID for the season.
	 * 
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * Gets the name of the season.
	 * 
	 * @return Returns the name.
	 */
	public String getName()
	{
		return(name);
	}
	/**
	 * Sets the name of the season.
	 * 
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the pointRounds.
	 */
	public int getPointRounds()
	{
		return pointRounds;
	}

	/**
	 * @param pointRounds The pointRounds to set.
	 */
	public void setPointRounds(int pointRounds)
	{
		this.pointRounds=pointRounds;
	}
}
