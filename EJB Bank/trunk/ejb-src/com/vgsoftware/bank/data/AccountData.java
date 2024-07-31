package com.vgsoftware.bank.data;

import java.io.Serializable;

public class AccountData implements Serializable
{
	private int number=-1;
	private double balance=0.0;

	public AccountData()
	{
	}
	
	public AccountData(int number, double balance)
	{
		this.number=number;
		this.balance=balance;
	}
	
	public double getBalance()
	{
		return(balance);
	}

	public void setBalance(double balance)
	{
		this.balance=balance;
	}

	public int getNumber()
	{
		return(number);
	}

	public void setNumber(int number)
	{
		this.number=number;
	}
}
