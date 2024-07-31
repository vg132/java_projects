/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-01 Updated for secure comunication by Viktor
 */
package com.vgsoftware.sjrc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.vgsoftware.sjrc.data.ClientData;
import com.vgsoftware.sjrc.data.ServerData;

/**
 * The connection class represents a connection to a server.
 * It opens the connection, closes the connection and listen for incoming messages.
 */
public class Connection
{
	private ClientMessageCenter cmc=null;
	private Listen l=null;
	private String password=null;
	private int port=-1;
	private ObjectOutputStream oos=null;
	private String server=null;
	private Socket socket=null;
	private String nick=null;
	private SecretKey sKey=null;
	private PublicKey serverKey=null;
	private SecretKeySpec skeySpec=null;
	private Cipher cipher=null;

	/**
	 * Constructs a connection object with the specified values.
	 * 
	 * @param server the address to the server.
	 * @param port the port on which to make the connection.
	 * @param nick the nick name to use for this connection.
	 * @param password the password to use for this connection.
	 */
	public Connection(String server, int port, String nick, String password)
	{
		this.server=server;
		this.port=port;
		this.nick=nick;
		this.password=password;
		sKey=getKey();
	}

	/**
	 * Close this connection.
	 * 
	 * @throws IOException
	 */
	public void closeConnection()
	throws IOException
	{
		socket.close();
	}
	
	/**
	 * Open a connection to a server based on the information in this object.
	 * When the connection is created creat a printwriter for the connection and start
	 * listen for incoming messages from the server.
	 * 
	 * @param cmc the {@link ClientMessageCenter} used to handle incoming messages.
	 * @return <code>true</code> if the connection was created, <code>false</code> otherwise.
	 * @throws IOException if the creation of a connection failed a IOException will be thrown.
	 */
	public boolean connect(ClientMessageCenter cmc)
	throws IOException
	{
		socket=new Socket(server,port);
		oos=new ObjectOutputStream(socket.getOutputStream());
		this.cmc=cmc;
		l=new Listen();
		l.start();
		return(true);
	}
	
	/**
	 * Gets the port of this connection.
	 * @return the port.
	 */
	public int getPort()
	{
		return(port);
	}
	
	/**
	 * Checks if this connection is open.
	 * @return <code>true</code> if open, <code>false</code> otherwise.
	 */
	public boolean open()
	{
		if(socket==null)
			return(false);
		return(!socket.isClosed());
	}
	
	/**
	 * Gets the server address for this connection.
	 * @return the server address.
	 */
	public String getServer()
	{
		return(server);
	}
	
	/**
	 * Gets the nick name of the user for this connection.
	 * @return the nick name.
	 */
	public String getNick()
	{
		return(nick);
	}

	/**
	 * Gets the password assosiated with this connection.
	 * @return the password.
	 */
	public String getPassword()
	{
		return(password);
	}

	/**
	 * Seales and send a message object to the server. The object is sealed with the
	 * session key.
	 * 
	 * @param data the data object that will be sent.
	 */
	public void send(ClientData data)
	{
		try
		{
			oos.writeObject(new SealedObject(data,cipher));
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(IllegalBlockSizeException ibse)
		{
			ibse.printStackTrace(System.err);
		}
	}

	/**
	 * Generate a new AES secret key for use in this session.
	 * 
	 * @return The new <code>SecretKey</code>. 
	 */
	private SecretKey getKey()
	{
		try
		{
			KeyGenerator kgen=KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey skey=kgen.generateKey();
			byte[] raw=skey.getEncoded();
			skeySpec=new SecretKeySpec(raw, "AES");
			cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,skeySpec);
			return(skey);
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
		return(null);
	}
	
	/**
	 * Packes the secret key for use in this session and sends it to the server
	 * encrypted with the servers public key.
	 * 
	 * @param publicKey the servers public key.
	 */
	private void initCommunication(PublicKey publicKey)
	{
		try
		{
			serverKey=publicKey;
			Cipher tempCipher=Cipher.getInstance(serverKey.getAlgorithm());
			tempCipher.init(Cipher.WRAP_MODE,serverKey);
			byte[] wrappedKey=tempCipher.wrap(sKey);
			oos.writeObject(wrappedKey);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(IllegalBlockSizeException ibse)
		{
			ibse.printStackTrace(System.err);
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

	class Listen extends Thread
	{
		/**
		 * Listen for incoming messages from the server. When a message is recived
		 * send it for decoding to the {@link ClientMessageCenter} or if its a public key
		 * send it to {@link initCommunivations(PublicKey publicKey)}.
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
						cmc.decodeMessage((ServerData)((SealedObject)obj).getObject(sKey));
					else if(obj instanceof PublicKey)
						initCommunication((PublicKey)obj);
				}
			}
			catch(NoSuchAlgorithmException nsae)
			{
				nsae.printStackTrace(System.err);
			}
			catch(InvalidKeyException ike)
			{
				ike.printStackTrace(System.err);
			}
			catch(ClassNotFoundException cnfe)
			{
				System.err.println("Unknown object recived. Quiting.");
			}
			catch(IOException io)
			{
				System.err.println("Connection Closed.");
			}
		}
	}
}
