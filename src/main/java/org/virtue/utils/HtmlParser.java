package org.virtue.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HtmlParser {
    public static String httpPostWithJSON(String url) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
        httpPost.setHeader("Accept-Language:","zh-CN,zh;q=0.9");
        httpPost.setHeader("Cache-Control","max-age=0");
        httpPost.setHeader("Connection","keep-alive");
        httpPost.setHeader("Cookie","hide_bottom_flag=1; Hm_lvt_38dd4dc9d50668280cafd5e36fb55bb4=1552725204,1552798651; Hm_lpvt_38dd4dc9d50668280cafd5e36fb55bb4=1552802719");
        httpPost.setHeader("Host","www.jiazhao.com");
        httpPost.setHeader("Referer","https://www.jiazhao.com/tiba/kmy_1601/");
        httpPost.setHeader("Upgrade-Insecure-Requests","1");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        System.out.println();
        HttpResponse resp = client.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }
        return respContent;
    }


    public static void main(String[] args) throws Exception {
        String result = httpPostWithJSON("https://www.jiazhao.com/tiba/kmy_1601/");
        System.out.println(result);
    }
}
