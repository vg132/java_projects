package com.vgsoftware.kthchat;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.kthchat.network.ClientMessageCenter;
import com.vgsoftware.kthchat.state.AChatState;
import com.vgsoftware.kthchat.state.ConnectionState;

/**
 * ChatClient is the main class for this chat client for mobile devices.
 *   
 * @author Viktor
 * @version 1.0
 */
public class ChatClient extends MIDlet
{
	private ClientMessageCenter cmc=null;
	private boolean splitScreen=false;

	private Display display=null;
	private AChatState currentState=null;
	private int canvasWidth=0;
	private int canvasHeight=0;

	/**
	 * Class constructor.
	 */
	public ChatClient()
	{
		display=Display.getDisplay(this);
	}

	public void startApp()
	throws MIDletStateChangeException
	{
		//Get screen Size
		CanvasSize cs=new CanvasSize();
		display.setCurrent(cs);
		canvasWidth=cs.getWidth();
		canvasHeight=cs.getHeight();
		
		currentState=ConnectionState.getInstance(this);
		display.setCurrent(currentState.getForm());
	}
	
	/**
	 * Sets the current state and display of this application.
	 * 
	 * @param state the new state for this application.
	 */
	public void changeState(AChatState state)
	{
		currentState=state;
		display.setCurrent(currentState.getForm());
	}
	
	public void destroyApp(boolean unconditional)
	throws MIDletStateChangeException
	{
	}

	/**
	 * Gets the current ClientMessageCenter object.
	 * 
	 * @return the current ClientMessageCenter object
	 */
	public ClientMessageCenter getCMC()
	{
		return(cmc);
	}
	
	/**
	 * Sets the current ClientMessageCenter object.
	 * 
	 * @param cmc the current ClientMessageCenter object
	 */
	public void setCMC(ClientMessageCenter cmc)
	{
		this.cmc=cmc;
	}

	/**
	 * Gets the current layout settings.
	 * 
	 * @return true if splitscreen is selected, otherwise false
	 */
	public boolean getSplitScreen()
	{
		return(splitScreen);
	}
	
	/**
	 * Sets splitscreen mode on or off.
	 * 
	 * @param splitScreen true if splitscreen is selected, otherwise false 
	 */
	public void setSplitScreen(boolean splitScreen)
	{
		this.splitScreen=splitScreen;
	}

	public void pauseApp()
	{
	}
	
	/**
	 * Gets the height of this screen.
	 * 
	 * @return the height of this screen
	 */
	public int getCanvasHeight()
	{
		return(canvasHeight);
	}

	/**
	 * Gets the width of this screen.
	 * 
	 * @return the width of this screen
	 */
	public int getCanvasWidth()
	{
		return(canvasWidth);
	}
}