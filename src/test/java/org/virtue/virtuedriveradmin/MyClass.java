package org.virtue.virtuedriveradmin;

import org.junit.Test;

public class MyClass {
    @Test
    public void test(){
        System.out.println(System.currentTimeMillis());
        String secret_key = "xg9PQBgvxkHZfX9WlocBUcV7UcaJo47u";
        String src = "GETcsec.api.qcloud.com/v2/index.php?" +
                "Action=AntiFraud" +
                "&Nonce=610022972" +
                "&Region=all" +
                "&SecretId=AKIDYu0sY0a8h28zazo2g7JFvMaZMtGObsEI" +
                "&Timestamp=1553083252019";
        String s = HMACSHA1.sha1_HMAC(src, secret_key);
        System.out.println(s);
    }
}
