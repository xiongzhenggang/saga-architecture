package com.xzg.library.config.infrastructure.configuration.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzg.library.config.infrastructure.auth.ApiHeaderUtil;
import com.xzg.library.config.infrastructure.common.constant.SystemMessage;
import com.xzg.library.config.infrastructure.model.HeaderUserModel;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration
 * @className: TokenInterceptor
 * @author: xzg
 * @description: TODO
 * @date: 13/11/2023-下午 10:37
 * @version: 1.0
 */
@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Resource
    private ObjectMapper mapper;

    @Override
    public void apply(RequestTemplate template) {
        HeaderUserModel header = ApiHeaderUtil.getHeader();
        if (header != null) {
            try {
                template.header(SystemMessage.HEADER_PORTAL_USER, mapper.writeValueAsString(header));
            } catch (JsonProcessingException e) {
                log.warn("write header portalUser error : {}", header);
                log.warn("error", e);
            }
        }
    }
}

    