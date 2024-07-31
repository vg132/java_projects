package com.vgsoftware.bank.customer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.vgsoftware.bank.account.AccountHome;
import com.vgsoftware.bank.account.AccountRemote;
import com.vgsoftware.bank.data.AccountData;
import com.vgsoftware.bank.user.UserHome;
import com.vgsoftware.bank.user.UserRemote;

public class CustomerBean implements SessionBean
{
	private UserRemote user=null;
	private UserHome userHome=null;
	private AccountHome accountHome=null;

	public void ejbCreate()
	{
		try
		{
			InitialContext jndiContext=new InitialContext();
			Object ref=jndiContext.lookup("java:comp/env/UserEJB");
			userHome=(UserHome)PortableRemoteObject.narrow(ref,UserHome.class);

			ref=jndiContext.lookup("java:comp/env/AccountEJB");
			accountHome=(AccountHome)PortableRemoteObject.narrow(ref,AccountHome.class);
		}
		catch(NamingException ne)
		{
			ne.printStackTrace(System.err);
		}
	}
	
	public String getName()
	{
		try
		{
			return(user.getName());
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		return("");
	}
	
	public Boolean login(String username, String password)
	{
		try
		{
			UserRemote remote=userHome.findByPrimaryKey(username);
			if((remote.validate(password))&&(!remote.isAdmin()))
			{
				user=remote;
				return(true);
			}
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(FinderException ce)
		{
			ce.printStackTrace(System.err);
		}
		return(false);
	}

	public Collection<AccountData> getAccounts()
	{
		List<AccountData> ad=new ArrayList<AccountData>();
		if(user!=null)
		{
			try
			{
				Collection<AccountRemote> accounts=accountHome.findByOwner(user.getName());
				for(AccountRemote ar : accounts)
					ad.add(new AccountData(ar.getNumber(),ar.getBalance()));
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
			catch(FinderException fe)
			{
				fe.printStackTrace(System.err);
			}
		}
		return(ad);
	}

	public Boolean transfer(Integer from, Integer to, Double amount)
	{
		if((user!=null)&&(from!=to))
		{
			try
			{
				AccountRemote accountFrom=accountHome.findByPrimaryKey(from);
				AccountRemote accountTo=accountHome.findByPrimaryKey(to);
				if((accountFrom.getUser().equals(user.getName()))&&(accountFrom.getBalance()>amount))
				{
					accountFrom.withdraw(amount);
					accountTo.deposit(amount);
					return(true);
				}
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
			catch(FinderException fe)
			{
				fe.printStackTrace(System.err);
			}
		}		
		return(false);
	}
	
	public Boolean deposit(Integer number, Double amount)
	{
		if(user!=null)
		{
			try
			{
				AccountRemote ar=accountHome.findByPrimaryKey(number);
				ar.deposit(amount);
				return(true);
			}
			catch(FinderException fe)
			{
			}
			catch(RemoteException re)
			{
			}
		}
		return(false);
	}

	public void ejbActivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbPassivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbRemove()
	throws EJBException, RemoteException
	{
	}

	public void setSessionContext(SessionContext ctx)
	throws EJBException, RemoteException
	{
	}
}
