/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-02 Updated by Viktor for SJRC.
 */
package com.vgsoftware.sjrc.client;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Start class for the SJRC Client program.
 */
public class Client
{
	/**
	 * Creates and starts a new SJRC client. First add the BouncyCastleProvider to the
	 * security providers.
	 * @param args commandline aguments.
	 */
	public static void main(String args[])
	{
		Security.addProvider(new BouncyCastleProvider());
		FrmClient frmClient=new FrmClient();
	}
}
