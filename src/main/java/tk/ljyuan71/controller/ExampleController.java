package tk.ljyuan71.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.ljyuan71.bean.Example;
import tk.ljyuan71.common.bean.Pager;
import tk.ljyuan71.common.bean.ResultPager;
import tk.ljyuan71.common.util.PagerUtil;
import tk.ljyuan71.service.ExampleService;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
@Controller
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("list.html")
    public String index() {
        return "example/list";
    }

    @ResponseBody
    @GetMapping("pager")
    public ResultPager<Example> pager(@RequestParam(value = "offset", defaultValue = "1") Integer offset, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Integer total = exampleService.countExample();
        Pager pager = PagerUtil.getPager(offset, limit, total);//开始页数,每页显示记录数据
        List<Example> examples = exampleService.examplePager(pager);
        return new ResultPager<>(pager.getTotalRecords(), examples);
    }

    @GetMapping("example/{id}")
    public String getEditExamplePage(Model model, @PathVariable("id") Integer id) {
        Example example = exampleService.getExample(id);
        model.addAttribute("example", example);
        return "example/edit";
    }

    @ResponseBody
    @PutMapping("example/{id}")
    public Map<String, String> updateExample(Example example, @PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("success", "true");
        Integer result = exampleService.updateExample(example);
        if (result > 0) {
            map.put("success", "true");
            map.put("message", "Successful operation");
        }
        return map;
    }

    @ResponseBody
    @DeleteMapping("example/{id}")
    public Map<String, String> removeExample(@PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        Integer result = exampleService.removeExample(id);
        if (result > 0) {
            map.put("success", "true");
            map.put("message", "successfully deleted");
        }
        return map;
    }
    
    @ResponseBody
    @RequestMapping("/setAndGetDataFromRedis")
    public Map<String, Object> setAndGetDataFromRedis(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	retMap.put("Data", exampleService.setAndGetDataFromRedis());
        return retMap;
    }

}
