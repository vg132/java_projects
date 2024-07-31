package com.vgsoftware.appengine.slrealtime.dataabstraction;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.vgsoftware.appengine.slrealtime.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TransportationType
{
	@PrimaryKey
	@Persistent
	private Long _id;

	@Persistent
	private String _name;

	public TransportationType()
	{
	}
	
	public TransportationType(long id, String name)
	{
		setId(id);
		setName(name);
	}

	public long getId()
	{
		return _id;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}
	
	public void save()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			pm.makePersistent(this);
		}
		finally
		{
			pm.close();
		}
	}
}
