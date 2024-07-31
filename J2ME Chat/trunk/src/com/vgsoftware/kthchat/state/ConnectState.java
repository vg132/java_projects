/*
 * Created on 2004-nov-14 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.state;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.kthchat.ChatClient;
import com.vgsoftware.kthchat.network.ClientMessageCenter;

/**
 * ConnectionState tries to open a connection to the server
 * and depending on the success of this different things happen.
 * As all states this is a singleton class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class ConnectState extends AChatState implements CommandListener, Runnable
{
	private static AChatState instance=new ConnectState();
	private Form frmConnect=null;
	private Gauge prbConnecting=new Gauge("Connecting...",false,10,0);
	private ClientMessageCenter cmc=null;

	private Command exitCommand=new Command("Exit",Command.EXIT,1);
	private Command okCommand=new Command("Ok",Command.OK,1);
	private Command backCommand=new Command("Back",Command.BACK,1);

	private ConnectState()
	{
	}

	/**
	 * Tries to connect to the server and updates the
	 * gauge on screen. Times out after 5 seconds.
	 */
	public void run()
	{
		boolean up=true;
		try
		{
			cmc=client.getCMC();
			cmc.connect();
			int timeout=100;
			while((!cmc.isConnected())&&(timeout>0))
			{
				if(up)
					prbConnecting.setValue(prbConnecting.getValue()+1);
				else
					prbConnecting.setValue(prbConnecting.getValue()-1);
				if(prbConnecting.getValue()>=10)
					up=false;
				else if(prbConnecting.getValue()<=0)
					up=true;
				Thread.sleep(50);
				timeout--;
			}
			frmConnect.deleteAll();
			if(cmc.isConnected())
			{
				frmConnect.append("Successfully connected to chat server.");
				frmConnect.addCommand(okCommand);
				frmConnect.addCommand(exitCommand);
			}
			else
			{
				frmConnect.append("Unable to connect to chat server.");
				frmConnect.addCommand(backCommand);
				frmConnect.addCommand(exitCommand);
			}
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	/**
	 * Returns the instance for this class.
	 * 
	 * @param client the current ChatClient object. 
	 */
	public static AChatState getInstance(ChatClient client)
	{
		instance.client=client;
		return(instance);
	}
	
	/**
	 * Inits and returns the form for this state.
	 * 
	 * @return the form for this state
	 */
	public Form getForm()
	{
		frmConnect=new Form("Connecting...");
		prbConnecting.setLayout(Gauge.LAYOUT_EXPAND);
		frmConnect.append(prbConnecting);
		frmConnect.setCommandListener(this);
		Thread t=new Thread(this);
		t.start();

		return(frmConnect);
	}

	/**
	 * Returns the instance for the next state.
	 */
	public void nextState()
	{
		client.changeState(LayoutState.getInstance(client));
	}

	/**
	 * Returns the instance for the previous state.
	 */
	public void prevState()
	{
		client.changeState(ConnectionState.getInstance(client));
	}

	/**
	 * If successfull connection move to next state, otherwise move back to ConnectionState
	 * or quit application if that button was pressed.
	 * 
	 * @param c the command that was triggered
	 * @param d
	 */
	public void commandAction(Command c,Displayable d)
	{
		if(c==okCommand)
		{
			nextState();
		}
		else if(c==backCommand)
		{
			prevState();
		}
		else if(c==exitCommand)
		{
			try
			{
				client.destroyApp(false);
			}
			catch(MIDletStateChangeException msce)
			{
			}
			client.notifyDestroyed();
		}
	}
}
