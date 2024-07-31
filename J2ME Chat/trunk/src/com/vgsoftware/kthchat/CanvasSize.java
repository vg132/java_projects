/*
 * Created on 2004-dec-03 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * This is just a dummy class to get the height and width of
 * the screen.
 * 
 * @author Viktor
 * @version 1.0 
 */
public class CanvasSize extends Canvas
{
	private int width=0;
	private int height=0;

	/**
	 * Gets the height of this screen.
	 * 
	 * @return the height of this screen.
	 */
	public int getHeight()
	{
		return (height);
	}

	/**
	 * Gets the width of this screen.
	 * 
	 * @return the width of this screen.
	 */
	public int getWidth()
	{
		return (width);
	}

	/**
	 * Inits the widht and height of this screen.
	 * 
	 * @param g The graphics object for this screen.
	 */
	public void paint(Graphics g)
	{
		width=super.getWidth();
		height=super.getHeight();
	}
}
