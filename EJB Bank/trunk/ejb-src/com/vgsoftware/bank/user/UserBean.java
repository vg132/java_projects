package com.vgsoftware.bank.user;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

public abstract class UserBean implements EntityBean
{
	private static final Integer CUSTOMER_TYPE=0;
	private static final Integer ADMIN_TYPE=1;

	public abstract void setName(String name);
	public abstract String getName();
	public abstract void setPassword(String password);
	public abstract String getPassword();
	public abstract void setType(Integer type);
	public abstract Integer getType();

	public String ejbCreate(String name, String password)
	{
		this.setName(name);
		this.setPassword(password);
		this.setType(UserBean.CUSTOMER_TYPE);
		return(null);
	}

	public void ejbPostCreate(String name, String password)
	{
	}

	public String ejbCreate(String name, String password, Integer type)
	{
		this.setName(name);
		this.setPassword(password);
		this.setType(type);
		return(null);
	}

	public void ejbPostCreate(String name, String password, Integer type)
	{
	}

	public Boolean validate(String password)
	{
		if(password.equals(getPassword()))
			return(true);
		return(false);
	}
	
	public Boolean isAdmin()
	{
		if(getType().intValue()==UserBean.ADMIN_TYPE.intValue())
			return(true);
		return(false);
	}
	
	public void ejbActivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbLoad()
	throws EJBException, RemoteException
	{
	}

	public void ejbPassivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbRemove()
	throws RemoveException, EJBException, RemoteException
	{
	}

	public void ejbStore()
	throws EJBException, RemoteException
	{
	}

	public void setEntityContext(EntityContext ctx)
	throws EJBException, RemoteException
	{
	}

	public void unsetEntityContext()
	throws EJBException, RemoteException
	{
	}
}
