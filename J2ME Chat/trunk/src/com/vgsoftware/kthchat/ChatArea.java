package com.vgsoftware.kthchat;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * ChatArea is a custom text box that will line break the text so that it fits inside
 * the area of the text box. ChatArea also supports text scrolling by pressing the buttons
 * three and nine on the phone.  
 * 
 * @author Viktor
 * @version 1.0
 */
public class ChatArea extends CustomItem
{
	private Vector rows=new Vector();
	private int height=0;
	private int width=0;
	private int lineWidth=0;
	private int currentRow=0;
	private int visibleRows=0;
	private Font font=Font.getDefaultFont();

	/**
	 * Class constructor specifying the width and height of this
	 * screen.
	 * 
	 * @param width the width of this screen
	 * @param height the height of this screen
	 */
	public ChatArea(int width, int height)
	{
		super("");
		this.height=height-20;
		this.width=width-8;
		this.lineWidth=this.width-4;
		this.visibleRows=this.height/(font.getHeight());
		setLayout(LAYOUT_EXPAND|LAYOUT_TOP);
	}

	protected int getMinContentHeight()
	{
		return(height);
	}

	protected int getMinContentWidth()
	{
		return(width);
	}

	protected int getPrefContentHeight(int height)
	{
		return(this.height);
	}

	protected int getPrefContentWidth(int width)
	{
		return(this.width);
	}

	protected void paint(Graphics g, int x, int y)
	{
		g.setFont(font);
		g.drawLine(0,0,0,height);
		g.drawLine(width-1,0,width-1,height);
		g.drawLine(0,0,width-1,0);
		g.drawLine(0,height-1,width-1,height-1);

		int loops=0;
		for(int i=((currentRow-visibleRows)<0)?0:(currentRow-visibleRows);i<currentRow&&i<rows.size();i++)
		{
			g.drawString((String)rows.elementAt(i),3,(font.getHeight()*(loops++))+3,Graphics.TOP|Graphics.LEFT);
		}
	}

	private String[] getText(int fontHeight)
	{
		String[] text=null;
		text=new String[(height-6)/fontHeight];
		return(null);
	}

	protected void keyPressed(int keyCode)
	{
		switch(keyCode)
		{
			case Canvas.KEY_NUM3:
				if((currentRow-visibleRows)>0)
					currentRow--;
				break;
			case Canvas.KEY_NUM9:
				if(currentRow<rows.size())
					currentRow++;
				break;
		}
		this.invalidate();
	}

	/**
	 * Adds a row of text to the text area and cuts the row into smaller pices if
	 * needed to fit on the screen.
	 * 
	 * @param text the row to be added 
	 */
	public void appendRow(String text)
	{
		if(font.stringWidth(text)>lineWidth)
		{
			int offset=0;
			int len=1;
			do
			{
				while(((offset+len)<=text.length())&&(font.substringWidth(text,offset,len)<=lineWidth))
				{
					len++;
				}
				rows.addElement(text.substring(offset,offset+len-1).trim());
				offset+=len-1;
				len=1;
			}while(offset+len<=text.length());
		}
		else
		{
			rows.addElement(text);
		}
		currentRow=rows.size();
		this.invalidate();
	}
}
