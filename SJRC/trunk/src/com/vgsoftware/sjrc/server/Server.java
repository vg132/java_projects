/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-01 Updated by Viktor, added stuff for secure JRC.
 */
package com.vgsoftware.sjrc.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Start class for the server.<br/>
 * Creates a server socket, listen on port 2000 if no other port was provided via
 * the command line, <code>server [PORT]</code>.<br/>
 * Add a new security handler, BouncyCastleProvider, to make the RSA keys work.
 * Also get the servers RSA keys from a keystore.
 * The server then listen for new connections, when one is recived it is sent to
 * the {@link ServerMessageCenter} where its added and taken care of.
 */
public class Server extends Thread
{
	private ServerSocket serverSocket=null;
	private ServerMessageCenter mc=null;

	public static void main(String args[])
	{
		new Server(args);
	}

	public Server(String args[])
	{
		try
		{
			Security.addProvider(new BouncyCastleProvider());
			int port=2000;
			String keyStore=null;
			String ksPassword=null;
			String kyPassword=null;
			String alias=null;

			for(int i=0;i<args.length;i++)
			{
				if(args[i].equals("-port"))
					port=Integer.parseInt(args[i+1]);
				else if(args[i].equals("-keystore"))
					keyStore=args[i+1];
				else if(args[i].equals("-keypassword"))
					kyPassword=args[i+1];
				else if(args[i].equals("-storepassword"))
					ksPassword=args[i+1];
				else if(args[i].equals("-alias"))
					alias=args[i+1];
			}
			KeyPair kp=getKeys(keyStore,ksPassword,alias,kyPassword);
			if(kp==null)
			{
				System.out.println("No key found. Please check keystore path, alias and passwords.");
				return;
			}
			serverSocket=new ServerSocket(port);
			mc=new ServerMessageCenter(kp);
			start();
			System.out.println("Server running on port: "+port);
		}
		catch(IOException io)
		{

		}
		catch(NumberFormatException nfe)
		{
			System.out.println("VG Software SJRC Server. Default port: 2000");
			System.out.println("Usage: java -jar Server.jar [-port [port]] [[-keystore [path]] [-password [password]]]");
		}
	}

	/**
	 * Listen for new connections, when one is made sent it to the {@link ServerMessageCenter#addUser(Socket socket)} function.
	 */
	public void run()
	{
		try
		{
			Socket s=null;
			while(true)
			{
				s=serverSocket.accept();
				mc.addUser(s);
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * Load a keystore from the filesystem into the program.
	 *
	 * @param fileName the file path to the keystore.
	 * @param password the password for the keystore.
	 *
	 * @return The keystore that was loaded or null if none was loaded.
	 */
	private KeyStore loadKeyStore(String fileName, String password)
	{
		try
		{
			KeyStore ks=KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream(fileName),password.toCharArray());
			return(ks);
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
		}
		catch(KeyStoreException kse)
		{
			kse.printStackTrace(System.err);
		}
		catch(CertificateException ce)
		{
			ce.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(null);
	}

	/**
	 * Gets the public/private keypair to use with this server.<br/>
	 *
	 * @param path the path to the keystore where the key is stored.
	 * @param storePassword password for the keystore.
	 * @param alias the alias for the keys.
	 * @param keyPassword the password for the keys.
	 *
	 * @return the public and private key to be used by this program.
	 */
	private KeyPair getKeys(String path, String storePassword, String alias, String keyPassword)
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try
		{
			if(storePassword==null)
			{
				System.out.print("Keystore Password: ");
				storePassword=br.readLine().trim();
			}
			if(alias==null)
			{
				System.out.print("Alias: ");
				alias=br.readLine().trim();
			}
			if(keyPassword==null)
			{
				System.out.print("Key Password: ");
				keyPassword=br.readLine().trim();
			}
			if(path==null)
			{
				path=System.getProperty("user.home")+File.separator+".keystore";
			}
			KeyStore keyStore=loadKeyStore(path,storePassword);
			if(keyStore!=null)
			{
				PrivateKey privateKey=(PrivateKey)keyStore.getKey(alias,keyPassword.toCharArray());
				if(privateKey instanceof PrivateKey)
				{
					Certificate cert=keyStore.getCertificate(alias);
					PublicKey publicKey = cert.getPublicKey();
					return(new KeyPair(publicKey,privateKey));
				}
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(KeyStoreException kse)
		{
			kse.printStackTrace(System.err);
		}
		catch(UnrecoverableKeyException uke)
		{
			uke.printStackTrace(System.err);
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
		}
		return(null);
	}
}
