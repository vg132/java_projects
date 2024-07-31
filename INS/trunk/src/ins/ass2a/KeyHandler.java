/*
 * Created on: 2003-dec-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-dec-13 Created by Viktor
 */
package ins.ass2a;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Creates a public and a privet DSA key and save them to files.
 */
public class KeyHandler
{
	public KeyHandler(String args[])
	{
		try
		{
			KeyPair kp=createDSAKeyPair();
			if(kp!=null)
			{
				saveKey(kp.getPrivate(),args[0]);
				saveKey(kp.getPublic(),args[1]);
			}
		}
		catch(FileNotFoundException fnfe)
		{
		}
		catch(IOException io)
		{
		}
	}
	
	public static void main(String args[])
	{
		if(args.length==2)
			new KeyHandler(args);
		else
			System.out.println("Wrong number of arguments, 2 expected.");
	}
	
	/**
	 * Generate a DSA keypair.
	 * 
	 * @return The generated keypair or null.
	 */
	private KeyPair createDSAKeyPair()
	{
		try
		{
			KeyPairGenerator kpg=KeyPairGenerator.getInstance("DSA","SUN");
			SecureRandom securerandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			kpg.initialize(1024, securerandom);
			return(kpg.generateKeyPair());
		}
		catch(NoSuchProviderException nspe)
		{
			return(null);
		}
		catch(NoSuchAlgorithmException nsae)
		{
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
