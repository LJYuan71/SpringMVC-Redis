package tk.ljyuan71.common.util;

import java.util.Collections;

import redis.clients.jedis.Jedis;

/**
 * Created by LJYuan71 on 2018-11-22.
 * 
 * Redis分布式锁
 *  基本特性：
 *      1、高性能（加、解锁高性能）
 *      2、互斥性，在任意时刻，只有一个客户端持有锁
 *      3、不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁
 *      4、具有容错性,具有大部分的redis正常节点运行，客户端没有加锁和解锁
 *      5、解铃还需要系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人的锁给解了
 *  实现原理：
 *      Redis set key 时的一个 NX 参数可以保证在这个 key 不存在的情况下写入成功；
 *      并且再加上 EX 参数可以让该 key 在超时之后自动删除；
 *      所以可以保证在同一时刻只会有一个进程获得锁，并且不会出现死锁（最坏的情况为即为超时后自动删除）。
 *
 */
public class RedisLock {
	
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";//只在键不存在的时候设置键
    private static final String SET_WITH_EXPIRE_TIME = "PX";//设置指定到期时间（毫秒为单位)
    private static final String LOCK_PREFIX = "LJYuan71Lock_";//锁前缀,一个项目一个前缀

    private static final int TIME = 1000;//默认失效时间
    
    
    /**
     * 获取分布式锁
     * @param lockKey
     * @param requstId  存储的值
     * @param expireTime  超时时间 小于0时使用默认的值
     * @param jedis 建议JedisPool.getResource()  JedisConnectionFactory.fetchJedisConnector()
     * @return 是否成功
     */
	public static boolean lock(Jedis jedis, String lockKey,
			String requstId, int expireTime) {
		String result = null;
		if (expireTime <= 0) expireTime = TIME;
		result = jedis.set(LOCK_PREFIX + lockKey, requstId,
				SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
		
		return LOCK_SUCCESS.equals(result);
	}
    
	/**
	 * 释放分布式锁
	 * @param lockKey
	 * @param requstId
	 * @return 是否成功
	 */
	public boolean unlock(Jedis jedis, String lockKey, String requstId) {
		//Lua代码
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(LOCK_PREFIX+lockKey),
				Collections.singletonList(requstId));
		
		return RELEASE_SUCCESS.equals(result);
	}
    
    

}
