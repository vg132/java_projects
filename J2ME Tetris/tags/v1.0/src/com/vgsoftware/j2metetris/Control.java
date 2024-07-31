package com.vgsoftware.j2metetris;

public class Control
{
	public void Handle(Game game, int keyState, long frmctr)
	{
		J2MEControl c=game.getPlayer().getControl();
		c.init(frmctr,keyState);
		if(game.getStatus()==Game.STATUS_MAIN_MENU)
		{
			if(c.Up(7))
			{
				if(game.getMenuPos()==1)
					game.setMenuPos(0);	
				else
					game.setMenuPos(1);
			}
			if(c.Down(7))
			{
				if(game.getMenuPos()==1)
					game.setMenuPos(0);	
				else
					game.setMenuPos(1);
			}
			if(c.Fire(7))
			{
				if(game.getMenuPos()==0)
				{
					game.getPlayer().reset();
					game.setStatus(Game.STATUS_RUNNING);
				}
				else
				{
					game.setStatus(Game.STATUS_EXIT);
				}
			}
		}
		else if(game.getStatus()==Game.STATUS_RUNNING)
		{
			if(c.Down(2))
			{
				if(game.getPlayer().getField().move(Field.MOVE_DOWN))
				{
					game.getPlayer().setLastMove(frmctr);
					game.getPlayer().addPoints(1);
				}
			}
			if(c.Left(5))
				game.getPlayer().getField().move(Field.MOVE_LEFT);
			if(c.Right(5))
				game.getPlayer().getField().move(Field.MOVE_RIGHT);
			if(c.Fire(5))
				game.getPlayer().getField().rotate(Field.ROTATE_LEFT);
			if(c.Up(5))
				game.getPlayer().getField().rotate(Field.ROTATE_RIGHT);
		}
		else if(game.getStatus()==Game.STATUS_GAME_OVER)
		{
			if(c.Fire(5))
				game.setStatus(Game.STATUS_MAIN_MENU);
		}
	}
}
