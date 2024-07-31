/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-01 Updated by Viktor, added key handeling and encryption of messages.
 */
package com.vgsoftware.sjrc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.vgsoftware.sjrc.data.ClientData;
import com.vgsoftware.sjrc.data.ServerData;

/**
 * Server representation of a connected user.<br/>
 * The ServerUser class handles all communication between the server
 * and the user.
 */
public class ServerUser extends Thread
{
	private static long idCounter;

	private Socket socket=null;
	private String nick=null;
	private ObjectOutputStream oos=null;
	private ServerMessageCenter mc=null;
	private Map channels=new HashMap();
	private SecretKey sKey=null;
	private Cipher cipher=null;
	private SecretKeySpec skeySpec=null;
	private KeyPair keyPair=null;

	/**
	 * Constructs a new user with a name and socket.
	 * @param mc the message center that handels the messages for this user.
	 * @param socket the socket used for communication with this user.
	 * @param keyPair the servers public and private key pair.
	 * 
	 * @throws IOException if an I/O error occurs when creating the output stream.
	 */
	public ServerUser(ServerMessageCenter mc, Socket socket, KeyPair keyPair)
	throws IOException
	{
		this.mc=mc;
		this.socket=socket;
		oos=new ObjectOutputStream(socket.getOutputStream());
		this.keyPair=keyPair;
		initCommunication();
	}

	/**
	 * Constructs a new user with a name and socket.
	 * 
	 * @param mc the message center that handels the messages for this user.
	 * @param socket the socket used for communication with this user.
	 * @param nick the nick name of this user.
	 * @param keyPair the servers public and privet key pair.
	 * 
	 * @throws IOException if an I/O error occurs when creating the output stream.
	 */
	public ServerUser(ServerMessageCenter mc, Socket socket, String nick, KeyPair keyPair)
	throws IOException
	{
		this.mc=mc;
		this.socket=socket;
		this.nick=nick;
		oos=new ObjectOutputStream(socket.getOutputStream());
		this.keyPair=keyPair;
		initCommunication();
	}

	/**
	 * Gets the nick name of this user.
	 * @return the nick name of this user.
	 */
	public String getNick()
	{
		return(nick);
	}

	/**
	 * Sets the nick name of this user.
	 * @param nick the nick name of this user.
	 */
	public void setNick(String nick)
	{
		this.nick=nick;
	}
	
	/**
	 * Gets the socket used for comunication with this user.
	 * @return the socket used for comunication with this user.
	 */
	public Socket getSocket()
	{
		return(socket);
	}
	
	/**
	 * Sets the socket used for communication with this user.
	 * @param socket the socket to use for communication with this user.
	 */
	public void setSocket(Socket socket)
	{
		this.socket=socket;
	}

	/**
	 * Sends a message to this user.
	 * 
	 * @param data the message that will be sent.
	 */
	public void send(ServerData data)
	{
		try
		{
			oos.writeObject(new SealedObject(data,cipher));
		}
		catch(IllegalBlockSizeException ibse)
		{
			ibse.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * Close the connection to this user.
	 */
	public void closeConnection()
	{
		try
		{
			socket.close();
		}
		catch(IOException io)
		{
		}
	}

	/**
	 * Overloaded toString method, will return a string in the format<br/>
	 * <code>nick@hostname</code>
	 */
	public String toString()
	{
		return(nick+"@"+socket.getInetAddress().getHostName());
	}

	/**
	 * Adds a channel to the list of channels that this user is connected to.
	 * 
	 * @param channel channel to be added.
	 */
	public void addChannel(ServerChannel channel)
	{
		channels.put(channel.getName(),channel);
	}

	/**
	 * Removes the user from the specified channel and removes the channel from the list
	 * of channels that this user is connected to.
	 * 
	 * @param channel the channel to remove.
	 */
	public void removeChannel(String channel)
	{
		((ServerChannel)channels.get(channel)).removeUser(this);
		channels.remove(channel);
	}
	
	/**
	 * Gets all chnnels that this user is connected to.
	 *
	 * @return a <code>Map</code> with all channels, null if there are no channels,  
	 */
	public Map getChannels()
	{
		return(channels);
	}

	/**
	 * Initialize comunication with a new client by first sending the public key used
	 * when the client sends over the session key.
	 */
	public void initCommunication()
	{
		try
		{
			oos.writeObject(keyPair.getPublic());
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * Unwrap the session key recived from the client and send a OK message back to the
	 * client if the key was recived OK.
	 * 
	 * @param key The raw wrapped key. This key is wrapped with the servers public key.
	 */
	private void initKey(byte[] key)
	{
		try
		{
			Cipher tempCipher=Cipher.getInstance(keyPair.getPrivate().getAlgorithm());
			tempCipher.init(Cipher.UNWRAP_MODE,keyPair.getPrivate());
			sKey=(SecretKey)tempCipher.unwrap(key,"AES",Cipher.SECRET_KEY);
			byte[] raw=sKey.getEncoded();
			skeySpec=new SecretKeySpec(raw, "AES");
			cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,skeySpec);
			send(new ServerData("SYSTEM",ServerData.KEY_OK,null));
		}
		catch(InvalidKeyException ike)
		{
			ike.printStackTrace(System.err);
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
		}
		catch(NoSuchPaddingException nspe)
		{
			nspe.printStackTrace(System.err);
		}
	}

	/**
	 * Listen for incoming messages from the user. When a message is recived it sends it to
	 * the message center for decoding or if it is a keydata message send it to initKey.<br/>
	 * When the connection is lost it calls the {@link ServerMessageCenter#quit(Map channels, ServerUser user)} function
	 * to close all communication with this user.
	 *  
	 */
	public void run()
	{
		try
		{
			ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			Object obj=null;
			while((obj=ois.readObject())!=null)
			{
				if(obj instanceof SealedObject)
				{
					mc.decodeMessage((ClientData)((SealedObject)obj).getObject(sKey),this);
				}
				else if(obj instanceof byte[])
				{
					initKey((byte[])obj);
				}
			}
			oos.close();
			ois.close();
			socket.close();
		}
		catch(IOException io)
		{
			mc.quit(channels,this);
		}
		catch(ClassNotFoundException cnfe)
		{
			mc.quit(channels,this);
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
		}
		catch(InvalidKeyException ike)
		{
			ike.printStackTrace(System.err);
		}
	}
}
