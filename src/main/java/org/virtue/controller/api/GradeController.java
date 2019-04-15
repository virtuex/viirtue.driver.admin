package org.virtue.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.virtue.basic.result.BizResult;
import org.virtue.cache.TokenCache;
import org.virtue.dao.GradeRepository;
import org.virtue.dao.UserRepository;
import org.virtue.domain.DriverUser;
import org.virtue.domain.Grade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GradeController {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/user/grade/add",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult addUserGrade(int score,String errorStr,String token){
        BizResult<Object> fail = new BizResult<>();
        Grade grade = new Grade();
        if(TokenCache.tokens==null||TokenCache.tokens.get(token)==null){
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        long o = (long) TokenCache.tokens.get(token);
        grade.setErrorIds(errorStr);
        grade.setUserGrade(score);
        grade.setUserId(o);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        grade.setDate(format.format(new Date()));
        gradeRepository.save(grade);
        BizResult<Object> success = BizResult.success();
        success.setRetCode(200);
        return success;
    }



    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/api/grade/my",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult getUserGrade(String token){
        BizResult<Object> fail = new BizResult<>();

        Grade grade = new Grade();
        if(TokenCache.tokens==null||TokenCache.tokens.get(token)==null){
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        long o = (long) TokenCache.tokens.get(token);
        DriverUser userByid = userRepository.findUserByid(o);
        List<Grade> grades = gradeRepository.findByUserId(o);
        BizResult<Object> success = BizResult.success();
        success.setRetCode(200);
        success.setData(grades);
        success.setMessage(userByid.getUsername());
        return success;
    }
}
