package tk.ljyuan71.common.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository  
public class RedisDao{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 获得原始Template
	 */
	public RedisTemplate<String, Object> getTemplate(){
		return this.redisTemplate;
	}
	
	/**
	 * 获得原始StringRedisTemplate
	 */
	public StringRedisTemplate getTemplate(String stringTemplate){
		return this.stringRedisTemplate;
	}
	
	/**
	 * String添加Object记录
	 */
	public boolean setStringObj(String key, Object value) {  
        try {  
        	redisTemplate.opsForValue().set(key, value); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());  
            return false;  
        }  
    } 
	
	/**
	 * String添加Object记录timeout后过期
	 */
	public boolean setStringObj(String key, Object value,Long timeout) {  
        try {  
        	redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());  
            return false;  
        }  
    }
	/**
	 * String添加记录
	 */
	public boolean setString(String key, String value) {  
        try {  
        	stringRedisTemplate.opsForValue().set(key, value); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());  
            return false;  
        }  
    } 
	
	/**
	 * String添加记录timeout后过期
	 */
	public boolean setString(String key, String value,Long timeout) {  
        try {  
        	stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());  
            return false;  
        }  
    }
	/**
	 * opsForList 压栈添加Object记录
	 */
	public boolean leftPushListObj(String key, Object value) {  
        try {  
        	redisTemplate.opsForList().leftPush(key, value); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());
            return false;  
        }  
    } 
	/**
	 * opsForList 压栈添加记录
	 */
	public boolean leftPushList(String key, String value) {  
        try {  
        	stringRedisTemplate.opsForList().leftPush(key, value); 
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());
            return false;  
        }  
    } 
	/**
	 * opsForHash添加Object记录
	 * @param key redis指定redis的key后期根据此key来获取map
	 * @param sonKey map-key
	 * @param value  map-value
	 * @return
	 */
	public boolean putHashObj(String key, String sonKey ,Object value) {
        try {  
        	redisTemplate.opsForHash().put(key, sonKey, value);     
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage()); 
            return false;  
        }  
    }
	
	/**
	 * opsForHash添加记录
	 * @param key redis指定redis的key后期根据此key来获取map
	 * @param sonKey map-key
	 * @param value  map-value
	 * @return
	 */
	public boolean putHash(String key, String sonKey ,String value) {
        try {  
        	stringRedisTemplate.opsForHash().put(key, sonKey, value);     
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage()); 
            return false;  
        }  
    }
	/**
	 * opsForSet添加Object记录
	 */
	public boolean addSetObj(String key, Object value) {
        try {  
        	redisTemplate.opsForSet().add(key, value);     
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());
            return false;  
        }  
    }
	/**
	 * opsForSet添加记录
	 */
	public boolean addSet(String key, String value) {
        try {  
        	stringRedisTemplate.opsForSet().add(key, value);     
            return true;  
        } catch (Exception e) {  
        	logger.info("新增错误:{}", e.getMessage());
            return false;  
        }  
    }
	/**
	 * 获取对应key的值,没有则返回""
	 */
	public Object get(String key){  
        try {  
			DataType type = redisTemplate.type(key);  
             if(DataType.NONE == type){  
                 System.out.println(key+"key不存在");  
                 return null;  
             }else if(DataType.STRING == type){  
                 return redisTemplate.opsForValue().get(key);  
             }else if(DataType.LIST == type){  
                 return redisTemplate.opsForList().range(key, 0, -1);  
             }else if(DataType.HASH == type){  
                 return redisTemplate.opsForHash().entries(key);  
             }else  
                 return null;  
        } catch (Exception e) {  
        	logger.info("查询错误:{}", e.getMessage());
            return null;  
        }  
    } 
	/**
	 * 获取对应key的值,没有则返回""
	 */
	public Object getStr(String key){  
        try {  
			DataType type = stringRedisTemplate.type(key);  
             if(DataType.NONE == type){  
                 System.out.println(key+"key不存在");  
                 return null;  
             }else if(DataType.STRING == type){  
                 return stringRedisTemplate.opsForValue().get(key);  
             }else if(DataType.LIST == type){  
                 return stringRedisTemplate.opsForList().range(key, 0, -1);  
             }else if(DataType.HASH == type){  
                 return stringRedisTemplate.opsForHash().entries(key);  
             }else  
                 return null;  
        } catch (Exception e) {  
        	logger.info("查询错误:{}", e.getMessage());
            return null;  
        }  
    }
	/**
	 * 批量删除对应key的值
	 */
	public boolean delete(List<String> keys){  
        try{  
        	redisTemplate.delete(keys);  
            return true;  
        }catch(Exception e){  
        	logger.info("删除失败:{}", e.getMessage());
            return false;  
        }  
    }  
	
	/**
	 * 单个删除对应的value 
	 */
	public void delete(final String key) { 
	  if (exists(key)) { 
		  redisTemplate.delete(key); 
	  } 
	}
	
	/**
	 * 判断缓存中是否有对应的value 
	 */
	public boolean exists(final String key) { 
	  return redisTemplate.hasKey(key); 
	}
	
	

}
