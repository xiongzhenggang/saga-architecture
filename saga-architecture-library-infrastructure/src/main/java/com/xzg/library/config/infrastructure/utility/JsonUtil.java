package com.xzg.library.config.infrastructure.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.xzg.library.config.infrastructure.configuration.feign.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration.feign
 * @className: JsonUtil
 * @author: xzg
 * @description: json转换对象
 * @date: 14/11/2023-下午 9:02
 * @version: 1.0
 */
@Component
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER;
    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(JavaTimeModule.YYYY_MM_DD_HH_MM_SS));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.registerModule(javaTimeModule)
                .registerModule(new ParameterNamesModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     *序列化转换对象
     * @param jsonStr
     * @param targetType
     * @return
     * @param <T>
     */
    public <T> T json2obj(String jsonStr, Type targetType) {
        if (jsonStr == null || jsonStr.length() <= 0){
            return null;
        }
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);

        } catch (Exception e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }

    /**
     * 类防范
     * @param jsonStr
     * @param targetType
     * @return
     * @param <T>
     */
    public static  <T> T jsonStr2obj(String jsonStr, Type targetType) {
        if (jsonStr == null || jsonStr.length() <= 0){
            return null;
        }
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);

        } catch (Exception e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }
    /**
     * 对象转json
     */
    public  String object2Json(Object object) {
        try {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            OBJECT_MAPPER.registerModule(javaTimeModule);
            return  OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (Exception e) {
            return null;
        }
    }
    public  static String object2JsonStr(Object object) {
        try {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            OBJECT_MAPPER.registerModule(javaTimeModule);
            return  OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ObjectMapper getMapper(){
        return  OBJECT_MAPPER;
    }
}


    