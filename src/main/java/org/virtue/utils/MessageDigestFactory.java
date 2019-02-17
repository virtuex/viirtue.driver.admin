package org.virtue.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.constant.DigestAlgEnum;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @author xuda
 * @email xuda@koal.com
 * @Description: ${todo}
 * @date 2019/1/22 13:45
 */
public class MessageDigestFactory {
    Logger logger = LoggerFactory.getLogger(MessageDigestFactory.class);


    private MessageDigest digest;

    public static MessageDigestFactory getInstance(String digestAlg) throws Exception {
        if (!DigestAlgEnum.getValuesMap().contains(digestAlg.toUpperCase())) {
            throw new Exception("未知的算法");
        } else {
            return new MessageDigestFactory(digestAlg);
        }
    }

    public String digest2Base64(String plainText) throws Exception {
        return Base64Coder.encodeLines(digest.digest(plainText.getBytes()));
    }

    public String digestFile2Base64(File file) throws Exception {
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = new FileInputStream(file)) {
            int read = 0;
            while ((read = inputStream.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        return Base64Coder.encodeLines(digest.digest());
    }

    private MessageDigestFactory(String digestAlg) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            digest = MessageDigest.getInstance(digestAlg);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void judgeNpm() throws Exception {
        if (digest == null) {
            throw new Exception(String.format("初始化%s失败",MessageDigestFactory.class.getName()));
        }
    }
}

