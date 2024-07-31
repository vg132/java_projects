/**
 * Copyright (C) VG Software 2003-mar-30
 *  
 * Document History
 * 
 * Created: 2003-mar-30 18:32:44 by Viktor
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
public class Player extends Item
{
	public static final int MOVE_LEFT=0;
	public static final int MOVE_RIGHT=1;
	public static final int MOVE_UP=2;
	public static final int MOVE_DOWN=3;
	
	private static Random rand=new Random();
	private int points=0;
	private Image[] img=new Image[8];
	private int curImg=0;
	private GameField gf=null;
	private int direction=0;
	private List keys=new ArrayList();
	private int lives=0;
	
	
	public Player(GameField gf,int lives)
	{
		super(rand.nextInt(640-32),rand.nextInt(480-32));
		this.gf=gf;
		loadImages();
		this.lives=lives;
	}
	
	public Player(GameField gf, int x, int y, int direction, int lives)
	{
		super(x,y);
		this.gf=gf;
		loadImages();
		this.direction=direction;
		this.lives=lives;
	}
	
	public void addKey(Key key)
	{
		if(key!=null)
			keys.add(key);
	}
	
	public void addKey(List keys)
	{
		if(keys!=null)
			this.keys.addAll(keys);
	}
	
	/**
	 * Load all graphics used for painting the player on screen.
	 * 
	 */
	private void loadImages()
	{
		MediaTracker tracker = new MediaTracker(gf);
		img[0]=Toolkit.getDefaultToolkit().getImage("./images/fig_1-1.gif");
		tracker.addImage(img[0],0);
		img[1]=Toolkit.getDefaultToolkit().getImage("./images/fig_1-2.gif");
		tracker.addImage(img[1],1);
		img[2]=Toolkit.getDefaultToolkit().getImage("./images/fig_2-1.gif");
		tracker.addImage(img[2],2);
		img[3]=Toolkit.getDefaultToolkit().getImage("./images/fig_2-2.gif");
		tracker.addImage(img[3],3);
		img[4]=Toolkit.getDefaultToolkit().getImage("./images/fig_3-1.gif");
		tracker.addImage(img[4],4);
		img[5]=Toolkit.getDefaultToolkit().getImage("./images/fig_3-2.gif");
		tracker.addImage(img[5],5);
		img[6]=Toolkit.getDefaultToolkit().getImage("./images/fig_4-1.gif");
		tracker.addImage(img[6],6);
		img[7]=Toolkit.getDefaultToolkit().getImage("./images/fig_4-2.gif");
		tracker.addImage(img[7],7);
		try
		{
			tracker.waitForAll();
		}
		catch(InterruptedException ie)
		{
		}
	}
	
	public void move(int direction)
	{
		this.direction=direction;
		if(direction==MOVE_UP)
			yPos-=2;
		else if(direction==MOVE_DOWN)
			yPos+=2;
		else if(direction==MOVE_LEFT)
			xPos-=2;
		else if(direction==MOVE_RIGHT)
			xPos+=2;
	}

	public int getLives()
	{
		return(lives);
	}
	
	public void setLives(int lives)
	{
		this.lives=lives;
	}
	
	public List getKeys()
	{
		return(keys);
	}
	
	public void setPoints(int points)
	{
		this.points=points;
	}
	
	public int getPoints()
	{
		return(points);
	}
	
	/**
	 * @see com.vgsoftware.dsvquest.Item#paint(Graphics)
	 */
	public void paint(Graphics g)
	{
		int tmpCur=0;
		if(curImg>=5)
			tmpCur=1;
		switch(direction)
		{
			case MOVE_UP:
				g.drawImage(img[2+tmpCur], xPos,yPos,null);
				break;
			case MOVE_DOWN:
				g.drawImage(img[6+tmpCur], xPos,yPos,null);
				break;
			case MOVE_LEFT:
				g.drawImage(img[0+tmpCur], xPos,yPos,null);
				break;
			case MOVE_RIGHT:
				g.drawImage(img[4+tmpCur], xPos,yPos,null);
				break;
		}
		curImg++;
		if(curImg>=10)
			curImg=0;
	}
}
