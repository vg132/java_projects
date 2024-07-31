/**
 * Copyright (C) VG Software 2003-mar-30
 *  
 * Document History
 * 
 * Created: 2003-mar-30 18:31:42 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.vgsoftware.questions.qtype.*;

/**
 * @author Viktor
 * 
 */
public class GameField extends JPanel
{
	public final static int STATUS_PAUSE=0;
	public final static int STATUS_RUN=1;
	public final static int STATUS_QUESTION=2;
	public final static int STATUS_GAME_OVER=3;
	public final static int STATUS_INIT=4;

	private Player player=null;

	private List buildings=new ArrayList();

	private int curLevel=0;
	private int status=4;
	private boolean hit=false;
	private QuestionPool qp=null;
	private JFrame owner=null;

	public void setStatus(int status)
	{
		this.status =status;
	}

	public Player getPlayer()
	{
		return(player);
	}
	
	public int getLevel()
	{
		return(curLevel);
	}

	public int getStatus()
	{
		return(status);
	}

	private void initLevel(int level)
	{
		List keys=null;
		switch(level)
		{
			case 1:
				player=new Player(this,0,279,1,3);
				buildings=new ArrayList();
				keys=new ArrayList();
				keys.add(new Key("Red"));
				keys.add(new Key("Blue"));
				keys.add(new Key("Green"));
				buildings.add(new Building(this,150,408, (Key)keys.get(0),null));
				buildings.add(new Building(this,342,419,(Key)keys.get(1),null));
				buildings.add(new Building(this,233,10,(Key)keys.get(2),null));
				buildings.add(new Castle(this,320,240,null,keys));
				break;
			case 2:
				player=new Player(this,0,279,1,3);
				buildings=new ArrayList();
				keys=new ArrayList();
				keys.add(new Key("Red"));
				keys.add(new Key("Blue"));
				keys.add(new Key("Green"));
				keys.add(new Key("Yellow"));
				for(int i=0;i<2+level;i++)
					buildings.add(new Building(this,(Key)keys.get(i),null));
				buildings.add(new Castle(this,null,keys));
				break;
			case 3:
				player=new Player(this,0,279,1,3);
				buildings=new ArrayList();
				keys=new ArrayList();
				keys.add(new Key("Red"));
				keys.add(new Key("Blue"));
				keys.add(new Key("Green"));
				keys.add(new Key("Yellow"));
				keys.add(new Key("Black"));
				for(int i=0;i<2+level;i++)
					buildings.add(new Building(this,(Key)keys.get(i),null));
				buildings.add(new Castle(this,null,keys));
				break;
			case 4:
				player=new Player(this,0,279,1,3);
				buildings=new ArrayList();
				keys=new ArrayList();
				keys.add(new Key("Red"));
				keys.add(new Key("Blue"));
				keys.add(new Key("Green"));
				keys.add(new Key("Yellow"));
				keys.add(new Key("Black"));
				keys.add(new Key("Silver"));
				for(int i=0;i<2+level;i++)
					buildings.add(new Building(this,(Key)keys.get(i),null));
				buildings.add(new Castle(this,null,keys));
				break;
			case 5:
				player=new Player(this,0,279,1,3);
				buildings=new ArrayList();
				keys=new ArrayList();
				keys.add(new Key("Red"));
				keys.add(new Key("Blue"));
				keys.add(new Key("Green"));
				keys.add(new Key("Yellow"));
				keys.add(new Key("Black"));
				keys.add(new Key("Silver"));
				keys.add(new Key("Gold"));				
				for(int i=0;i<2+level;i++)
					buildings.add(new Building(this,(Key)keys.get(i),null));
				buildings.add(new Castle(this,null,keys));
				break;
		};
	}

	public GameField(JFrame owner, int level)
	{
		qp=new QuestionPool("./questions/qpool.txt");
		curLevel=level;
		initLevel(curLevel);
		addKeyListener(new GameKeyListener());
		addMouseListener(new GameMouseListener());
		this.owner=owner;
	}

	public void restart()
	{
		curLevel=1;
		qp.reset();
		initLevel(curLevel);
		setStatus(STATUS_RUN);
		owner.repaint();
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(status!=STATUS_INIT)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("./images/level1.jpg"),0,0,this);
			for(int i=0;i<buildings.size();i++)
				((Item)buildings.get(i)).paint(g);
			player.paint(g);
		}
	}

	class GameMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			requestFocus();
		}
	}

	class GameKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent ke)
		{
			switch(ke.getKeyCode())
			{
				case KeyEvent.VK_UP:
					player.move(Player.MOVE_UP);
					break;
				case KeyEvent.VK_DOWN:
					player.move(Player.MOVE_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					player.move(Player.MOVE_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					player.move(Player.MOVE_RIGHT);
					break;
				default:
					return;
			}
			boolean tmp1=false,tmp2=false;
			for(int i=0;i<buildings.size();i++)
			{
				tmp1=((Item)buildings.get(i)).hitTest(player);
				if((tmp1)&&(buildings.get(i) instanceof Warp))
				{
					initLevel(++curLevel);
					owner.repaint();
				}
				else if((tmp1)&&
					(hit==false)&&
					(!((IBuilding)buildings.get(i)).isClosed()))
				{
					if(((IBuilding)buildings.get(i)).canEnter(player.getKeys()))
					{
						hit=true;
						tmp2=true;
						QuestionForm qf=new QuestionForm(null);
	
						if(qf.showDialog(qp.getQuestion()))
						{
							((IBuilding)buildings.get(i)).close(true);
							player.addKey(((IBuilding)buildings.get(i)).getReward());
							if(buildings.get(i) instanceof Castle)
							{
								buildings.add(new Warp());
							}
							owner.repaint();
						}
						else
						{
							player.setLives(player.getLives()-1);
							if(player.getLives()==0)
							{
								owner.repaint();
								setStatus(STATUS_GAME_OVER);
							}
							owner.repaint();
						}
					}
					else
					{
						String tmp=((IBuilding)buildings.get(i)).getNededKeys().toString();
						JOptionPane.showMessageDialog(null,"You need "+tmp+" keys to enter!");
					}
				}
				else if(tmp1)
				{
					tmp2=true;
				}
			}
			if(tmp2==false)
				hit=false;
			repaint();
		}
	}
}