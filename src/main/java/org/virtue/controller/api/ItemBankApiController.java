package org.virtue.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.virtue.basic.result.BizResult;
import org.virtue.cache.TokenCache;
import org.virtue.config.ProConfig;
import org.virtue.constant.ItemType;
import org.virtue.constant.ResultCode;
import org.virtue.controller.ItemBankController;
import org.virtue.dao.CollectRepository;
import org.virtue.dao.GradeRepository;
import org.virtue.dao.ItemBankRepository;
import org.virtue.domain.Grade;
import org.virtue.domain.ItemBank;
import org.virtue.domain.MyCollect;
import org.virtue.utils.JsonFormatter;

import java.util.*;
import java.util.regex.Pattern;

@Controller
public class ItemBankApiController {
    private Logger log = LoggerFactory.getLogger(ItemBankController.class);
    @Autowired
    ItemBankRepository itemBankRepository;
    @Autowired
    CollectRepository collectRepository;
    @Autowired
    GradeRepository gradeRepository;

    /**
     * 随机练习，App发送选择题和判断题的数量，服务端根据传过来的数量选取相应的题目并返回
     *
     * @param choiceNum
     * @param judgeNum
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/random/all", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult itemBanksList(Integer choiceNum, Integer judgeNum) {
        //从数据库查找选择题和判断题，分别存在两个list钟
        List<ItemBank> choices = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.CHOICE.getId());
        List<ItemBank> judges = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.JUDGE.getId());
        //这个set用来存储响应给APP的题目列表
        Set<ItemBank> result = new HashSet<>();
        Random random = new Random();
        //判断数据库里的选择题的数量和要求的数量对比
        //如果app请求的数量比数据库中的选择数量还多，那就把数据库中的所有选择题响应回去
        if (choices.size() < choiceNum) {
            result.addAll(choices);
        } else {
            //如果数据库里的题目足够，那就随机数，从数据库里的选择题中根据随机数作为下标取出来
            for (int i = 0; i < choiceNum; i++) {
                int i1 = random.nextInt(choices.size());
                result.add(choices.get(i1));
            }
        }
        //处理判断题
        //判断题的逻辑和选择题的逻辑一样
        if (judges.size() < judgeNum) {
            result.addAll(judges);
        } else {
            for (int i = 0; i < judgeNum; i++) {
                int i1 = random.nextInt(judges.size());
                result.add(judges.get(i1));
            }
        }
        BizResult bizResultresult = BizResult.success();
        //吧题目放到响应里面返回给客户端
        bizResultresult.setData(result);
        bizResultresult.setMessage("请求成功");
        bizResultresult.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:" + JsonFormatter.toPrettyJSON(result));
        if (result.size() == 0) {
            bizResultresult.setMessage("题库为空。");
        }
        return bizResultresult;
    }

    @ResponseBody
    @RequestMapping(value = "/api/item/strong/all", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult strongitemBanksList(Integer difficult, Integer knowledge, Integer type) {
        //从数据库查找选择题和判断题，需要随机
        List<ItemBank> itemDif = itemBankRepository.findItemBankByItemBankDifficutLevel(difficult);
        List<ItemBank> itemKnow = itemBankRepository.findItemBankByItemBankKownledgeType(knowledge);
        List<ItemBank> itemType = itemBankRepository.findItemBankByItemBankSubjectType(type);
        //强化练习的处理逻辑比较简单，先从数据库里根据APP传过来的条件，从数据库里查询所有满足条件的数据
        //比如传过来的条件时，难度：简单，知识点：信号灯，类型：选择题
        //那根据这三个条件分别查询，并把所有的结果放到一个List中
        //Collects这个工具类提供了一个shuffle()方法，能够随机打乱list中的数据，所以这里调用这个方法
        //Collections.shuffle(itemDif);吧虽有的题目顺序打乱，然后再读取前100道，就符合要求了。
        Set<ItemBank> items = new HashSet<>();
        itemDif.addAll(itemKnow);
        itemDif.addAll(itemType);
        //打乱顺序
        Collections.shuffle(itemDif);
        //先判断下，如果查询的结果都不够100，那就全部返回
        if (itemDif.size() < 100) {
            items.addAll(itemDif);
        } else {
            //如果查询的结果大于100，那就只取前100道题目
            for (int i = 0; i < 100; i++) {
                items.add(itemDif.get(i));
            }
        }
        //吧查询到的结果塞到响应中
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("成功");
        bizResult.setData(items);
        log.debug(items.toString());
        return bizResult;
    }


    /**
     * 这个是用来收藏题目的方法，客户端传过来两个参数，questionid题目Id和Token，token是用户凭证
     *
     * @param questionid
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/collect", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult collectMyItem(Integer questionid, String token) {
        //

        //第一步，先判断是否已经收藏过了，如果收藏过了的话，就返回个：“已经收藏”给客户端
        List<MyCollect> byItemBankId = collectRepository.findByItemBankId(questionid);
        if (byItemBankId.size() > 0) {
            BizResult fail = new BizResult();
            fail.setRetCode(-1);
            fail.setMessage("您已收藏过，请勿重复！");
            return fail;
        }

        MyCollect collect = new MyCollect();
        BizResult fail = new BizResult();
        //如果没有收藏过，需要取出用户ID
        if (TokenCache.tokens == null || TokenCache.tokens.get(token) == null) {
            fail.setMessage("会话已过期");
            fail.setRetCode(-1);
            return fail;
        }
        long o = (long) TokenCache.tokens.get(token);
        //根据传过来的题目ID和用户ID封装成一条收藏记录，并保存到收藏表中
        collect.setUserId(o);
        collect.setItemBankId(questionid);
        collectRepository.save(collect);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("收藏成功");
        return bizResult;
    }

    /**
     * 随机出题，随机出题比较简单，把所有的题目取出来，然后把顺序打乱，在取前100道就可以了
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/all/random", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult allRandom() {
        //1.查询所有数据
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        //2.打乱顺序
        Collections.shuffle(all);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，随机出题");
        //取前100道题，仍然要先判断，如果数据库中的题目都不够100，那就把数据库中的饿题目全部返回
        if (all.size() <= 100) {
            result.addAll(all);
        } else {
            //如果够100道，那就取前100道
            for (int i = 0; i < 100; i++) {
                result.add(all.get(i));
            }
        }
        bizResult.setData(result);
        return bizResult;
    }


    /**
     * 优先未做题，
     *
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/not/do", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult notdo(String token) {
        //根据传过来的用户Token，先从缓存中根据token查找用户ID，从而判断用户是否已经会话过期
        if (null == TokenCache.tokens.get(token)) {
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        //如果缓存中存在用户
        long userId = (long) TokenCache.tokens.get(token);
        //查询所有题目
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        //把顺序打乱
        Collections.shuffle(all);
        //统计登录用户的错题
        List<Grade> grades = gradeRepository.findByUserId(userId);
        Set set = new HashSet();
        for (Grade grade : grades) {
            String errorIds = grade.getErrorIds();
            if (errorIds.length() > 1 && errorIds.contains("-")) {
                String substring = errorIds.substring(0, errorIds.length() - 1);
                String[] split = substring.split("-");
                for (String s : split) {
                    if (Pattern.matches("^[1-9]\\d*$", s)) {
                        set.add(Long.valueOf(s));
                    }
                }
            }

        }
        //这里需要把错题从所有题目中剔除。
        //这里需要注意，如果排除错题数量剩余的不够一张试卷的题目，那就只能从错题里面取出题目，直至够一张试卷的数量了
        if (set.size() >= all.size()) {
            result = all.subList(0, 99);
        } else if (all.size() < 101) {
            result.addAll(all);
        } else {
            //错题小于100
            if (set.size() < ProConfig.examSize) {
                for (ItemBank itemBank : all) {
                    if (set.contains(itemBank.getItemBankId())) {
                        result.add(itemBank);
                    }
                }
                int examSize = result.size();
                int notEnought = ProConfig.examSize - examSize;
                if (notEnought > 0) {
                    result.addAll(all.subList(0, notEnought - 1));
                }
            }
        }
        //吧题目响应里
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，优先做未做的");

        bizResult.setData(result);
        return bizResult;
    }


    /**
     * 全真模拟
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/real/all/exam", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult allRealExam() {
        //分别取出选择题和判断题，并分别把他们顺序打乱
        List<ItemBank> choice = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.CHOICE.getId());
        List<ItemBank> judge = itemBankRepository.findItemBankByItemBankSubjectType(ItemType.JUDGE.getId());
        List<ItemBank> result = new ArrayList<>();
        //吧数据全部打乱
        Collections.shuffle(choice);
        Collections.shuffle(judge);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，全真模拟");
        //然后取出100道题
        //取出50道判断题
        for (int i = 0; i < 50; i++) {
            result.add(choice.get(i));
        }
        //取出50道选择题
        for (int i = 0; i < 50; i++) {
            result.add(judge.get(i));
        }
        //把题目返回给前端
        bizResult.setData(result);
        return bizResult;
    }

    /**
     * 这个暂时有点毛病
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/real/all", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult examReal() {
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        //吧数据全部打乱
        Collections.shuffle(all);
        BizResult bizResult = new BizResult();
        bizResult.setRetCode(200);
        bizResult.setMessage("获取题库成功，全真模拟");
        //然后取出100道题
        if (all.size() <= 100) {
            result.addAll(all);
        } else {
            for (int i = 0; i < 100; i++) {
                result.add(all.get(i));
            }
        }
        bizResult.setData(result);
        return bizResult;
    }

    /**
     * 我的错题
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/my/error", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult getErrorItems(String token) {
        //根据传过来的用户Token判断缓存中是否有这个用户
        //如果没有，会话已经过期
        if (null == TokenCache.tokens.get(token)) {
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        //如果有，就根用户Token取出用户ID
        long userId = (long) TokenCache.tokens.get(token);
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> result = new ArrayList<>();
        Collections.shuffle(all);
        //根据用户ID，从错题中取出所有的错题ID，统计登录用户的错题
        List<Grade> grades = gradeRepository.findByUserId(userId);
        Set<Long> set = new HashSet();
        for (Grade grade : grades) {
            String errorIds = grade.getErrorIds();
            if (errorIds.length() > 1 && errorIds.contains("-")) {
                String substring = errorIds.substring(0, errorIds.length() - 1);
                String[] split = substring.split("-");
                for (String s : split) {
                    if (Pattern.matches("^[1-9]\\d*$", s)) {
                        set.add(Long.valueOf(s));
                    }
                }
            }
        }
        //set中保存的是用户的错题，并将其返回给客户端APP
        for (Long i : set) {
            result.add(itemBankRepository.findItemBankByItemBankId(i));
        }
        BizResult myResult = new BizResult();
        myResult.setMessage("获取成功");
        myResult.setData(result);
        myResult.setRetCode(200);
        return myResult;
    }

    /**
     * 我的收藏
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/my/collect", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult getMyCollect(String token) {
        //根据传过来的TOken判断会话是否过期，如果过期了，就响应异常给客户端
        if (null == TokenCache.tokens.get(token)) {
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        //如果没有过期，就从缓存中取出用户的ID
        long userId = (long) TokenCache.tokens.get(token);
        //从收藏表里，根据用户的ID，取出该用户的收藏题目的ID
        List<MyCollect> collects = collectRepository.findByUserId(userId);
        Set<Long> itemIdSet = new HashSet<>();
        List<ItemBank> result = new ArrayList<>();
        for (MyCollect collect : collects) {
            //添加到Set中
            itemIdSet.add(collect.getItemBankId());
        }
        //根据收藏的题目ID，从题库里面取出对应的题目，并相应给客户端
        for (Long itemId : itemIdSet) {
            result.add(itemBankRepository.findItemBankByItemBankId(itemId));
        }
        BizResult myResult = new BizResult();
        myResult.setMessage("获取成功");
        myResult.setData(result);
        myResult.setRetCode(200);
        return myResult;
    }

    /**
     * 移除收藏
     * @param token
     * @param questionid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/item/collect/remove", method = {RequestMethod.GET, RequestMethod.POST})
    public BizResult removeMyCollect(String token, long questionid) {
        //根据传过来的TOken判断会话是否过期，如果过期了，就响应异常给客户端
        if (null == TokenCache.tokens.get(token)) {
            BizResult biz = new BizResult();
            biz.setMessage("会话已过期");
            biz.setRetCode(-1);
            return biz;
        }
        //如果缓存中有用户，就根据token查询到用户的ID
        long userId = (long) TokenCache.tokens.get(token);
        //根据题目id和用户ID查找收藏的题目，如果没有找到，说明传递的参数有问题，响应异常给客户端
        List<MyCollect> byUserIdAndItemBankId = collectRepository.findByUserIdAndItemBankId(userId, questionid);
        if (byUserIdAndItemBankId == null || byUserIdAndItemBankId.size() < 1) {
            BizResult bizResult = new BizResult();
            bizResult.setRetCode(-1);
            bizResult.setMessage("服务端处理失败，请稍后再试");
            return bizResult;
        }
        //如果找到了，就把这条记录删除掉，再把“移除成功”的信息响应给客户端APP，APP再刷新页面
        MyCollect collect = byUserIdAndItemBankId.get(0);
        long collectId = collect.getCollectId();
        collectRepository.deleteById(collectId);
        BizResult myResult = new BizResult();
        myResult.setMessage("移除成功");
        myResult.setRetCode(200);
        return myResult;
    }
}
