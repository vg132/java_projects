/*
 * Created on 2004-sep-04 by Viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.jrc.client.applet;

import javax.swing.JApplet;


public class JRCApplet extends JApplet
{
	private int port=-1;
	private String server=null;
	
	public void init()
	{
		port=Integer.parseInt(getParameter("port"));
		server=getParameter("server");
	}
}
