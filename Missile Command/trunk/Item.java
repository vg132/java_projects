/**
 * The Interface for the game objects (missile and explosion)
 * (C) Viktor Gars 2002
 */

import java.awt.Graphics;

public interface Item
{
	public final int LINE=0;
	public final int HIT=1;
	public final int CLICK=2;

	public void paint(Graphics g);
	public boolean finished();
	public void move();
	public Pos getCurPos();
	public void setState(int state);
	public int getState();
}