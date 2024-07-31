/*
 * Created on: 2003-dec-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-dec-13 Created by Viktor
 */
package ins.ass2a;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Verify if a given signature is OK.
 */
public class VerifyHandler
{
	public static void main(String args[])
	{
		if(args.length==3)
			new VerifyHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 3.");
	}
	
	public VerifyHandler(String args[])
	{
		byte[] data=readData(args[0]);
		PublicKey pk=(PublicKey)loadKey(args[1]);
		byte[] sign=readSignature(args[2]);
		if(verifySignature(pk,data,sign))
			System.out.println("OK");
		else
			System.out.println("Not OK");
	}
	
	/**
	 * Checks if this signature was signed by the owner of the privet key
	 * 
	 * @param key The public key to use when verifying the digest.
	 * @param digest The digest that has been signed.
	 * 
	 * @return The signature.
	 */
	private boolean verifySignature(PublicKey key, byte[] message, byte[] signature)
	{
		try
		{
			Signature sign=Signature.getInstance("SHA1withDSA","SUN");
			sign.initVerify(key);
			sign.update(message);
			return(sign.verify(signature));
		}
		catch(NoSuchAlgorithmException nsae)
		{
			return(false);
		}
		catch(NoSuchProviderException nspe)
		{
			return(false);
		}
		catch(InvalidKeyException ike)
		{
			return(false);
		}
		catch(SignatureException se)
		{
			return(false);
		}
	}
	
	/**
	 * Loads a key from the file system.
	 * 
	 * @param fileName The object file containing the key.
	 * 
	 * @return The read key or null if no key was found.
	 */
	private Key loadKey(String fileName)
	{
		try
		{
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileName));
			Key key=(Key)ois.readObject();
			ois.close();
			return(key);
		}
		catch(ClassNotFoundException cnfe)
		{
			return(null);
		}
		catch(IOException io)
		{
			return(null);
		}
	}
	
	/**
	 * Read all data from a file into a string.
	 * 
	 * @param fileName The path to the file that will be read.
	 * 
	 * @return The file content.
	 */
	private byte[] readData(String fileName)
	{
		try
		{
			File file=new File(fileName);
			FileInputStream fis=new FileInputStream(file);
			
			byte[] data=new byte[(int)file.length()];
			fis.read(data);
			fis.close();
			return(data);
		}
		catch(IOException io)
		{
			return(null);
		}
	}

	/**
	 * Writes the signature to a file.
	 * 
	 * @param fileName The filename of the file used to save this signature.
	 * @param sign The signature.
	 */
	private byte[] readSignature(String fileName)
	{
		try
		{
			File file=new File(fileName);
			FileInputStream fis=new FileInputStream(file);
			byte[] sign=new byte[(int)file.length()];
			fis.read(sign);
			fis.close();
			return(sign);
		}
		catch(IOException io)
		{
			return(null);
		}
	}
}
