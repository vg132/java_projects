package com.vgsoftware.appengine.slrealtime;

import java.util.Collections;
import java.util.logging.Logger;

import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

public class Cache
{
	private static final Logger _logger = Logger.getLogger(Cache.class.getName());
	private static net.sf.jsr107cache.Cache _cache = null;

	static
	{
		try
		{
			_cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		}
		catch (CacheException ex)
		{
			_logger.severe(ex.getMessage());
		}
	}

	public static synchronized void put(Object key, Object value)
	{
		_cache.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public static synchronized <T> T get(Object key)
	{
		return (T) _cache.get(key);
	}

	public static synchronized void remove(Object key)
	{
		_cache.remove(key);
	}
}
