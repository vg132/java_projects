package com.vgsoftware.web.data.f1video;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-30 - Document created.
 */
public class QualityData
{
	private int id=-1;
	private String name=null;
	private String sign=null;
	private String info=null;
	
	public QualityData(int id, String name, String sign, String info)
	{
		this.id=id;
		this.name=name;
		this.sign=sign;
		this.info=info;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return info;
	}
	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info)
	{
		this.info=info;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}
	/**
	 * @return Returns the sign.
	 */
	public String getSign()
	{
		return(sign);
	}
	/**
	 * @param sign The sign to set.
	 */
	public void setSign(String sign)
	{
		this.sign=sign;
	}
}
