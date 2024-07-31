/*
 * Created on: 2003-dec-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-dec-13 Created by Viktor
 */
package ins.ass3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Creates a secret key.
 */
public class KeyHandler
{
	public static void main(String args[])
	{
		if(args.length==1)
			new KeyHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 1.");
	}
	
	private KeyHandler(String args[])
	{
		SecretKey skey=createKey();
		try
		{
			saveKey(skey,args[0]);
		}
		catch(IOException io)
		{
			System.err.println("ERROR: Could not save key file.");
			io.printStackTrace(System.err);
		}
	}
	
	/**
	 * Generates a AES secret key.
	 * 
	 * @return The new secretkey.
	 */
	private SecretKey createKey()
	{
		try
		{
			KeyGenerator kg=KeyGenerator.getInstance("AES");
			kg.init(128);
			return(kg.generateKey());
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
			return(null);
		}
	}
	
	/**
	 * Serialize a key to a file.
	 * 
	 * @param key The key that will be serialized.
	 * @param fileName The filename to use when serializing the key.
	 * 
	 * @throws IOException
	 */
	private void saveKey(Key key, String fileName)
	throws IOException
	{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));
		oos.writeObject(key);
		oos.flush();
		oos.close();
	}
}
