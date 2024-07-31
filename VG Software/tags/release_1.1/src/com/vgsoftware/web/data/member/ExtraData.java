package com.vgsoftware.web.data.member;

public class ExtraData
{
	private int id=-1;
	private int memberId=-1;
	private String name=null;
	private String data=null;

	public ExtraData()
	{
	}
	
	public ExtraData(int id, int memberId, String name, String data)
	{
		this.id=id;
		this.memberId=memberId;
		this.name=name;
		this.data=data;
	}
	
	/**
	 * @return Returns the data.
	 */
	public String getData()
	{
		return data;
	}
	/**
	 * @param data The data to set.
	 */
	public void setData(String data)
	{
		this.data=data;
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
	 * @return Returns the member_id.
	 */
	public int getMemberId()
	{
		return memberId;
	}
	/**
	 * @param member_id The member_id to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
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
}
