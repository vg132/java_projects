/*
 * Created on 2004-nov-28 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 2005-sep-27 - Rewritten.
 */
package com.vgsoftware.web.data.member;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberData
{
	private int id=-1;
	private String name=null;
	private String password=null;
	private String email=null;
	private Timestamp joinDate=null;
	private String groupName=null;
	private int groupId=-1;
	private List<ServiceData> services=new ArrayList<ServiceData>();
	private Map<String,ExtraData> extra=new HashMap<String,ExtraData>();

	public MemberData()
	{
	}

	public boolean isAdmin()
	{
		return(groupName.equalsIgnoreCase("admin"));
	}

	public String getEmail()
	{
		return(email);
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public int getId()
	{
		return(id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public String getName()
	{
		return(name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	/**
	 * @return Returns the group.
	 */
	public String getGroupName()
	{
		return(groupName);
	}

	/**
	 * @param group The group to set.
	 */
	public void setGroupName(String groupName)
	{
		this.groupName=groupName;
	}

	/**
	 * @return Returns the memberGroup.
	 */
	public int getGroupId()
	{
		return(groupId);
	}
	
	public void setGroupId(int groupId)
	{
		this.groupId=groupId;
	}
	
	public void addServices(List<ServiceData> sd)
	{
		services.addAll(sd);
	}

	public void addService(ServiceData sd)
	{
		services.add(sd);
	}

	public boolean isMemberOf(String service)
	{
		for(ServiceData sd : services)
		{
			if(sd.getName().equals(service))
				return(true);
		}
		return(false);
	}

	public void addExtras(Map<String,ExtraData> ed)
	{
		extra.putAll(ed);
	}

	public void addExtra(ExtraData ed)
	{
		extra.put(ed.getName(),ed);
	}
	
	public ExtraData getExtra(String name)
	{
		return(extra.get(name));
	}

	public String getExtraData(String name)
	{
		if(extra.containsKey(name))
			return(extra.get(name).getData());
		return(null);
	}

	/**
	 * @return Returns the joinDate.
	 */
	public Timestamp getJoinDate()
	{
		return joinDate;
	}

	/**
	 * @param joinDate The joinDate to set.
	 */
	public void setJoinDate(Timestamp joinDate)
	{
		this.joinDate=joinDate;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return(password);
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}
}
