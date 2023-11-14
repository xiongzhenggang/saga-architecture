package com.xzg.library.config.infrastructure.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.utility
 * @className: SplitHeaderToken
 * @author: xzg
 * @description: TODO
 * @date: 14/11/2023-下午 8:44
 * @version: 1.0
 */
public class SplitHeaderToken {

    /**
     * 截取请求头中的token
     * @param request
     * @return
     */
    public static String splitAuthTokenStr(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //如果不存在Token或者Token不已Bearer开头，则不进行处理
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
          return   null ;
        }
        //从authHeader中截取出Token信息
        return authHeader.substring(7);
    }

}


    