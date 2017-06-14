package tk.ljyuan71.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
@Controller
public class IndexController {

    @GetMapping({"/", "index.html"})
    public String index() {
        return "index";
    }

}
