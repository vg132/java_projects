package com.vgsoftware.appengine.bokstavsjakten;

public class Encryption
{
	public String encrypt(String text, int key)
	{
		String endResult = "";
		key = key * 7;
		for (char a : text.toCharArray())
		{
			for (int i = 0; i < key; i++)
			{
				if (!(a >= 123 || a < 31))
				{
					if (a + 1 != 123)
					{
						a += 1;
					}
					else
					{
						a = 32;
					}
				}
			}
			endResult += a;
		}
		return endResult;
	}

	public String decrypt(String cipherText, int key)
	{
		String endResult = "";
		key = key * 7;
		for (char a : cipherText.toCharArray())
		{
			for (int i = 0; i < key; i++)
			{
				if (!(a >= 123 || a < 31))
				{
					if (a - 1 != 31)
					{
						a -= 1;
					}
					else
					{
						a = 122;
					}
				}
				else
				{
					break;
				}
			}
			endResult += a;
		}
		return endResult;
	}
}
