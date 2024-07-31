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
import javax.microedition.lcdui.Form;

import com.vgsoftware.kthchat.ChatClient;

/**
 * Super class for the states in the application.
 *  
 * @author Viktor
 * @version 1.0
 */
public abstract class AChatState
{
	protected ChatClient client=null;
	protected Command exitCommand=new Command("Exit",Command.EXIT,1);
	protected Command okCommand=new Command("Ok",Command.OK,1);

	public abstract void nextState();
	public abstract Form getForm();
}
