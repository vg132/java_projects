/**
 * Copyright (C) VG Software 2003-apr-07
 *  
 * Document History
 * 
 * Created: 2003-apr-07 10:29:33 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.List;
import java.util.Random;
/**
 * @author Viktor
 * 
 */
public class Castle extends Item implements IBuilding
{
	private static Random rand=new Random();
	private GameField gf=null;
	private int curImg=0;
	private Image[] img=new Image[2];
	private List rewardKeys=null, nededKeys=null;

	public Castle(GameField gf, List rewardKeys, List nededKeys)
	{
		super(rand.nextInt(640-32),rand.nextInt(480-32));
		this.gf=gf;
		loadImages();
		this.rewardKeys=rewardKeys;
		this.nededKeys=nededKeys;
	}
	
	public Castle(GameField gf, int x, int y, List rewardKeys, List nededKeys)
	{
		super(x,y);
		this.gf=gf;
		loadImages();
		this.rewardKeys=rewardKeys;
		this.nededKeys=nededKeys;
	}
	
	private void loadImages()
	{
		curImg=0;
		MediaTracker tracker = new MediaTracker(gf);
		img[0]=Toolkit.getDefaultToolkit().getImage("./images/castle1.gif");
		tracker.addImage(img[0],0);
		img[1]=Toolkit.getDefaultToolkit().getImage("./images/castle2.gif");
		tracker.addImage(img[1],1);
		try
		{
			tracker.waitForAll();
		}
		catch(InterruptedException ie)
		{
		}	
	}
	
	public List getReward()
	{
		return(rewardKeys);
	}
	
	public List getNededKeys()
	{
		return(nededKeys);
	}
	
	public boolean canEnter(List keys)
	{
		if(nededKeys==null)
			return(true);
		else if(keys==null)
			return(false);
		return(keys.containsAll(nededKeys));
	}
	

	/**
	 * @see com.vgsoftware.dsvquest.Item#paint(Graphics)
	 */
	public void paint(Graphics g)
	{
		g.drawImage(img[curImg],xPos,yPos,null);
	}
	
	
	public boolean isClosed()
	{
		if(curImg==0)
			return(false);
		else
			return(true);
	}
	
	public void close(boolean close)
	{
		if(close)
			curImg=1;
		else
			curImg=0;
	}
}
