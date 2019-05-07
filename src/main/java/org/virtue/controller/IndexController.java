package org.virtue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.virtue.Resource;
@Controller
public class IndexController {
    /**
     * 后台管理首页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        //首页的HTML名称
        return Resource.WEB_URL_INDEX_HTML;
    }
}
