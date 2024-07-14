package com.xzg.library.config.infrastructure.common.constant;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.constant
 * @className: SystemMessage
 * @author: xzg
 * @description: TODO
 * @date: 14/11/2023-下午 8:27
 * @version: 1.0
 */
public class SystemMessage {
    public final  static  String SYS_USER_PERMISSION_LIMIT="该请求缺少JWT认证信息，请登录";

    public final  static  String SYS_USER_PERMISSION_ERROR="JWT认证异常";
    public final  static  String SYS_USER_PERMISSION_EXPIRED="JWT已过期";
    public static String HEADER_PORTAL_USER ="userModel";
}


    