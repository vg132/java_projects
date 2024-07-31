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
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.kthchat.ChatArea;
import com.vgsoftware.kthchat.ChatClient;
import com.vgsoftware.kthchat.network.ClientMessageCenter;
import com.vgsoftware.kthchat.observer.Observer;
import com.vgsoftware.kthchat.observer.Subject;

/**
 * ChatState shows the chat area and if splitscreen was selected also
 * a chat textbox. As all states this is a singleton class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class ChatState extends AChatState implements CommandListener, Observer
{
	private static AChatState instance=new ChatState();
	private Form frmChat=null;
	private TextField txtChat=new TextField("","",999,TextField.ANY);
	private Command cmdChat=null;
	private ChatArea ca=null;
	private boolean init=false;

	private ChatState()
	{
	}

	/**
	 * If this is the first time this function is called 
	 * init the form otherwise just return the current form.
	 * 
	 * @return the form
	 */
	public Form getForm()
	{
		if(init==false)
		{
			frmChat=new Form("Chat");
			if(client.getSplitScreen())
			{
				ca=new ChatArea(client.getCanvasWidth(),client.getCanvasHeight()-(txtChat.getPreferredHeight()+10));
				frmChat.append(ca);
				frmChat.append(txtChat);
				cmdChat=new Command("Send",Command.OK,1);
			}
			else
			{
				ca=new ChatArea(client.getCanvasHeight()-10,client.getCanvasWidth());
				frmChat.append(ca);
				cmdChat=new Command("Write",Command.OK,1);
			}
			frmChat.addCommand(exitCommand);
			frmChat.addCommand(cmdChat);
			frmChat.setCommandListener(this);
			client.getCMC().attach(this);
			init=true;
		}
		return(frmChat);
	}

	/**
	 * Returns the instance for the next state.
	 */
	public void nextState()
	{
		client.changeState(MessageState.getInstance(client));
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
	 * Gets notifications from the ClientMessageCenter about
	 * incoming chat messages from the server.
	 * 
	 * @param subject the subject that sent the notification
	 * @param event the unique event id
	 * @param data the data assosiated with this event
	 */
	public void update(Subject subject, int event, Object data)
	{
		if(event==ClientMessageCenter.CHAT)
			ca.appendRow((String)data);
	}
	
	/**
	 * If the user uses splitscreen sends the chat message
	 * otherwise move to MessageState or quit application if
	 * that button was pressed.
	 * 
	 * @param c the command that was triggered
	 * @param d
	 */
	public void commandAction(Command c,Displayable d)
	{
		if(c==cmdChat)
		{
			if(client.getSplitScreen())
			{
				client.getCMC().chat(txtChat.getString());
				txtChat.setString("");
			}
			else
			{
				nextState();
			}
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