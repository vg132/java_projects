package com.vgsoftware.web.data.gt4;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class PointData
{
	private int id=-1;
	private int pos=-1;
	private int points=-1;
	
	public PointData()
	{
	}
	
	public PointData(int id, int pos, int points)
	{
		this.id=id;
		this.pos=pos;
		this.points=points;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return Returns the points.
	 */
	public int getPoints()
	{
		return points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}
	/**
	 * @return Returns the pos.
	 */
	public int getPos()
	{
		return pos;
	}
	/**
	 * @param pos The pos to set.
	 */
	public void setPos(int pos)
	{
		this.pos = pos;
	}
}
