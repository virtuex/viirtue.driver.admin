package org.virtue.basic.exception;


public class ErrorCodeUtil {

    private ErrorCodeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 得到固定长度以内的数据值
     *
     * @param str
     * @param len
     * @return
     */
    public static int fixCode(String str, int len) {
        int code = hashCode(str);
        int curLen = String.valueOf(code).length();
        if (curLen > len) {
            return fixCode(String.valueOf(code), len);
        }
        return code;
    }

    /**
     * 字符串转换为int
     *
     * @param str
     * @return
     */
    public static int hashCode(String str) {
        int h = 0;
        if (str == null) {
            return h;
        }
        char[] value = str.toCharArray();
        if (value.length > 0) {

            for (int i = 0; i < value.length; i++) {
                h = h + value[i];
            }
        }
        return h;
    }
}

