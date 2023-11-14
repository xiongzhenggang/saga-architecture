package com.xzg.library.config.infrastructure.auth;

import com.xzg.library.config.infrastructure.model.HeaderUserModel;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.auth
 * @className: ApiHeadUtil
 * @author: xzg
 * @description: 通用获取请求头数据
 * @date: 13/11/2023-下午 8:00
 * @version: 1.0
 */
public final class ApiHeaderUtil {
    private static final ThreadLocal<HeaderUserModel> header = new ThreadLocal();

    public static HeaderUserModel getHeader() {
        return (HeaderUserModel)header.get();
    }

    public static void setHeader(HeaderUserModel headerModel) {
        header.set(headerModel);
    }

    public static void removeHeader() {
        header.remove();
    }

    private ApiHeaderUtil() {
        throw new UnsupportedOperationException("A utility class  cannot be instantiated");
    }
}

    