package com.caffeinepoc.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

class CacheInfo {
	private final String name;
	private final int size;
	private final Set<Object> keys;
	private final String stats;

	public CacheInfo(String name, int size, Set<Object> keys, String stats) {
		this.name = name;
		this.size = size;
		this.keys = keys;
		this.stats = stats;
	}

	public String getName() { return name; }
	public int getSize() { return size; }
	public Set<Object> getKeys() { return keys; }
	public String getStats() { return stats; }
}

@RestController
@RequestMapping("admin/cache")
public class CacheController {
	private CacheManager cacheManager;

	public CacheController(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<CacheInfo> getCacheInfo() {
		return cacheManager.getCacheNames().stream().map(this::getCacheInfo).collect(Collectors.toList());
	}

	@GetMapping(path = "/evict/{cachename}", produces = TEXT_PLAIN_VALUE)
	public String evictCache(@PathVariable String cachename) {
		String cache = cacheManager.getCacheNames().stream().filter(name -> name.equals(cachename)).findFirst()
			.orElseThrow(() -> new RuntimeException("Cache was not found."));

		cacheManager.getCache(cachename).clear();
		return String.format("Cache source %s has been cleared.", cachename);
	}

	@GetMapping(path = "/evict", produces = TEXT_PLAIN_VALUE)
	public String evictAllCaches() {
		cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
		return "All Cache sources have been cleared.";
	}

	private CacheInfo getCacheInfo(String cacheName) {
		Cache<Object, Object> nativeCache = (Cache) cacheManager.getCache(cacheName).getNativeCache();
		Set<Object> keys = nativeCache.asMap().keySet();
		CacheStats stats = nativeCache.stats();
		return new CacheInfo(cacheName, keys.size(), keys, stats.toString());
	}
}
