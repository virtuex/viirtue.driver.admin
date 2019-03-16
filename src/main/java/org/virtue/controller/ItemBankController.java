package org.virtue.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Log4j2
public class ItemBankController {
    @Autowired
    ItemBankRepository itemBankRepository;

    @ResponseBody
    @RequestMapping(value = "/item/bank/list/{page}",method = {RequestMethod.GET})
    public BizResult itemBanksList(@PathVariable Integer page){
        int start = (page-1)* ProConfig.pageSize;
        int end = start+ProConfig.pageSize-1;
        List<ItemBank> all = itemBankRepository.findAll();
        List<ItemBank> target;
        if(all.size()>ProConfig.pageSize){
            target = all.subList(start,end);
        }else{
            target = all;
        }
        BizResult result = BizResult.success();
        result.setData(target);
        result.setMessage("请求成功");
        result.setRetCode(ResultCode.SUCCESS_CODE);
        log.debug("响应数据:"+JsonFormatter.toPrettyJSON(result));
        return result;
    }

    @RequestMapping(value ="/item-bank-list",method = {RequestMethod.GET})
    public String toItemBanksList(){
        return "/pages/charts/item_bank_list";
    }

    @RequestMapping(value = "/item-bank-add",method = {RequestMethod.GET})
    public String addItemBank(ItemBank itemBank,HttpServletRequest request){
        ItemBank save = itemBankRepository.save(itemBank);
        log.debug("插入成功");
        return "/pages/charts/item_bank_list";
    }

    @RequestMapping(value="/item-detail",method = {RequestMethod.GET})
    @ResponseBody
    public ItemBank itemBankDetail(long itemBankId){
        ItemBank itemBankByItemBankId = itemBankRepository.findItemBankByItemBankId(itemBankId);
        return itemBankByItemBankId;
    }

    @RequestMapping(value = "/item-bank-update",method = {RequestMethod.GET})
    public String updateItemBank(ItemBank itemBank,HttpServletRequest request){
        ItemBank save = itemBankRepository.save(itemBank);
        log.debug("更新成功");
        return "/pages/charts/item_bank_list";
    }

    @RequestMapping(value ="/item-delete",method = {RequestMethod.GET})
    public String deleteItemBank(Long itemBankId){
        itemBankRepository.deleteById(itemBankId);
        return "/pages/charts/item_bank_list";
    }


}
