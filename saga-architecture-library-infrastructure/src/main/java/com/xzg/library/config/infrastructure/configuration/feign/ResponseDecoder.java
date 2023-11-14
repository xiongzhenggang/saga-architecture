package com.xzg.library.config.infrastructure.configuration.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.xzg.library.config.infrastructure.utility.JsonUtil;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration.feign
 * @className: ResponseDecoder
 * @author: xzg
 * @description: feign传递后转换
 * @date: 14/11/2023-下午 9:01
 * @version: 1.0
 */
@Slf4j
@Component
public class ResponseDecoder implements Decoder {

    private final static String RES_DATA = "result";
    public static final String COMMON_RESPONSE = "com.xzg.library.config.infrastructure.model";

    @Autowired
    JsonUtil jsonUtil;

    /**
     * 统一处理，根据状态码判断返回正常还是异常的，
     * 其他状态码直接抛出异常
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.body() == null) {
            return response;
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        log.info("bodyStr===========> {}", bodyStr);
        if (type.getTypeName().contains(COMMON_RESPONSE)){
            log.info("decode COMMON_RESPONSE");
            return jsonUtil.json2obj(bodyStr, type);
        }
        try {
            JsonNode jsonNode = jsonUtil.getMapper().readTree(bodyStr);
            JsonNode statusNode = jsonNode.get("status");
            JsonNode messageNode = jsonNode.get("messages");
            JsonNode responseNode = jsonNode.get(RES_DATA);
            if (statusNode != null) {
                if (responseNode != null) {
                    String resData = responseNode.toPrettyString();
                    if (StringUtils.hasLength(resData)) {
                        return jsonUtil.json2obj(resData, type);
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            log.error("not json{}", bodyStr);
            return response;
        }
        return jsonUtil.json2obj(bodyStr, type);
    }


}

    