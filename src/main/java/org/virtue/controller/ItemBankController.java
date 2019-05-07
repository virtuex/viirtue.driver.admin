package org.virtue.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.virtue.basic.result.BizResult;
import org.virtue.config.ProConfig;
import org.virtue.constant.ResultCode;
import org.virtue.domain.ItemBank;
import org.virtue.dao.ItemBankRepository;
import org.virtue.utils.JsonFormatter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemBankController {
    private Logger log = LoggerFactory.getLogger(ItemBankController.class);
    @Autowired
    ItemBankRepository itemBankRepository;

    /**
     * 后台题目列表
     * 请求URL：/item/bank/list/{page}
     * 其中{page}是浏览器传过来的页数
     * 每次查询的结果进行分页，分页的规则：
     *   开始条数=(页数-1)*每页数量
     *   结束页数=开始页数+每页数量
     *
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/item/bank/list/{page}",method = {RequestMethod.GET})
    public BizResult itemBanksList(@PathVariable Integer page){
        //计算开始和结束
        int start = (page-1)* ProConfig.pageSize;
        int end = start+ProConfig.pageSize-1;
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> target;
        //取出本页的题目数
        if(all.size()>ProConfig.pageSize){
            target = all.subList(start,end);
        }else{
            target = all;
        }
        //返回给浏览器
        BizResult result = BizResult.success();
        result.setData(target);
        result.setMessage("请求成功");
        result.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:"+JsonFormatter.toPrettyJSON(result));
        return result;
    }

    @RequestMapping(value ="/item-bank-list",method = {RequestMethod.GET})
    public String toItemBanksList(){
        return "pages/charts/item_bank_list";
    }

    /**
     * 插入题目，接收浏览器发送来的数据，框架会自动封装成对象
     * 后台将对象保存到数据库中
     * @param itemBank
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/item-bank-add",method = {RequestMethod.GET})
    public String addItemBank(ItemBank itemBank, BindingResult bindingResult){
        ItemBank save = itemBankRepository.save(itemBank);
        log.debug("插入成功");
        return "pages/charts/item_bank_list";
    }

    /**
     * 浏览器发送题目的ID，服务端根据题目的ID找到具体的题目，然后响应给浏览器
     * @param itemBankId
     * @return
     */
    @RequestMapping(value="/item-detail",method = {RequestMethod.GET})
    @ResponseBody
    public ItemBank itemBankDetail(long itemBankId){
        ItemBank itemBankByItemBankId = itemBankRepository.findItemBankByItemBankId(itemBankId);
        return itemBankByItemBankId;
    }

    /**
     * 浏览器将更新后的题目信息发送到服务端，服务端封装成对象，然后保存到数据库
     * @param itemBank
     * @param request
     * @return
     */
    @RequestMapping(value = "/item-bank-update",method = {RequestMethod.GET})
    public String updateItemBank(ItemBank itemBank,HttpServletRequest request){
        ItemBank save = itemBankRepository.save(itemBank);
        log.debug("更新成功");
        return "pages/charts/item_bank_list";
    }

    /**
     * 浏览器发送题目的ID到后台，后台根据题目的ID删除掉这个题目
     * @param itemBankId
     * @return
     */
    @RequestMapping(value ="/item-delete",method = {RequestMethod.GET})
    public String deleteItemBank(Long itemBankId){
        itemBankRepository.deleteById(itemBankId);
        return "pages/charts/item_bank_list";
    }


    @ResponseBody
    @RequestMapping(value = "/item/bank/search",method = {RequestMethod.GET})
    public BizResult search(@PathVariable String keyword){
        List<ItemBank> target = itemBankRepository.findItemBanksByItemBankSubjectLike(keyword);
        BizResult result = BizResult.success();
        result.setData(target);
        result.setMessage("请求成功");
        result.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:"+JsonFormatter.toPrettyJSON(result));
        return result;
    }



}
