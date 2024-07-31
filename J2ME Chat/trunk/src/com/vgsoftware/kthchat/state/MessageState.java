/*
 * Created on 2004-dec-06 by viktor
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
import javax.microedition.lcdui.TextField;

import com.vgsoftware.kthchat.ChatClient;

/**
 * MessageState is the state for sending chat messages in when not
 * using a splitscreen. As all states this is a singleton class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class MessageState extends AChatState implements CommandListener
{
	private static AChatState instance=new MessageState();
	private Form frmMessage=null;
	private TextField txtChat=new TextField("","",999,TextField.ANY);
	private Command cmdChat=new Command("Send",Command.OK,1);
	private Command cmdBack=new Command("Back",Command.BACK,1);

	private MessageState()
	{
		frmMessage=new Form("Message");
		frmMessage.setCommandListener(this);
		txtChat.setLayout(TextField.LAYOUT_EXPAND|TextField.LAYOUT_VEXPAND);
		frmMessage.append(txtChat);
		frmMessage.addCommand(cmdBack);
		frmMessage.addCommand(cmdChat);
	}

	/**
	 * Returns the form for this sate.
	 * 
	 * @return the form for this state
	 */
	public Form getForm()
	{
		return(frmMessage);
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
	 * Sends the message that was written and returns to the chat
	 * state, or if user pressed back just return to the chat state.
	 * 
	 * @param c the command that was triggered
	 * @param d
	 */
	public void commandAction(Command c, Displayable d)
	{
		if(c==cmdChat)
		{
			client.getCMC().chat(txtChat.getString());
			txtChat.setString("");
			nextState();
		}
		else if(c==cmdBack)
		{
			txtChat.setString("");
			nextState();
		}
	}
}
