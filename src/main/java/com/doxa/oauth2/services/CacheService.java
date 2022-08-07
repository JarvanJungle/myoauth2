package com.doxa.oauth2.services;

/**
 * @author vuducnoi
 */
public interface CacheService {
	/**
	 * Clear cache by key
	 *
	 * @param cache CacheName
	 * @param key   Key
	 */
	void evictCache(String cache, String key);
}