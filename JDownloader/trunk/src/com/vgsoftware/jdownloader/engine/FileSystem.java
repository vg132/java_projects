package com.vgsoftware.jdownloader.engine;

import java.io.File;

public class FileSystem
{
	public static boolean removeExtension(File file, String extension)
	{
		String fileName=file.getAbsolutePath();
		return(file.renameTo(new File(fileName.substring(0,fileName.length()-extension.length()))));
	}

	public static boolean addExtension(File file, String extension)
	{
		return(file.renameTo(new File(file.getAbsolutePath()+extension)));
	}
}
