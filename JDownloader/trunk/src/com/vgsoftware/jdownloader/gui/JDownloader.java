package com.vgsoftware.jdownloader.gui;

import com.vgsoftware.jdownloader.engine.IJDEngine;
import com.vgsoftware.jdownloader.engine.JDEngine;
import com.vgsoftware.jdownloader.gui.nowindow.ConsoleGui;
import com.vgsoftware.jdownloader.gui.window.WindowGui;

public class JDownloader
{
	public static void main(String args[])
	{
		IJDEngine engine=new JDEngine();
		if((args.length>0)&&(args[0].equals("nowindow")))
			new ConsoleGui(engine);
		else
			new WindowGui(engine);
	}
}
