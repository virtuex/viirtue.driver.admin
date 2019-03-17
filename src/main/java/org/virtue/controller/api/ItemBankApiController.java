package org.virtue.controller.api;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.virtue.basic.result.BizResult;
import org.virtue.bizexception.BizException;
import org.virtue.cache.TokenCache;
import org.virtue.config.ProConfig;
import org.virtue.constant.ItemType;
import org.virtue.constant.ResultCode;
import org.virtue.dao.CollectRepository;
import org.virtue.dao.GradeRepository;
import org.virtue.dao.ItemBankRepository;
import org.virtue.domain.Grade;
import org.virtue.domain.ItemBank;
import org.virtue.domain.MyCollect;
import org.virtue.utils.JsonFormatter;
import org.virtue.utils.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@Log4j2
public class ItemBankApiController {
    @Autowired
    ItemBankRepository itemBankRepository;
    @Autowired
    CollectRepository collectRepository;
    @Autowired
    GradeRepository gradeRepository;

    @ResponseBody
    @RequestMapping(value = "/api/item/random/all",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult itemBanksList(Integer choiceNum,Integer judgeNum){
        //从数据库查找选择题和判断题，需要随机
        List<ItemBank> choices = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.CHOICE.getId());
        List<ItemBank> judges = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.JUDGE.getId());
        Set<ItemBank> result = new HashSet<>();
        Random random = new Random();
        if(choices.size()<choiceNum){
            result.addAll(choices);
        }else{
            for(int i=0; i<choiceNum;i++){
                int i1 = random.nextInt(choices.size());
                result.add(choices.get(i1));
            }
        }
        //处理判断题
        if(judges.size()<judgeNum){
            result.addAll(judges);
        }else{
            for(int i=0; i<judgeNum;i++){
                int i1 = random.nextInt(judges.size());
                result.add(judges.get(i1));
            }
        }
        BizResult bizResultresult = BizResult.success();
        bizResultresult.setData(result);
        bizResultresult.setMessage("请求成功");
        bizResultresult.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:"+JsonFormatter.toPrettyJSON(result));
        if(result.size()==0){
            bizResultresult.setMessage("题库为空。");
        }
        return bizResultresult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/strong/all",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult strongitemBanksList(Integer difficult,Integer knowledge,Integer type){
        //从数据库查找选择题和判断题，需要随机
        List<ItemBank> itemDif = itemBankRepository.findItemBankByItemBankDifficutLevel(difficult);
        List<ItemBank> itemKnow = itemBankRepository.findItemBankByItemBankKownledgeType(knowledge);
        List<ItemBank> itemType = itemBankRepository.findItemBankByItemBankSubjectType(type);
        Set<ItemBank> items = new HashSet<>();
        itemDif.addAll(itemKnow);
        itemDif.addAll(itemType);
        Collections.shuffle(itemDif);
        if(itemDif.size()<20){
            items.addAll(itemDif);
        }else{
            for(int i=0;i<20;i++){
                items.add(itemDif.get(i));
            }
        }
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("成功");
        bizResult.setData(items);
        log.debug(items);
        return bizResult;
    }


    @ResponseBody
    @RequestMapping(value = "/api/item/collect",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult collectMyItem(Integer questionid,String token){
        List<MyCollect> byItemBankId = collectRepository.findByItemBankId(questionid);
        if(byItemBankId.size()>0){
            BizResult fail = new BizResult();
            fail.setRetCode(-1);
            fail.setMessage("您已收藏过，请勿重复！");
            return fail;
        }
        MyCollect collect = new MyCollect();
        BizResult fail = new BizResult();
        if(TokenCache.tokens==null||TokenCache.tokens.get(token)==null){
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        long o = (long) TokenCache.tokens.get(token);
        collect.setUserId(o);
        collect.setItemBankId(questionid);
        collectRepository.save(collect);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("收藏成功");
        return bizResult;
    }


    @ResponseBody
    @RequestMapping(value = "/api/item/all/random",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult allRandom(){
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        Collections.shuffle(all);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，随机出题");
        if(all.size()<=20){
            result.addAll(all);
        }else {
            for (int i = 0; i < 20; i++) {
                result.add(all.get(i));
            }
        }
        bizResult.setData(result);
        return bizResult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/not/do",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult notdo(String token){
        if(null == TokenCache.tokens.get(token)){
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        long userId = (long) TokenCache.tokens.get(token);
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        Collections.shuffle(all);
        //统计登录用户的错题
        List<Grade> grades = gradeRepository.findByUserId(userId);
        Set set = new HashSet();
        for(Grade grade:grades){
            String errorIds = grade.getErrorIds();
            if(errorIds.length()>1&&errorIds.contains("-")) {
                String substring = errorIds.substring(0, errorIds.length() - 1);
                String[] split = substring.split("-");
                for (String s : split) {
                    if (Pattern.matches("^[1-9]\\d*$", s)) {
                        set.add(Long.valueOf(s));
                    }
                }
            }

        }
        //如果错题数量多余所以题库，不可能的，就不处理
        if(set.size()>=all.size()){
            result = all.subList(0,19);
        }else if(all.size()<21){
            result.addAll(all);
        }else{
            //错题小于20
            if(set.size()<ProConfig.examSize){
                for(ItemBank itemBank:all){
                    if(set.contains(itemBank.getItemBankId())){
                        result.add(itemBank);
                    }
                }
                int examSize = result.size();
                int notEnought = ProConfig.examSize-examSize;
                if(notEnought>0){
                    result.addAll(all.subList(0,notEnought-1));
                }
            }
        }
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，优先做未做的");

        bizResult.setData(result);
        return bizResult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/real/all",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult examReal(){
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        Collections.shuffle(all);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，全真模拟");
        if(all.size()<=20){
            result.addAll(all);
        }else {
            for (int i = 0; i < 20; i++) {
                result.add(all.get(i));
            }
        }
        bizResult.setData(result);
        return bizResult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/my/error",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult getErrorItems(String token){
        if(null == TokenCache.tokens.get(token)){
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        long userId = (long) TokenCache.tokens.get(token);
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        Collections.shuffle(all);
        //统计登录用户的错题
        List<Grade> grades = gradeRepository.findByUserId(userId);
        Set<Long> set = new HashSet();
        for(Grade grade:grades){
            String errorIds = grade.getErrorIds();
            if(errorIds.length()>1&&errorIds.contains("-")) {
                String substring = errorIds.substring(0, errorIds.length() - 1);
                String[] split = substring.split("-");
                for (String s : split) {
                    if (Pattern.matches("^[1-9]\\d*$", s)) {
                        set.add(Long.valueOf(s));
                    }
                }
            }
        }
        //set中保存的是用户的错题
        for(Long i:set){
            result.add(itemBankRepository.findItemBankByItemBankId(i));
        }
        BizResult myResult = new BizResult();
        myResult.setMessage("获取成功");
        myResult.setData(result);
        myResult.setRetCode(200);
        return myResult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/my/collect",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult getMyCollect(String token){
        if(null == TokenCache.tokens.get(token)){
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        long userId = (long) TokenCache.tokens.get(token);
        List<MyCollect> collects = collectRepository.findByUserId(userId);
        Set<Long> itemIdSet = new HashSet<>();
        List<ItemBank> result = new ArrayList<>();
        for(MyCollect collect:collects){
            //添加到Set中
            itemIdSet.add(collect.getItemBankId());
        }
        for(Long itemId:itemIdSet){
            result.add(itemBankRepository.findItemBankByItemBankId(itemId));
        }
        BizResult myResult = new BizResult();
        myResult.setMessage("获取成功");
        myResult.setData(result);
        myResult.setRetCode(200);
        return myResult;
    }


    @ResponseBody
    @RequestMapping(value = "/api/item/collect/remove",method = {RequestMethod.GET,RequestMethod.POST})
    public BizResult removeMyCollect(String token,long questionid){
        if(null == TokenCache.tokens.get(token)){
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        long userId = (long) TokenCache.tokens.get(token);
        List<MyCollect> byUserIdAndItemBankId = collectRepository.findByUserIdAndItemBankId(userId, questionid);
        if(byUserIdAndItemBankId==null||byUserIdAndItemBankId.size()<1){
           BizResult bizResult = new BizResult();
           bizResult.setRetCode(-1);
           bizResult.setMessage("服务端处理失败，请稍后再试");
           return  bizResult;
        }
        MyCollect collect = byUserIdAndItemBankId.get(0);
        long collectId = collect.getCollectId();
        collectRepository.deleteById(collectId);
        BizResult myResult =new BizResult();
        myResult.setMessage("移除成功");
        myResult.setRetCode(200);
        return myResult;
    }
}
