package com.vgsoftware.android.blog;

import android.content.Context;

public class Utility
{
	private static Context _context;

	public static Context getContext()
	{
		return _context;
	}

	public static void setContext(Context context)
	{
		_context=context;
	}
}
