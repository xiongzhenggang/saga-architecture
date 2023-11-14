package com.xzg.library.config.infrastructure.configuration.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration.feign
 * @className: ResponseErrorDecoder
 * @author: xzg
 * @description: TODO
 * @date: 14/11/2023-下午 9:01
 * @version: 1.0
 */
@Slf4j
@Configuration
public class ResponseErrorDecoder implements ErrorDecoder {

    private final static String MSG = "messages";
    private final static String CODE = "status";

    private final static String SERVICE_EX = "com.cmsr.common.exception.ServiceException";

    private final static String VALIDATE_EX= "com.cmsr.common.exception.ValidateException";


    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            if (response.body() != null) {

                ObjectMapper mapper = new ObjectMapper();
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                log.info("feign error response :"+bodyStr);
                Map<String,Object> result = mapper.readValue(bodyStr,
                        new TypeReference<Map<String,Object>>() {});
                if (result.get(CODE)==null||result.get(MSG)==null){
                    return FeignException.errorStatus(methodKey, response);
                }
                if (response.status()== HttpStatus.BAD_REQUEST.value()){
                    Class clazz = Class.forName(VALIDATE_EX);
                    return (Exception) clazz.getDeclaredConstructor(String.class).newInstance(result.get(CODE)+"|"+result.get(MSG));
                }else {
                    Class clazz = Class.forName(SERVICE_EX);
                    Object msg = result.get(MSG);
                    if(result.get(MSG).getClass().equals(ArrayList.class)){
                        msg = ((ArrayList)result.get(MSG)).get(0);
                    }
                    return (Exception) clazz.getDeclaredConstructor(String.class).newInstance(result.get(CODE)+"|"+msg);
                }

            }

        } catch (Exception e) {
            log.error("feign处理出错",e);
        }
        return FeignException.errorStatus(methodKey, response);
    }
}


    