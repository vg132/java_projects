/*
 * Created on 2004-nov-14 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.state;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

import com.vgsoftware.kthchat.ChatClient;
import com.vgsoftware.kthchat.network.ClientMessageCenter;

/**
 * ConnectionState takes in connection information from the
 * user and creates a ClientMessageCenter object based on this
 * information. As all states this is a singleton class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class ConnectionState extends AChatState implements CommandListener
{
	private static AChatState instance=new ConnectionState();
	private Form frmLogon=null;
	private TextField txtServer=new TextField("Server:","",50,TextField.ANY);
	private TextField txtPort=new TextField("Port:","",4,TextField.NUMERIC);
	private TextField txtNickname=new TextField("Nickname:","",10,TextField.ANY);
	private ChoiceGroup chkSaveInfo=new ChoiceGroup("Save information?",ChoiceGroup.MULTIPLE);
	private RecordStore rs=null;
	private boolean init=false;

	private Command exitCommand=new Command("Exit",Command.EXIT,1);
	private Command okCommand=new Command("Ok",Command.OK,1);

	private ConnectionState()
	{
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
		if(init==false)
		{
			frmLogon=new Form("Connection");
			if((client.getAppProperty("locked")==null)||(client.getAppProperty("locked").equals("true")==false))
			{
				frmLogon.append(txtServer);
				frmLogon.append(txtPort);
			}
			else
			{
				txtServer.setString(client.getAppProperty("server"));
				txtPort.setString(client.getAppProperty("port"));
			}
			frmLogon.append(txtNickname);
			chkSaveInfo.append("Yes",null);
			frmLogon.append(chkSaveInfo);
			frmLogon.addCommand(exitCommand);
			frmLogon.addCommand(okCommand);
			frmLogon.setCommandListener(this);
	
			//Load data from record if there is any data.
			try
			{
				rs=RecordStore.openRecordStore("j2mechat",false);
				byte[] data=rs.getRecord(1);
				int i=0;
				for(;data[i]!='|';i++)
					txtServer.setString(txtServer.getString()+(char)data[i]);
				i++;
				for(;data[i]!='|';i++)
					txtPort.setString(txtPort.getString()+(char)data[i]);
				i++;
				for(;i<data.length;i++)
					txtNickname.setString(txtNickname.getString()+(char)data[i]);
			}
			catch(RecordStoreNotFoundException rsnfe)
			{
				txtServer.setString(client.getAppProperty("server"));
				txtPort.setString(client.getAppProperty("port"));
			}
			catch(RecordStoreException rse)
			{
				rse.printStackTrace();
			}
			init=true;
		}
		return(frmLogon);
	}

	/**
	 * Returns the instance for the next state.
	 */
	public void nextState()
	{
		client.changeState(ConnectState.getInstance(client));
	}

	/**
	 * Saves the connection information to RMS if the user has selected
	 * this option and moves to the next state or if the user pressed the 
	 * quit button quits the application.
	 * 
	 * @param c the command that was triggered
	 * @param d
	 */
	public void commandAction(Command c,Displayable d)
	{
		if(c==okCommand)
		{
		  if(chkSaveInfo.isSelected(0))
		  {
				try
				{
					rs=RecordStore.openRecordStore("j2mechat",true);
					byte[] data=(txtServer.getString()+"|"+txtPort.getString()+"|"+txtNickname.getString()).getBytes();
					if(rs.getNumRecords()==1)
						rs.setRecord(1,data,0,data.length);
					else
						rs.addRecord(data,0,data.length);
				}
				catch(RecordStoreException rse)
				{
					rse.printStackTrace();
				}
		  }

			client.setCMC(new ClientMessageCenter(txtServer.getString(),Integer.parseInt(txtPort.getString()),txtNickname.getString()));
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
