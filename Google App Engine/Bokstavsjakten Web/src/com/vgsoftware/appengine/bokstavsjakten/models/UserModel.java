package com.vgsoftware.appengine.bokstavsjakten.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.vgsoftware.appengine.bokstavsjakten.data.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserModel implements Serializable
{
	@PrimaryKey
	@Index
	private String _userName = null;
	@Persistent
	private String _password = null;
	@Persistent
	private int _matches = 0;
	@Persistent
	private int _wins = 0;
	@Persistent
	private Date _registrationDate = null;
	@Persistent
	private List<PushIdModel> _pushIds = null;

	public UserModel(String userName, String password)
	{
		_userName = userName;
		_password = password;
	}

	public void save()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			pm.makePersistent(this);
			// Cache.put(this.getUserName(), this);
		}
		finally
		{
			pm.close();
		}
	}

	public static UserModel getAccount(String userName)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			Query query = pm.newQuery(UserModel.class);
			query.setFilter("_userName == userNameParameter");
			query.declareParameters("String userNameParameter");
			List<UserModel> userList = (List<UserModel>) query.execute(userName);
			if (userList != null && userList.size() > 0)
			{
				return userList.get(0);
			}
		}
		finally
		{
			pm.close();
		}
		return null;
	}

	public String getUserName()
	{
		return _userName;
	}

	public int getWins()
	{
		return _wins;
	}

	public void setWins(int wins)
	{
		_wins = wins;
	}

	public int getMatches()
	{
		return _matches;
	}

	public void setMatches(int matches)
	{
		_matches = matches;
	}

	public Date getRegistrationDate()
	{
		return _registrationDate;
	}

	public void setRegistrationDate(Date date)
	{
		_registrationDate = date;
	}
}
