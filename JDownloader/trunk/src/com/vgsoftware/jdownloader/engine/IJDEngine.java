package com.vgsoftware.jdownloader.engine;

import java.io.File;
import java.net.URL;
import java.util.List;

import com.vgsoftware.jdownloader.data.Status;
import com.vgsoftware.jdownloader.gui.IGui;

public interface IJDEngine
{
	/**
	 * Sets the referense to the gui.
	 * 
	 * @param gui referense to the gui.
	 */
	public void setGui(IGui gui);
	/**
	 * Adds a new download.
	 * 
	 * @param file the file to save the downloaded data to.
	 * @param url the url to download.
	 * 
	 * @return true if successfull, otherwise false.
	 */
	public boolean addDownload(File file, URL url);
	/**
	 * Cancel a donwload, dont delete file.
	 * 
	 * @param id id of the download that will be canceled.
	 * 
	 * @return true if successfull, otherwise false.
	 */
	public boolean cancelDownload(int id);
	/**
	 * Cancel a donwload.
	 * 
	 * @param id id of the download that will be canceled.
	 * @param deleteFile if true delete the file, otherwise dont delete the file.
	 * 
	 * @return true if successfull, otherwise false.
	 */	
	public boolean cancelDownload(int id, boolean deleteFile);
	
	/**
	 * Gets the status for all current downloades.
	 * 
	 * @return status about all downloades.
	 */
	public List<Status> getDownloades();
	/**
	 * Gets the status for a specified download.
	 * 
	 * @param id id of the download that status will be returned for.
	 * 
	 * @return status about the download.
	 */
	public Status getDownload(int id);
	/**
	 * This method is called when the user quits the program.
	 */
	public void quit();
	/**
	 * This method is called when the GUI wants to pause or resume a download.
	 * 
	 * @param id id of the download that will be paused or resumed.
	 */
	public void pause(int id);
}
