/**
 * Copyright (C) VG Software 2003-mar-30
 *  
 * Document History
 * 
 * Created: 2003-mar-30 23:39:21 by Viktor
 * 
 */

package com.vgsoftware.dsvquest;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Viktor
 * 
 */
public class Building extends Item implements IBuilding
{
	private static Random rand=new Random();
	private Image[] img=new Image[2];
	private int curImg=0;
	private GameField gf=null;
	private List rewardKeys=null, nededKeys=null;
	
	public Building(GameField gf, List rewardKeys, List nededKeys)
	{
		super(rand.nextInt(640-32),rand.nextInt(480-32));
		this.gf=gf;
		loadImages();
		this.rewardKeys=rewardKeys;
		this.nededKeys=nededKeys;
	}

	public Building(GameField gf, Key rewardKey, Key nededKey)
	{
		super(rand.nextInt(640-32),rand.nextInt(480-32));
		this.gf=gf;
		loadImages();
		if(rewardKey!=null)
		{
			this.rewardKeys=new ArrayList();
			rewardKeys.add(rewardKey);
		}
		if(nededKey!=null)
		{
			this.nededKeys=new ArrayList();
			nededKeys.add(nededKey);
		}
	}
	
	public Building(GameField gf,int x, int y, List rewardKeys, List nededKeys)
	{
		super(x,y);
		this.gf=gf;
		loadImages();
		this.rewardKeys=rewardKeys;
		this.nededKeys=nededKeys;
	}
	
	public Building(GameField gf,int x, int y, Key rewardKey, Key nededKey)
	{
		super(x,y);
		this.gf=gf;
		loadImages();
		if(rewardKey!=null)
		{
			this.rewardKeys=new ArrayList();
			rewardKeys.add(rewardKey);
		}
		if(nededKey!=null)
		{
			this.nededKeys=new ArrayList();
			nededKeys.add(nededKey);
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
	
	/**
	 * Load all graphics used for painting the building on screen.
	 * 
	 */
	private void loadImages()
	{
		curImg=0;
		MediaTracker tracker = new MediaTracker(gf);
		img[0]=Toolkit.getDefaultToolkit().getImage("./images/building1.gif");
		tracker.addImage(img[0],0);
		img[1]=Toolkit.getDefaultToolkit().getImage("./images/building2.gif");
		tracker.addImage(img[1],1);
		try
		{
			tracker.waitForAll();
		}
		catch(InterruptedException ie)
		{
		}
	}

	/**
	 * Draw this building on the graphic context provided.
	 * 
	 * @param g The graphic context used to draw on.
	 * 
	 */
	public void paint(Graphics g)
	{
		g.drawImage(img[curImg],xPos,yPos,null);
	}
}
