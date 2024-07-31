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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Verify that a given signature is OK based on the data and certificate.
 */
public class VerifyHandler
{
	public static void main(String args[])
	{
		if(args.length==3)
			new VerifyHandler(args);
		else
			System.out.println("Wrong number of arguments, expected 7.");
	}
	
	private VerifyHandler(String args[])
	{
		byte[] data=readData(args[0]);
		Certificate cert=loadCertificate(args[1]);
		byte[] sign=readSignature(args[2]);
		if(verifySignature(cert.getPublicKey(),data,sign))
			System.out.println("Ok");
		else
			System.out.println("Not Ok");
	}
	
	/**
	 * Load a certificate from a file into a certificate object.
	 * 
	 * @param fileName The file with the certificate data.
	 * 
	 * @return The certificate if successful, otherwise null.
	 */
	private Certificate loadCertificate(String fileName)
	{
		try
		{
			FileInputStream fileinputstream = new FileInputStream(fileName);
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			return(certificatefactory.generateCertificate(fileinputstream));
		}
		catch(CertificateException ce)
		{
			return(null);
		}
		catch(FileNotFoundException fnfe)
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
			io.printStackTrace(System.err);
			return(null);
		}
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
