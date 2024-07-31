/**
 * Copyright (C) VG Software 2003-mar-30
 *  
 * Document History
 * 
 * Created: 2003-mar-30 18:34:44 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.Graphics;

/**
 * Abstract base class for use with objects in DSVQuest.
 * 
 * @author Viktor
 * 
 */
public abstract class Item
{
	protected int xPos=0, yPos=0;
	
	/**
	 * Test if this object is on the same position on the screen as 'item'.
	 * 
	 * @param item The item to compare position with.
	 * 
	 * @return True if any part of 'item' is on the same position as any part of this object.
	 */
	public boolean hitTest(Item item)
	{
		if((item.getXPos()>getXPos()+32)||
			(getXPos()>item.getXPos()+32)||
			(item.getYPos()>getYPos()+32)||
			(getYPos()>item.getYPos()+32))
		{
			return(false);
		}
		else
		{
			return(true);
		}
	}
	
	/**
	 * Paint this object on the component.
	 * 
	 * @param g The graphics context to paint on.
	 * */
	public abstract void paint(Graphics g);
	
	public Item(int x, int y)
	{
		xPos=x;
		yPos=y;
	}
	
	/**
	 * Sets the x-position
	 * 
	 * @param x x-Position.
	 */
	public void setXPos(int x)
	{
		xPos=x;
	}
	
	/**
	 * Gets the x-position
	 * 
	 * @return int x-Position
	 */
	public int getXPos()
	{
		return(xPos);
	}
	
	/**
	 * Sets the y-position.
	 * 
	 * @param y y-Position.
	 */
	public void setYPos(int y)
	{
		yPos=y;
	}
	
	/**
	 * Gets the y-position.
	 * 
	 * @return int y-Position
	 */
	public int getYPos()
	{
		return(yPos);
	}
}
