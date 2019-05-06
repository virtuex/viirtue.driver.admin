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

    /**
     * 添加用户成绩，在客户端点击提交成绩后，就会把成绩请求发送到这个方法进行处理
     * @param score
     * @param errorStr
     * @param token
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/user/grade/add",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult addUserGrade(int score,String errorStr,String token){
        BizResult<Object> fail = new BizResult<>();
        Grade grade = new Grade();
        //根据用户传过来的token，从缓存中判断是否存在这个用户
        if(TokenCache.tokens==null||TokenCache.tokens.get(token)==null){
        //如果不存在就返回异常给APP
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        //如果存在，就从缓存中取出用户ID，结合传过来的成绩封装一条成绩记录
        long o = (long) TokenCache.tokens.get(token);
        grade.setErrorIds(errorStr);
        //把成绩保存到对象中
        grade.setUserGrade(score);
        //用户ID
        grade.setUserId(o);
        //考试时间，以2019-05-06 14:23:00的形式保存
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        grade.setDate(format.format(new Date()));
        //保存成绩
        gradeRepository.save(grade);
        BizResult<Object> success = BizResult.success();
        //200表示正确的响应
        success.setRetCode(200);
        return success;
    }


    /**
     * 这个方法是获取用户历史成绩的
     * @param token
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/api/grade/my",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult getUserGrade(String token){
        BizResult<Object> fail = new BizResult<>();

        Grade grade = new Grade();
        //1。根据token判断在缓存里是否有用户ID，如果没有，响应异常信息给客户端
        if(TokenCache.tokens==null||TokenCache.tokens.get(token)==null){
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        //根据Token找到用户ID
        long o = (long) TokenCache.tokens.get(token);
        //这里需要查询到用户信息拿出用户名用于展示在APP上
        DriverUser userByid = userRepository.findUserByid(o);
        //根据用户ID找到所有成绩
        List<Grade> grades = gradeRepository.findByUserId(o);
        //封装数据
        BizResult<Object> success = BizResult.success();
        success.setRetCode(200);
        success.setData(grades);
        success.setMessage(userByid.getUsername());
        return success;
    }
}
