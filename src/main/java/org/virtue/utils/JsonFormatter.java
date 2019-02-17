package org.virtue.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TimeZone;

public class JsonFormatter {

    private static Logger log = LoggerFactory.getLogger(JsonFormatter.class);

    private static final ObjectMapper jackson = new ObjectMapper();

    static {
        jackson.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        jackson.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 支持 ISO-8601 时间格式， 形如："1970-01-01T00:00:00.000+0000"
        jackson.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 如果不设置缺省时区居然是 Zero，所以需要重新设置下
        jackson.setTimeZone(TimeZone.getDefault());
    }

    private JsonFormatter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSON(Object object) {

        try {
            return jackson.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 美化输出，通常用于控制台展示
     *
     * @param object
     * @return
     */
    public static String toPrettyJSON(Object object) {
        try {
            return jackson.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 美化输出，通常用于控制台展示
     *
     * @param jSON
     * @return
     */
    public static String toPrettyJSON(String jSON, boolean returnOriginIfError) {
        try {
            return jackson.writerWithDefaultPrettyPrinter().writeValueAsString(toMap(jSON));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return returnOriginIfError ? jSON : null;
    }

    /**
     * JavaBean转换为json的byte数组
     *
     * @param object
     * @return
     */
    public static byte[] toJSONBytes(Object object) {

        try {
            return jackson.writeValueAsBytes(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * Jason字符串 转 JavaBean
     *
     * @param jSON
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T toObject(String jSON, Class<T> valueType) {
        try {
            return jackson.readValue(jSON, valueType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toObject(byte[] src, Class<T> valueType) {

        try {
            return jackson.readValue(src, valueType);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    public static <T> T toObject(InputStream src, Class<T> valueType) {

        try {
            return jackson.readValue(src, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static <T> T toObject(String jSON, TypeReference<T> type) {
        try {
            return jackson.readValue(jSON, type);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String jSON) {
        if (jSON == null) {
            return null;
        }
        return toObject(jSON, Map.class);
    }

}
