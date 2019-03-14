package tk.ljyuan71.common.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    private volatile JedisConnectionFactory jedisConnectionFactory;
    private volatile RedisTemplate<String, String> redisTemplate;
    private volatile RedisCacheManager redisCacheManager;

    public RedisCacheConfig() {
        super();
    }

    public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String,String> redisTemplate, RedisCacheManager redisCacheManager) {
        super();
        this.jedisConnectionFactory = jedisConnectionFactory;
        this.redisTemplate = redisTemplate;
        this.redisCacheManager = redisCacheManager;
    }

    public JedisConnectionFactory redisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        return redisTemplate;
    }

    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return redisCacheManager;
    }

    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                //sb.append(o.getClass().getName());
                String declaringClass = method.getDeclaringClass().toString();
                sb.append(declaringClass.split(" ")[1]);
                sb.append("."+method.getName());
                for (int i = 0; i < objects.length; i++) {
					if(i == 0){
						sb.append(":");
					}
					 sb.append(objects[i].toString());
				}
                return sb.toString();
            }
        };
    }
}
