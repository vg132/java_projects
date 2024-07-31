/*
 * Created on 2003-sep-14
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-14 Created by Viktor.
 */
package com.vgsoftware.jrc.client;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.vgsoftware.jrc.client.panel.Channel;
import com.vgsoftware.jrc.client.panel.JRCPanel;
import com.vgsoftware.jrc.client.panel.Server;

/**
 * This is the main class on the client side.<br/>
 * This class recives all messages sent to the client and it acts on them and it also
 * send all messages from this client to the server.
 */
public class ClientMessageCenter
{
	public static final int JOIN_CHANNEL=0;
	public static final int QUIT_CHANNEL=1;
	public static final int WHOIS=2;
	public static final int LIST_USERS=3;
	public static final int LIST_CHANNELS=4;
	public static final int CHAT=5;

	private Map openChannels=new HashMap();
	private Map waitingChannels=new HashMap();
	private Connection conn=null;
	private FrmClient frmClient=null;

	/**
	 * Creates a new JRC Client and shows the main window on screen.
	 */
	public ClientMessageCenter()
	{
		frmClient=new FrmClient(this);
		frmClient.showWindow();
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
					conn.send(conn.getNick()+"|QUIT_CHANNEL|"+panel.getName());
					iter.remove();
				}
			}
			conn.send(conn.getNick()+"|QUIT");
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
			conn.send(conn.getNick()+"|QUIT_CHANNEL|"+panel.getName());
			frmClient.removePanel(panel);
		}
	}

	/**
	 * Connects to the server using the Connection object provided in the parameter.<br/>
	 * If it is successfull in connecting to the server a server channel will be created<br/>
	 * and shown in the main window. 
	 * 
	 * @param conn a <code>{@link Connection}</code> object.
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
			server.setMessage("Sending nick and password.");
			conn.send(conn.getNick()+"|CONNECT|"+conn.getPassword());
			server.setMessage("Nick and password sent.");
		}
		catch(IOException io)
		{
			frmClient.removePanel(server);
			return(false);
		}
		return(true);
	}

	/**
	 * @see #encodeMessage(int type, String data, String channel, String color)
	 * 
	 * @param type type of message, chat, whois request, join channel request etc. See static member variables.
	 * @param data the data that will be added to the message, for example in a whois request this will be the username.
	 */
	public void encodeMessage(int type, String data)
	{
		encodeMessage(type,data,null,null);
	}

	/**
	 * Creates a message based on the information recived in the parameters.<br/>
	 * The message is formated to the correct format and then sent to the server.<br/>
	 * 
	 * @param type type of message, chat, whois request, join channel request etc. See static member variables.
	 * @param data the data that will be added to the message, for example in a whois request this will be the username.
	 * @param channel the channel that this message will be sent to, only used by chat messages.
	 * @param color the color used for this text, only used by chat messages.
	 */
	public void encodeMessage(int type, String data, String channel, String color)
	{
		if(type==JOIN_CHANNEL)
		{
			if((openChannels.containsKey(data.toUpperCase()))||(waitingChannels.containsKey(data.toUpperCase())))
				return;
			Channel c=new Channel(this,data);
			waitingChannels.put(data.toUpperCase(),c);
			conn.send(conn.getNick()+"|JOIN_CHANNEL|"+data);
		}
		else if(type==WHOIS)
		{
			conn.send(conn.getNick()+"|WHOIS|"+data);			
		}
		else if(type==CHAT)
		{
			if(openChannels.containsKey(channel.toUpperCase()))
				conn.send(conn.getNick()+"|CHAT|"+channel+"|"+color+"|"+data);
		}
		else if(type==LIST_USERS)
		{
			conn.send(conn.getNick()+"|LIST_USERS");
		}
		else if(type==LIST_CHANNELS)
		{
			conn.send(conn.getNick()+"|LIST_CHANNELS");
		}
	}

	/**
	 * Decodes messages recived from the JRC Server and acts on the decoded informations.
	 * 
	 * @param str the message recived from the server.
	 */
	public synchronized void decodeMessage(String str)
	{
		StringTokenizer st=new StringTokenizer(str,"|");
		String channel=st.nextToken();
		String command=st.nextToken();

		if(command.equals("USER_LIST"))
		{
			String[] users=new String[st.countTokens()];
			int i=0;
			while(st.hasMoreTokens())
				users[i++]=st.nextToken();
			((Channel)openChannels.get(channel.toUpperCase())).setUsers(users);
		}
		else if(command.equals("CONNECTED"))
		{
			Channel c=(Channel)waitingChannels.remove(channel.toUpperCase());
			if(c!=null)
			{
				c.setName(channel);
				openChannels.put(channel.toUpperCase(),c);
				frmClient.addPanel(c,channel,true);
			}
		}
		else if(command.equals("DISCONNECTED"))
		{
			openChannels.remove(channel.toUpperCase());
		}
		else if(command.equals("SYSTEM"))
		{
			((JRCPanel)openChannels.get(channel.toUpperCase())).setMessage(null,st.nextToken(),st.nextToken());
		}
		else if(command.equals("CHAT"))
		{
			String user=st.nextToken();
			String color=st.nextToken();
			String message=st.nextToken();
			((JRCPanel)openChannels.get(channel.toUpperCase())).setMessage(user,color,message);
		}
		else if(command.equals("WHOIS"))
		{
			((JRCPanel)openChannels.get("SERVER")).setMessage(st.nextToken());
		}
		else if(command.equals("LIST_USERS"))
		{
			String info="Users:";
			while(st.hasMoreTokens())
			info+="\n"+st.nextToken();
			((JRCPanel)openChannels.get("SERVER")).setMessage(info);
		}
		else if(command.equals("LIST_CHANNELS"))
		{
			String info="Channels:";
			while(st.hasMoreTokens())
			info+="\n"+st.nextToken();
			((JRCPanel)openChannels.get("SERVER")).setMessage(info);
		}
		else if(command.equals("ERROR"))
		{
			String type=st.nextToken();
			if(type.equals("USERNAME_ALREADY_IN_USE"))
			{
				quitAll();
				JOptionPane.showMessageDialog(frmClient,"Username already in use, please select another username.");
			}
			else if(type.equals("USER_NOT_FOUND"))
			{
				((JRCPanel)openChannels.get("SERVER")).setMessage("User not found");
			}
		}
	}
}
