package com.github.mkalin.jwsur2.ch5.predictions;

import java.util.concurrent.ConcurrentHashMap;

//In-memory data source simulating a persistent data store.
public class DataStore {
	private static final ConcurrentHashMap<String, String> map;

	static {
		map = new ConcurrentHashMap<String, String>();
		map.put("moe", "MoeMoeMoe");
		map.put("curly", "CurlyCurlyCurly");
		map.put("larry", "LarryLarryLarry");
	}

	public static String get(String key) {
		return map.get(key);
	}
}