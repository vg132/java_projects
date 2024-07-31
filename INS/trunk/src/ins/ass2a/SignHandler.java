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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Creates a signature file from the input data and private key.
 */
public class SignHandler
{
	public static void main(String args[])
	{
		if(args.length==3)
			new SignHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 3.");
	}
	
	public SignHandler(String args[])
	{
		byte[] data=readData(args[0]);
		PrivateKey pk=(PrivateKey)loadKey(args[1]);
		byte[] sign=getSignature(pk,data);
		writeSignature(args[2],sign);
	}
	
	/**
	 * Sign a message digest with a DSA private key.
	 * 
	 * @param key The private key to use when signing the digest.
	 * @param digest The digest that will be signed.
	 * 
	 * @return The signature.
	 */
	private byte[] getSignature(PrivateKey key, byte[] data)
	{
		try
		{
			Signature sign=Signature.getInstance("SHA1withDSA","SUN");
			sign.initSign(key);
			sign.update(data);
			return(sign.sign());
		}
		catch(NoSuchAlgorithmException nsae)
		{
			return(null);
		}
		catch(NoSuchProviderException nspe)
		{
			return(null);
		}
		catch(InvalidKeyException ike)
		{
			return(null);
		}
		catch(SignatureException se)
		{
			return(null);
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
	private void writeSignature(String fileName, byte[] sign)
	{
		try
		{
			FileOutputStream fos=new FileOutputStream(fileName);
			fos.write(sign);
			fos.close();
		}
		catch(IOException io)
		{
		}
	}
}
