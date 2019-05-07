package org.virtue.controller;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.virtue.dao.ItemBankRepository;
import org.virtue.domain.ItemBank;
import org.virtue.utils.HtmlParser;

import java.util.Random;

/**
 * 爬虫工具，提供去互联网爬取题库的方法
 */
@Controller
public class SpiderController {
    private Logger log = LoggerFactory.getLogger(ItemBankController.class);
    public static  String baseimgUrl = "https://www.jiazhao.com/";
    @Autowired
    ItemBankRepository itemBankRepository;

    /**
     * 这个是爬虫类，用来从互联网上爬取题目，省去了每次一个个添加的麻烦。
     */
    @RequestMapping(value ="/spider",method = {RequestMethod.GET})
    public  void spilder(){
        //爬虫怕的数量不需要太多，够用就行
        for(int i=1;i<10000;i++) {
            int type = 1;//题目类型
            ItemBank itemBank = new ItemBank();
            String iamgeUrl;
            String s = null;
            try {
                s = HtmlParser.httpPostWithJSON("https://www.jiazhao.com/tiba/"+i+"/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Document document = Jsoup.parse(s);
            Elements body = document.select("div[class=topic-l f-l]");
            //其中包含字母和答案
            Elements li = body.select("li");

            if (li.size() == 6) {
                //选择题
                itemBank.setItemBankSubject(li.get(0).html());
                ;
                itemBank.setItemBankChoiceA(li.get(1).html());
                itemBank.setItemBankChoiceB(li.get(2).html());
                itemBank.setItemBankChoiceC(li.get(3).html());
                itemBank.setItemBankChoiceD(li.get(4).html());
                itemBank.setItemBankAnswer(li.select("strong").html());
                itemBank.setItemBankSubjectType(1);
            } else {
                //判断题
                itemBank.setItemBankSubject(li.get(0).html());
                ;
                itemBank.setItemBankChoiceA(li.get(1).html());
                itemBank.setItemBankChoiceB(li.get(2).html());
                itemBank.setItemBankAnswer(li.select("strong").html());
                itemBank.setItemBankSubjectType(2);
            }
            Elements image = document.select("div[class=topic-r f-l imgorvideo]");

            if (image.size() > 0) {
                Elements img = image.get(0).select("img");
                if (img.size() > 0) {
                    itemBank.setItemBankSubjectImageUrl(baseimgUrl + img.attr("src"));
                }
            }
            Elements analyse = document.select("div[class=topic-b analy-note t-bg]");
            Elements p = analyse.select("p");
            String anastr = p.html();
            itemBank.setItemBankAnswerAnalyse(anastr);
            Random random = new Random();
            itemBank.setItemBankDifficutLevel(random.nextInt(3) + 1);
            itemBank.setItemBankKownledgeType(random.nextInt(6) + 1);
            System.out.println("爬取第"+i+"条数据："+itemBank.toString());
            try {
                itemBankRepository.save(itemBank);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
