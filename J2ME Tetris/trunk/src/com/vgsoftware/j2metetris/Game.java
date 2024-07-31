package com.vgsoftware.j2metetris;

public class Game
{
	public static int STATUS_MAIN_MENU=10;
	public static int STATUS_RUNNING=11;
	public static int STATUS_PAUSED=12;
	public static int STATUS_GAME_OVER=13;
	public static int STATUS_EXIT=14;

	private Player player=null;
	private int status=0;
	private int menuPos=0;

	public Game()
	{
	}

	public void addPlayer(Player player)
	{
		this.player=player;
	}

	public Player getPlayer()
	{
		return(player);
	}

	public void setStatus(int status)
	{
		this.status=status;
	}

	public int getStatus()
	{
		return(status);
	}

	public int getMenuPos()
	{
		return menuPos;
	}

	public void setMenuPos(int menuPos)
	{
		this.menuPos=menuPos;
	}
}
