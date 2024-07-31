package com.vgsoftware.bank.account;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

public abstract class AccountBean implements EntityBean
{
	public abstract void setUser(String user);
	public abstract String getUser();
	public abstract void setNumber(Integer number);
	public abstract Integer getNumber();
	public abstract void setBalance(Double balance);
	public abstract Double getBalance();

	public Integer ejbCreate(String user, Integer number)
	{
		setUser(user);
		setNumber(number);
		setBalance(0.0);
		return(null);
	}

	public void ejbPostCreate(String user, Integer number)
	{
	}

	public boolean deposit(Double amount)
	{
		setBalance(getBalance()+amount);
		return(true);
	}

	public boolean withdraw(Double amount)
	{
		if(getBalance()<amount)
			return(false);
		setBalance(getBalance()-amount);
		return(true);
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
