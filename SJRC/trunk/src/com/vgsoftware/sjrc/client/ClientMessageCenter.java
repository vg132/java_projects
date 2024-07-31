/*
 * Created on 2003-sep-14
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-14 Created by Viktor.
 * 2004-may-28 Updated for secure comunication by Viktor.
 */
package com.vgsoftware.sjrc.client;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.vgsoftware.sjrc.client.panel.Channel;
import com.vgsoftware.sjrc.client.panel.JRCPanel;
import com.vgsoftware.sjrc.client.panel.Server;
import com.vgsoftware.sjrc.data.ClientData;
import com.vgsoftware.sjrc.data.ServerData;

/**
 * This is the main class on the client side.<br/>
 * This class recives all messages sent to the client and it acts on them and it also
 * send all messages from this client to the server.
 */
public class ClientMessageCenter
{
	private Map openChannels=new HashMap();
	private Map waitingChannels=new HashMap();
	private Connection conn=null;
	private IWindow frmClient=null;

	/**
	 * Creates a new SJRC Client and shows the main window on screen.
	 */
	public ClientMessageCenter(IWindow frmClient)
	{
		this.frmClient=frmClient;
	}

	/**
	 * Gets the window dimensions of the current client window.
	 *
	 * @return the window dimensions.
	 */
	public Dimension getWindowDimension()
	{
		return(frmClient.getSize());
	}

	/**
	 * Returns the status of the server connection
	 *
	 * @return <code>true</code> if this client is connected to a server, <code>false</code> otherwise
	 */
	public boolean isConnected()
	{
		if(conn==null)
			return(false);
		return(conn.open());
	}

