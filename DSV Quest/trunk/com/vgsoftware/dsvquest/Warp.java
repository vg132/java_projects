/**
 * Copyright (C) VG Software 2003-apr-08
 *  
 * Document History
 * 
 * Created: 2003-apr-08 00:55:01 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Toolkit;
import java.util.Random;
/**
 * @author Viktor
 * 
 */
public class Warp extends Item
{
	private static Random rand=new Random();
	private Image img=null;
	private int curImg=0;
	
	public Warp()
	{
		super(rand.nextInt(640-32),rand.nextInt(480-32));
		loadImages();
	}
	
	public Warp(int x, int y)
	{
		super(x,y);
		loadImages();
	}
	
	private void loadImages()
	{
		img=Toolkit.getDefaultToolkit().getImage("./images/warp.gif");
	}
	/**
	 * @see com.vgsoftware.dsvquest.Item#paint(Graphics)
	 */
	public void paint(Graphics g)
	{
		g.drawImage(img,xPos,yPos,null);
	}
}
