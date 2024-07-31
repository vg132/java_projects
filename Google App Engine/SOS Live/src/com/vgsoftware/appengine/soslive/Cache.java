package com.vgsoftware.appengine.soslive;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Cache
{
	private static final Logger _logger = Logger.getLogger(Cache.class.getName());
	private static net.sf.jsr107cache.Cache _cache = null;

	static
	{
		try
		{
			HashMap props = new HashMap();
			props.put(GCacheFactory.EXPIRATION_DELTA, 300);
			_cache = CacheManager.getInstance().getCacheFactory().createCache(props);
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

	public static synchronized <T> T get(Object key)
	{
		return (T) _cache.get(key);
	}

	public static synchronized void remove(Object key)
	{
		_cache.remove(key);
	}
}
