/*
 * Created on: 2003-dec-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-dec-13 Created by Viktor
 */
package ins.ass3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypt data with a secret key.
 */
public class EncryptHandler
{
	public static void main(String args[])
	{
		if(args.length==3)
			new EncryptHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 3.");
	}
	
	private EncryptHandler(String args[])
	{
		SecretKey sKey=(SecretKey)loadKey(args[1]);
		byte[] data=readData(args[0]);
		byte[] encData=encrypt(data,sKey);
		try
		{
			save(args[2],encData);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
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
	 * Save the encrypted data to a file.
	 * 
	 * @param fileName The file name.
	 * @param data The encrypted data.
	 */
	private void save(String fileName, byte[] data)
	throws IOException
	{
		FileOutputStream fos=new FileOutputStream(fileName);
		fos.write(data);
		fos.close();
	}

	/**
	 * Encrypt data with the secret key.
	 * 
	 * @param data The clear text data.
	 * @param key The secret key used to encrypt the data.
	 * 
	 * @return The encrypted data if successful, otherwise null.
	 */
	private byte[] encrypt(byte[] data, SecretKey key)
	{
		try
		{
			byte[] raw = key.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return(cipher.doFinal(data));
		}
		catch(NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace(System.err);
			return(null);
		}
		catch(NoSuchPaddingException nspe)
		{
			nspe.printStackTrace(System.err);
			return(null);
		}
		catch(InvalidKeyException ike)
		{
			ike.printStackTrace(System.err);
			return(null);
		}
		catch(BadPaddingException bpe)
		{
			bpe.printStackTrace(System.err);
			return(null);
		}
		catch(IllegalBlockSizeException ibse)
		{
			ibse.printStackTrace(System.err);
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
}
