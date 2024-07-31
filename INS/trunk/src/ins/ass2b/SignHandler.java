/*
 * Created on: 2003-dec-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-dec-13 Created by Viktor
 */
package ins.ass2b;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

/**
 * Signs the input data with a key from a keystore and saves the
 * certificate and signature
 */
public class SignHandler
{
	public static void main(String args[])
	{
		if(args.length==7)
			new SignHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 7.");
	}
	
	/**
	 * Loads a keystore and returns it.
	 * 
	 * @param fileName The path to the keystore to load.
	 * @param password The password for this keystore.
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
			return(null);
		}
		catch(KeyStoreException kse)
		{
			kse.printStackTrace(System.err);
			return(null);
		}
		catch(CertificateException ce)
		{
			ce.printStackTrace(System.err);
			return(null);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
			return(null);
		}
	}
	
	private KeyStore loadDefaultKeyStore(String password)
	{
		return(loadKeyStore(System.getProperty("user.home")+File.separator+".keystore",password));
	}
	
	private SignHandler(String args[])
	{
		KeyStore ks=loadKeyStore(args[0],args[1]);
		if(ks!=null)
		{
			try
			{
				PrivateKey key=(PrivateKey)ks.getKey(args[2],args[3].toCharArray());
				Certificate cert=ks.getCertificate(args[2]);
				byte[] data=readData(args[4]);
				byte[] sign=getSignature(key,data);
				writeSignature(args[6],sign);
				FileOutputStream fos=new FileOutputStream(args[5]);
				fos.write(cert.getEncoded());
				fos.close();
			}
			catch(CertificateEncodingException cee)
			{
			}
			catch(IOException io)
			{
			}
			catch(KeyStoreException kse)
			{
			}
			catch(UnrecoverableKeyException uke)
			{
			}
			catch(NoSuchAlgorithmException nsae)
			{
			}
		}
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
			nsae.printStackTrace(System.err);
			return(null);
		}
		catch(NoSuchProviderException nspe)
		{
			nspe.printStackTrace(System.err);
			return(null);
		}
		catch(InvalidKeyException ike)
		{
			ike.printStackTrace(System.err);
			return(null);
		}
		catch(SignatureException se)
		{
			se.printStackTrace(System.err);
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
			io.printStackTrace(System.err);
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
			io.printStackTrace(System.err);
		}
	}
}