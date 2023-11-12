package com.xzg.library.config.infrastructure.configuration;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration
 * @className: JPAConfiguration
 * @author: xzg
 * @description:  EntityManager的搜索范围
 * @date: 11/11/2023-下午 2:55
 * @version: 1.0
 */

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@EntityScan(basePackages = {"com.xzg"})
public class JPAConfiguration {
}

    