	/**
	 * Quits all channels that the client is connected to, also closes the connection to<br/>
	 * the server.
	 */
	public void quitAll()
	{
		if((conn!=null)&&(conn.open()))
		{
			Iterator iter=openChannels.values().iterator();
			while(iter.hasNext())
			{
				JRCPanel panel=(JRCPanel)iter.next();
				if(panel instanceof Channel)
				{
					conn.send(new ClientData(conn.getNick(),ClientData.QUIT_CHANNEL,panel.getName()));
					iter.remove();
				}
			}
			conn.send(new ClientData(conn.getNick(),ClientData.QUIT,null));
			try
			{
				if((conn!=null)&&(conn.open()))
					conn.closeConnection();
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
			frmClient.removePanel((JRCPanel)openChannels.remove("SERVER"));
		}
	}

	/**
	 * Quits one of the channels that the client is connected to.
	 * The channel to quit is specified with the channelName parameter.
	 * If the user try to quit the SERVER channel this function will exit without doing anything.
	 *
	 * @param channelName the name of the channel
	 */
	public void quitChannel(String channelName)
	{
		if(channelName.equalsIgnoreCase("SERVER"))
			return;
		JRCPanel panel=(JRCPanel)openChannels.remove(channelName);
		if(panel!=null)
		{
			conn.send(new ClientData(conn.getNick(),ClientData.QUIT_CHANNEL,panel.getName()));
			frmClient.removePanel(panel);
		}
	}

	/**
	 * Connects to the server using the Connection object provided in the parameter.<br/>
	 * If it is successfull in connecting to the server a server channel will be created<br/>
	 * and shown in the main window.
	 *
	 * @param conn a <code>{@link Connection}</code> object.
	 *
	 * @return <code>true</code> if successfull, <code>false</code> otherwise.
	 */
	public boolean connect(Connection conn)
	{
		this.conn=conn;
		JRCPanel server=new Server(this);
		openChannels.put("SERVER",server);
		frmClient.addPanel(server,"Server",true);
		try
		{
			server.setMessage("Connecting to server "+conn.getServer()+" on port "+conn.getPort()+".");
			conn.connect(this);
			server.setMessage("Connected.");
		}
		catch(IOException io)
		{
			frmClient.removePanel(server);
			return(false);
		}
		return(true);
	}

	/**
	 * @see #encodeMessage(ClientData clientData, String data, String channel, String color)
	 *
	 * @param clientData the base client data object for this operation. Usualy only inited with the command.
	 * @param data the data that will be added to the message, for example in a whois request this will be the username.
	 */
	public void encodeMessage(ClientData clientData, String data)
	{
		encodeMessage(clientData,data,null,null);
	}

	/**
	 * Creates a message based on the information recived in the parameters.<br/>
	 * The message is formated to the correct format and then sent to the server.<br/>
	 *
	 * @param clientData the base client data object for this operation. Usualy only inited with the command.
	 * @param data the data that will be added to the message, for example in a whois request this will be the username.
	 * @param channel the channel that this message will be sent to, only used by chat messages.
	 * @param color the color used for this text, only used by chat messages.
	 */
	public void encodeMessage(ClientData clientData, String data, String channel, String color)
	{
		clientData.setUserName(conn.getNick());
		clientData.setValue(data);
		if(clientData.getCommand()==ClientData.JOIN_CHANNEL)
		{
			if((openChannels.containsKey(data.toUpperCase()))||(waitingChannels.containsKey(data.toUpperCase())))
				return;
			Channel c=new Channel(this,data);
			waitingChannels.put(data.toUpperCase(),c);
			conn.send(clientData);
		}
		else if(clientData.getCommand()==ClientData.WHOIS)
		{
			conn.send(clientData);
		}
		else if(clientData.getCommand()==ClientData.CHAT)
		{
			if(openChannels.containsKey(channel.toUpperCase()))
			{
				clientData.setValue(channel+"|"+color+"|"+data);
				conn.send(clientData);
			}
		}
		else if(clientData.getCommand()==ClientData.LIST_USERS)
		{
			conn.send(clientData);
		}
		else if(clientData.getCommand()==ClientData.LIST_CHANNELS)
		{
			conn.send(clientData);
		}
	}

	/**
	 * Decodes messages recived from the SJRC Server and acts on the decoded informations.
	 *
	 * @param data the message recived from the server.
	 */
	public synchronized void decodeMessage(ServerData data)
	{
		StringTokenizer st=null;
		if(data.getValue()!=null)
			st=new StringTokenizer(data.getValue(),"|");
		String channel=data.getChannel();
		int command=data.getCommand();

		if(command==ServerData.USER_LIST)
		{
			String[] users=new String[st.countTokens()];
			int i=0;
			while(st.hasMoreTokens())
				users[i++]=st.nextToken();
			((Channel)openChannels.get(channel.toUpperCase())).setUsers(users);
		}
		else if(command==ServerData.CONNECTED)
		{
			Channel c=(Channel)waitingChannels.remove(channel.toUpperCase());
			if(c!=null)
			{
				c.setName(channel);
				openChannels.put(channel.toUpperCase(),c);
				frmClient.addPanel(c,channel,true);
			}
		}
		else if(command==ServerData.DISCONNECTED)
		{
			openChannels.remove(channel.toUpperCase());
		}
		else if(command==ServerData.SYSTEM)
		{
			((JRCPanel)openChannels.get(channel.toUpperCase())).setMessage(null,st.nextToken(),st.nextToken());
		}
		else if(command==ServerData.CHAT)
		{
			String user=st.nextToken();
			String color=st.nextToken();
			String message=st.nextToken();
			((JRCPanel)openChannels.get(channel.toUpperCase())).setMessage(user,color,message);
		}
		else if(command==ServerData.WHOIS)
		{
			((JRCPanel)openChannels.get("SERVER")).setMessage(st.nextToken());
		}
		else if(command==ServerData.LIST_USERS)
		{
			String info="Users:";
			while(st.hasMoreTokens())
				info+="\n"+st.nextToken();
			((JRCPanel)openChannels.get("SERVER")).setMessage(info);
		}
		else if(command==ServerData.LIST_CHANNELS)
		{
			String info="Channels:";
			while(st.hasMoreTokens())
			info+="\n"+st.nextToken();
			((JRCPanel)openChannels.get("SERVER")).setMessage(info);
		}
		else if(command==ServerData.ERROR)
		{
			String type=st.nextToken();
			if(type.equals("USERNAME_ALREADY_IN_USE"))
			{
				quitAll();
				JOptionPane.showMessageDialog(null,"Username already in use, please select another username.");
			}
			else if(type.equals("USER_NOT_FOUND"))
			{
				((JRCPanel)openChannels.get("SERVER")).setMessage("User not found");
			}
		}
		else if(command==ServerData.KEY_OK)
		{
			((JRCPanel)openChannels.get("SERVER")).setMessage("Session encryption setup OK.");
			((JRCPanel)openChannels.get("SERVER")).setMessage("Sending nick and password.");
			conn.send(new ClientData(conn.getNick(),ClientData.CONNECT,conn.getPassword()));
			((JRCPanel)openChannels.get("SERVER")).setMessage("Nick and password sent.");
		}
	}
}
