package com.ressseat.jedis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static JedisPool pool = null;
	
	private JedisPoolUtil() {}
	
	public static JedisPool getJedisPool() {
		if (pool == null) {
			//針對類別做syn
			synchronized (JedisPoolUtil.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(30);
					config.setMaxIdle(8);
					config.setMaxWaitMillis(10000);
					pool = new JedisPool(config, "localhost", 6379);
				}
			}
		}
		return pool;
	}
	
	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}

}
