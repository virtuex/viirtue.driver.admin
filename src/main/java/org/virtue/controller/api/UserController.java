package org.virtue.controller.api;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.virtue.basic.exception.BasicErrorCode;
import org.virtue.basic.exception.BasicException;
import org.virtue.basic.result.BizResult;
import org.virtue.cache.TokenCache;
import org.virtue.constant.DigestAlgEnum;
import org.virtue.dao.UserRepository;
import org.virtue.domain.DriverUser;
import org.virtue.utils.Base64Coder;
import org.virtue.utils.MessageDigestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/user/login", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult doUserLogin(String username, String password) throws Exception {
        BizResult result = null;
        //判断用户是否存在
        MessageDigestFactory md5Pass = MessageDigestFactory.getInstance(DigestAlgEnum.MD5.getDigAlgName());
        List<DriverUser> users = userRepository.findUserByUsernameAndPassword(username, Base64Coder.encodeString(password));
        //存在
        if (users.size() > 0) {
            result = BizResult.success();
            log.debug("用户{}登录成功", username);
            result.setMessage("成功");
            String token = UUID.randomUUID().toString();
            //保存TOKEN
            TokenCache.tokens.put(token, users.get(0).getId());
            //map中yi用户名为key，uuid为token,并将token返回给客户端
            Map<String, Object> myToken = new HashMap<>();
            myToken.put("token", token);
            result.setData(myToken);
        } else {
            //不存在
            result = BizResult.error(new BasicException(BasicErrorCode.BIZ_UNKOWN));
        }
        return result;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/user/register", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult userAdd(DriverUser user) throws Exception {
        String password = user.getPassword();
        String s = Base64Coder.encodeString(password);
        user.setPassword(s);
        userRepository.save(user);
        BizResult success = BizResult.success();
        return success;
    }
}
