package tk.ljyuan71.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import tk.ljyuan71.bean.Example;
import tk.ljyuan71.common.bean.Pager;
import tk.ljyuan71.common.util.RedisDao;
import tk.ljyuan71.dao.ExampleDao;
import tk.ljyuan71.service.ExampleService;

/**
 * Created by LJYuan71 on 2017-6-13.
 *@Cacheable 支持如下几个参数：
 *value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
 *key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
 *condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
 *
 *@CacheEvict 支持如下几个参数：
 *value：缓存位置名称，不能为空，同上
 *key：缓存的key，默认为空，同上
 *condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
 *allEntries：true表示清除value中的全部缓存，默认为false
 */
@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExampleServiceImpl implements ExampleService {

    @Autowired
    private ExampleDao exampleDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisDao redisDao;

    @Override
    //这里将缓存的注解放置到exampleDao中去了,以便对参数更精确的控制
    //@Cacheable(value = "pagerExample", keyGenerator = "customKeyGenerator")
    public List<Example> examplePager(Pager pager) {
        return exampleDao.examplePager(pager);
    }

    @Override
    //@Cacheable(value = "getExample", keyGenerator = "customKeyGenerator")
    public Example getExample(Integer id) {
        return exampleDao.getExample(id);
    }

    @Override
    //@CacheEvict(value = {"saveExample", "countExample"}, allEntries = true)
    public Integer saveExample(Example example) {
        return exampleDao.insertExample(example);
    }

    @Override
    //@CacheEvict(value = {"pagerExample", "countExample", "getExample"}, allEntries = true)
    public Integer updateExample(Example example) {
        return exampleDao.updateExample(example);
    }

    @Override
    //@CacheEvict(value = {"pagerExample", "countExample", "getExample"}, allEntries = true)
    public Integer removeExample(Integer id) {
        return exampleDao.deleteExample(id);
    }

    @Override
    //@Cacheable(value = "countExample", keyGenerator = "customKeyGenerator")
    public Integer countExample() {
        return exampleDao.countExample();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Example> setAndGetDataFromRedis() {
		List<Example> examples = new ArrayList<Example>();
        //Redis String的操作 
		System.out.println("======================//redis String混合形式操作=======================");
		redisDao.setStringObj("StringLJY:object121", "这里是RedisTemplate<String, Object> String的操作");
		redisDao.setStringObj("StringLJY:object122", 10000000);
		redisDao.setString("StringLJY:string123", "使用StringRedisTemplate的操作value都是String类型");
		redisDao.setString("StringLJY:string124", "使用StringRedisTemplate的操作value都是String类型"+10000000);
		System.out.println("带类型的获取String的操作"+redisDao.get("StringLJY:object122"));
		System.out.println("获取String的操作"+redisDao.getStr("StringLJY:string123"));
		
		System.out.println("=========================//列表操作 ====================");
		for(int i=0;i<5;i++){
			Example example = makeExample();
			//examples.add(example);
			redisDao.leftPushListObj("exampleList:object1008611", example);
			redisDao.leftPushList("exampleList:stringname", example.getName());
		}
		examples = (List<Example>) redisDao.get("exampleList:object1008611");
		if(examples != null){
			for (Example example : examples) {
				System.out.println("RedisTemplate的list列表存取:"+example.toString());
			}
		}
		
		System.out.println("======================//Hash哈希表操作=======================");
		String key1 = "id";
		int value1 = 100000;
		String key2 = "name";
		String value2 = "刘一"+10000;
		redisDao.putHashObj("Hash11:object", key1, value1);//value带有类型
		redisDao.putHashObj("Hash11:object", key2, value2);//value带有类型
		redisDao.putHashObj("Hash11:object", "example", makeExample());//value带有类型
		redisDao.putHash("Hash11:string", key1, "stringRedisTemplate"+value1);//value为String
		redisDao.putHash("Hash11:string", key2, "stringRedisTemplate"+value2);//value为String
		Map<String, Object> map = (Map<String, Object>) redisDao.get("Hash11:object");
		if(map != null){
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println("RedisTemplate的哈希表存取:"+entry.getKey()+" value:"+entry.getValue());
			}
		}
		
		System.out.println("======================//Set操作========================");
		for(int i=0;i<5;i++){
			Example example = makeExample();
			redisDao.addSetObj("exampleSet:object1008611", example);
			redisDao.addSet("exampleSet:stringname", example.getName());
		}
		
		Set<Example> examples2 = (Set<Example>) redisDao.get("exampleSet:object1008611");
		if(examples2 != null){
			for (Example example : examples2) {
				System.out.println("RedisTemplate的哈希表存取:"+example.toString());
			}
		}
		
		System.out.println("======================以上均为个人封装的部分redis操作========================");
		System.out.println("======================以下为原始的RedisTemplate操作========================");
		redisDao.getTemplate().opsForValue().set("StringLJY:OrgnObject", "这里是原始的Template");
		redisDao.getTemplate("StringTemplate").opsForValue().set("StringLJY:OrgnString", "这里是原始的StringTemplate");
		
		return examples;
	}
	
	private static int id = 100000;
	
	public static Example makeExample(){
		Example example = new Example();
		example.setAge((int)Math.random()*20+10);
		example.setBirthday(new Date());
		example.setName("刘一"+id);
		example.setId(id++);
		example.setSex((int)Math.random()*3==1?"男":"女");
		return example;
	}
}
