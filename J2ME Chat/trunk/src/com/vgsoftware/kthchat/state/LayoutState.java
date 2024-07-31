/*
 * Created on 2004-nov-14 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.state;

import java.io.IOException;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.kthchat.ChatClient;

/**
 * LayoutState is the state where the user can select if he want to use
 * splitscreen or not. As all states this is a singleton class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class LayoutState extends AChatState implements CommandListener
{
	private static AChatState instance=new LayoutState();
	private Form frmScreenLayout=null;
	private ChoiceGroup screenSetup=new ChoiceGroup("Select Screen Layout",ChoiceGroup.EXCLUSIVE);

	private Command exitCommand=new Command("Exit",Command.EXIT,1);
	private Command okCommand=new Command("Ok",Command.OK,1);

	private LayoutState()
	{
		frmScreenLayout=new Form("Screen Layout");
		try
		{
			screenSetup.append("1 Screen layout",Image.createImage("/res/layout2.png"));
			screenSetup.append("2 Screen layout",Image.createImage("/res/layout1.png"));
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		frmScreenLayout.append(screenSetup);
		frmScreenLayout.addCommand(exitCommand);
		frmScreenLayout.addCommand(okCommand);
		frmScreenLayout.setCommandListener(this);
	}
	
	/**
	 * Returns the form for this state.
	 * 
	 * @return the form for this state
	 */
	public Form getForm()
	{
		return(frmScreenLayout);
	}

	/**
	 * Returns the instance for the next state.
	 */
	public void nextState()
	{
		client.changeState(ChatState.getInstance(client));
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
	 * Moves to the next state and save the settings or quits application.
	 * 
	 * @param c the command that was triggered
	 * @param d
	 */
	public void commandAction(Command c,Displayable d)
	{
		if(c==okCommand)
		{
			if(screenSetup.getSelectedIndex()==0)
				client.setSplitScreen(true);
			else
				client.setSplitScreen(false);
			nextState();
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
