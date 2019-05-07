package org.virtue.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.virtue.basic.exception.BasicErrorCode;
import org.virtue.basic.exception.BasicException;
import org.virtue.basic.result.BizResult;
import org.virtue.cache.TokenCache;
import org.virtue.config.ProConfig;
import org.virtue.constant.DigestAlgEnum;
import org.virtue.constant.ResultCode;
import org.virtue.dao.UserRepository;
import org.virtue.domain.DriverUser;
import org.virtue.domain.ItemBank;
import org.virtue.utils.Base64Coder;
import org.virtue.utils.JsonFormatter;
import org.virtue.utils.MessageDigestFactory;

import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserWebController {
    private Logger log = LoggerFactory.getLogger(ItemBankController.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * 这个是用来进入用户列表的HTML页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/admin/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String doUserLogin() throws Exception {
        return "pages/UI/user_list";
    }


    /**
     * 浏览器发送请求到服务端，服务端根据传过来的页数返回当前页的数据
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/admin/list/{page}", method = {RequestMethod.GET})
    public BizResult userList(@PathVariable Integer page) {
        //这里和ItemBankController类中的题目列表相同
        int start = (page - 1) * ProConfig.pageSize;
        int end = start + ProConfig.pageSize - 1;
        List<DriverUser> all = userRepository.findAll();
        List<DriverUser> target;
        if (all.size() > ProConfig.pageSize) {
            target = all.subList(start, end);
        } else {
            target = all;
        }
        BizResult result = BizResult.success();
        result.setData(target);
        result.setMessage("请求成功");
        result.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:" + JsonFormatter.toPrettyJSON(result));
        return result;
    }


    /**
     * 删除用户，浏览器发送用户Id,后台根据ID删除掉用户
     * @param itemBankId
     * @return
     */
    @RequestMapping(value = "/user-delete", method = {RequestMethod.GET})
    public String userDelete(long itemBankId) {
        userRepository.deleteById(itemBankId);
        return "pages/UI/user_list";
    }


    public static void main(String[] args) throws Exception {
        MessageDigestFactory md5Pass = MessageDigestFactory.getInstance(DigestAlgEnum.MD5.getDigAlgName());
        System.out.println(md5Pass.digest2Base64("admin"));
    }
}
