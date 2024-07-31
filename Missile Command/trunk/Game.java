/**
 * The main game class
 * (C) Viktor Gars 2002
 */

import java.awt.event.*;
import java.util.Vector;
import java.awt.*;
import java.applet.*;

public class Game extends Applet implements Runnable
{
	//some game state varables
	private final int INTRO=0;
	private final int PLAYING=1;
	private final int PAUSE=2;
	private final int BETWEENLEVELS=3;
	private final int GAMEOVER=4;
	private final int FINISHED=5;
	//state holder :)
	private int state=INTRO;

	//Double buffering buffers
	private Image offscreenImg;
	private Graphics offscreen;

	//Vectors with all active game objects
	private Vector buildings=new Vector(10);
	private Vector explosions=new Vector(10);
	private Vector missiles=new Vector(10);

	//misc variables
	private int i=-1;
	private int iLoop=-1;
	private int missileIntervall=0;
	private Explosion explosion=null;
	private Missile missile=null;
	private Building building=null;
	Thread runner=null;

	//Level data missiles, bombs and points for every level (10 levels)
	private int[] levelMissiles={5,10,15,20,25,30,35,40,45,50};
	private int[] levelBombs={7,12,16,20,25,29,34,38,42,45};
	private int[] levelBasePoints={200,220,250,300,350,400,450,500,600,800};
	private int[] levelTimeIntervall={200,800,150,800,150,700,100,600,100,600,80,620,70,630,60,440,50,430,40,360};

	//Level/Point/other tracking variables
	private int points=0;
	private int levelMissilesLeft=0;
	private int levelBombsLeft=0;
	private int level=-1;

	//Move to the next level (level 1-10, 0-9)
	private void nextLevel()
	{
		if(state==PLAYING&&level<9)
		{
			level++;
			levelMissilesLeft=levelMissiles[level];
			levelBombsLeft=levelBombs[level];
			missileIntervall=0;
		}
		else if(state==PLAYING&&level>9)
			state=FINISHED;
	}

	public void init()
	{
		offscreenImg=createImage(getSize().width,getSize().height);
		offscreen=offscreenImg.getGraphics();
		this.enableEvents(MouseEvent.MOUSE_PRESSED);
		this.enableEvents(KeyEvent.KEY_PRESSED);
		state=INTRO;
	}

	public void start()
	{
		if(runner==null)
		{
			runner=new Thread(this);
			runner.start();
		}
	}

	public void stop()
	{
		runner=null;
	}

	public void run()
	{
		Thread thisThread = Thread.currentThread();
		while(runner==thisThread)
		{
			if(state==PLAYING)
			{
				if(levelMissilesLeft>0&&missileIntervall<1)
				{
					//Add new missile
					missiles.addElement(new Missile(new Pos(Math.random()*getSize().width,0),new Pos(Math.random()*getSize().width,getSize().height),3,Missile.LINE));
					//Calculate time before next missile
					missileIntervall=((int)((Math.random()*levelTimeIntervall[(level*2)+1])+levelTimeIntervall[level*2])/10);
					levelMissilesLeft--;
				}
				else if(missileIntervall>0)
				{
					missileIntervall--;
				}

				//Loop for every missile
				for(i=0;i<missiles.size();i++)
				{
					missile=(Missile)missiles.elementAt(i);
					//Check if missile is finished
					if(!missile.finished())
					{
						//Move missile and check if it's a line (normal mode) or if it is hit
						missile.move();
						if(missile.getState()!=missile.HIT)
						{
							//Loop for every explosion
							for(iLoop=0;iLoop<explosions.size();iLoop++)
							{
								//Check if missile has been hit by a explosion
								if(((Explosion)explosions.elementAt(iLoop)).hitTest(missile.getCurPos()))
								{
									//Change missile state to HIT and add points
									missile.setState(missile.HIT);
									points+=levelBasePoints[level]+((getSize().height)-(missile.getCurPos().getIY()));
								}
							}
							//Loop for every building and se if there is a hit
							for(iLoop=0;iLoop<buildings.size();iLoop++)
							{
								if(((Building)buildings.elementAt(iLoop)).hitTest(missile.getCurPos()))
								{
									//Change missile state to HIT and remove building
									missile.setState(missile.HIT);
									buildings.removeElement(buildings.elementAt(iLoop));
									iLoop--;
								}
							}
						}
					}
					else
					{
						//Remove missile if it's finished and move "loop" pointer back one step
						missiles.removeElement(missile);
						i--;
					}
				}
				for(i=0;i<explosions.size();i++)
				{
					explosion=(Explosion)explosions.elementAt(i);
					if(!explosion.finished())
					{
						explosion.move();
					}
					else
					{
						explosions.removeElement(explosion);
						i--;
					}

				}
				if(buildings.size()==0)
					state=GAMEOVER;
				else if(missiles.size()==0)
					state=BETWEENLEVELS;
			}
			repaint();
			try
			{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
			}
		}
	}

