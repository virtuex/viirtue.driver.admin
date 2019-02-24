package org.virtue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.virtue.Resource;
@Controller
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return Resource.WEB_URL_INDEX_HTML;
    }
}
