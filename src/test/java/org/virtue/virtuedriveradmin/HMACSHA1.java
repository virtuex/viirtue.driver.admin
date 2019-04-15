package org.virtue.virtuedriveradmin;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HMACSHA1 {
    //   SECRET KEY
    private final static String secret_key = "xg9PQBgvxkHZfX9WlocBUcV7UcaJo47u";
    private final static String ENCODE = "UTF-8";
    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    /**
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha1_HMAC(String message, String secret) {
        String hash = "";
        String result="";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash  = Base64.getEncoder().encodeToString(bytes);
             result = java.net.URLEncoder.encode(hash, ENCODE);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error HmacSHA1 ===========" + e.getMessage());
        }
        return result;
    }
}