	public void update(Graphics screen)
	{
		paint(screen);
	}

	// Main paint function, paint missiles, explosions, buildings or, if not playing, paint a message on the screen
	public void paint(Graphics screen)
	{
		offscreen.setColor(Color.black);
		offscreen.fillRect(0,0,getSize().width,getSize().height);
		if(state==PLAYING)
		{
			for(i=0;i<missiles.size();i++)
				((Missile)missiles.elementAt(i)).paint(offscreen);
			for(i=0;i<explosions.size();i++)
				((Explosion)explosions.elementAt(i)).paint(offscreen);
			for(i=0;i<buildings.size();i++)
				((Building)buildings.elementAt(i)).paint(offscreen);
			gameInfo(offscreen);
		}
		else if(state==INTRO)
			buildText(offscreen,"Missile Command","(C) 2002 Viktor Gars (viktor.gars@telia.com)","Click to begin");
		else if(state==PAUSE)
			buildText(offscreen,"Pause",null,"Click to start");
		else if(state==GAMEOVER)
			buildText(offscreen,"Game Over!!",null,"Click");
		else if(state==FINISHED)
			buildText(offscreen,"Congratulations!","You have complited the game!","Click");
		else if(state==BETWEENLEVELS)
			buildText(offscreen,"Level "+(level+1)+" Clear!",null,"Click for next level");
		screen.drawImage(offscreenImg,0,0,this);
	}

	public void destroy()
	{
		offscreen.dispose();
	}

	//Monitor mouse clicks, add a explosion to the explosion vector if the game is running and remove one from the explosion counter
	public void processMouseEvent(MouseEvent me)
	{
		if(me.getID()==me.MOUSE_PRESSED)
		{
			if(levelBombsLeft>0&&state==PLAYING)
			{
				explosions.addElement(new Explosion(new Pos(me.getX(),me.getY()),4,50,Explosion.CLICK));
				levelBombsLeft--;
			}
			else if(state==INTRO)
			{
				resetGame();
				state=PLAYING;
				nextLevel();
			}
			else if(state==BETWEENLEVELS)
			{
				state=PLAYING;
				nextLevel();
			}
			else if(state==PAUSE)
				state=PLAYING;
			else if(state==GAMEOVER)
				state=INTRO;
		}
	}

	//Check if user has pressed the pause key
	public void processKeyEvent(KeyEvent ke)
	{
		if(ke.getID()==ke.VK_P||ke.getID()==ke.VK_PAUSE)
		{
			if(state==PAUSE)
				state=PLAYING;
			else if(state==PLAYING)
				state=PAUSE;
		}
	}

	//Reset all variables to start a new game
	public void resetGame()
	{
		points=0;
		buildings.removeAllElements();
		missiles.removeAllElements();
		explosions.removeAllElements();
		level=-1;
		for(i=0;i<5;i++)
			buildings.addElement(new Building((getSize().width/6)*(i+1),getSize().height-10));
		//nextLevel();
	}

	//Show game info (level, points etc)
	public void gameInfo(Graphics g)
	{
		g.setColor(Color.green);
		Font f=new Font("Courier",Font.BOLD,12);
		g.setFont(f);
		g.drawString("Missiles Left: "+levelMissilesLeft+" Bombs left: "+levelBombsLeft+" Level: "+(level+1)+" Points: "+points,10,10);
	}

	//Function for display text on standard position on the screen
	public void buildText(Graphics g,String t1, String t2, String t3)
	{
		int x=0;
		String s="";
		Font f=null;
		FontMetrics fm=null;
		g.setColor(Color.red);

		if(t1!=null)
		{
			f=new Font("Courier", Font.BOLD,40);
			fm = getFontMetrics(f);
			g.setFont(f);
			x=(getSize().width-fm.stringWidth(t1))/2;
			g.drawString(t1,x,(getSize().height)/3);
		}
		if(t2!=null)
		{
			f=new Font("Courier", Font.BOLD,12);
			fm=getFontMetrics(f);
			x=(getSize().width-fm.stringWidth(t2))/2;
			g.setFont(f);
			g.drawString(t2,x,((getSize().height)/3)+22);
		}
		if(t3!=null)
		{
			f=new Font("Courier", Font.BOLD,25);
			fm=getFontMetrics(f);
			x=(getSize().width-fm.stringWidth(t3))/2;
			g.setFont(f);
			g.drawString(t3,x,((getSize().height)/3)*2);
		}
	}
}