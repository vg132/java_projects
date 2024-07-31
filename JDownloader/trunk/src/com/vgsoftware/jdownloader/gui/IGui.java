package com.vgsoftware.jdownloader.gui;

import java.io.File;
import java.net.URL;

public interface IGui
{
	/**
	 * Question from the JDEngine when the selected file excists if it should resume,
	 * restart or cancel the download.
	 * 
	 * @param id id of the download in question.
	 * 
	 * @return what to do, 0=resume, 1=restart, 2=cancel.
	 */
	public int eventResumeRestartCancel(File file, URL url);
	/**
	 * This method is called when there are any changes to the download list (added,removed etc).
	 */
	public void eventDownloadList();
	/**
	 * This method is called when a download is finished.
	 * 
	 * @param id id of the download that is finised.
	 */
	public void eventFinished(int id);
	/**
	 * This method is called for warnings from the engine.
	 */
	public void eventWarning(String text);
	/**
	 * This method is called for fatal errors from the engine.
	 */
	public void eventFatalError(String text);
}
