package tk.ljyuan71.service;

import org.apache.ibatis.annotations.Param;

import tk.ljyuan71.bean.Example;
import tk.ljyuan71.common.bean.Pager;

import java.util.List;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
public interface ExampleService {

    List<Example> examplePager(@Param("pager")Pager pager);

    Example getExample(Integer id);

    Integer saveExample(Example example);

    Integer updateExample(Example example);

    Integer removeExample(Integer id);

    Integer countExample();
    
    public List<Example> setAndGetDataFromRedis();

}
