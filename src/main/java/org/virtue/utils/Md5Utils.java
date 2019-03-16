package org.virtue.utils;


import org.springframework.util.DigestUtils;

public class Md5Utils {
    public static String base64Str(String oriStr){
        return Base64Coder.encodeString(oriStr) ;
    }
}
