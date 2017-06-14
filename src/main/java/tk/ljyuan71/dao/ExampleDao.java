package tk.ljyuan71.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.ljyuan71.bean.Example;
import tk.ljyuan71.common.bean.Pager;

import java.util.List;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface ExampleDao {
	
	@Cacheable(value = "pagerExample", keyGenerator = "customKeyGenerator")
    List<Example> examplePager(@Param("pager") Pager pager);
	
	@Cacheable(value = "getExample", keyGenerator = "customKeyGenerator")
    Example getExample(Integer id);
	
	@CacheEvict(value = {"saveExample", "countExample"}, allEntries = true)
    Integer insertExample(Example example);

	@CacheEvict(value = {"pagerExample", "countExample", "getExample"}, allEntries = true)
    Integer updateExample(Example example);

	@CacheEvict(value = {"pagerExample", "countExample", "getExample"}, allEntries = true)
    Integer deleteExample(Integer id);
	
	@Cacheable(value = "countExample", keyGenerator = "customKeyGenerator")
    Integer countExample();

}
