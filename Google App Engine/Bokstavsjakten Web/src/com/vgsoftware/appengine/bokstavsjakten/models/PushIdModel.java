package com.vgsoftware.appengine.bokstavsjakten.models;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PushIdModel implements Serializable
{
	public static int ANDROID = 10;
	public static int WINDOWS_PHONE = 20;
	public static int IOS = 30;

	@Persistent
	@Index
	private UserModel _user = null;
	@Persistent
	private int _type;
	@PrimaryKey
	private String _id = null;

	public PushIdModel(UserModel user, int type, String id)
	{
		_user = user;
		_type = type;
		_id = id;
	}

	public UserModel getUser()
	{
		return _user;
	}

	public int getType()
	{
		return _type;
	}

	public String getId()
	{
		return _id;
	}
}
