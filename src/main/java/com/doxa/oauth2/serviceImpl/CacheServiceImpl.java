package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.services.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author vuducnoi
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
	private final CacheManager cacheManager;

	/**
	 * Clear cache by key
	 *
	 * @param cacheName CacheName
	 * @param key       Key
	 */
	@Override
	public void evictCache(String cacheName, String key) {
		try {
			log.info("Evict cache {}, key = {}", cacheName, key);
			Cache cache = cacheManager.getCache(cacheName);
			if (cache != null) {
				cache.evict(key);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
