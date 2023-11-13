package com.xzg.library.config.infrastructure.configuration;

import com.xzg.library.config.infrastructure.utility.RsaUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.library.config.infrastructure.configuration
 * @className: TokenKeySingleBean
 * @author: xzg
 * @description: 生成单例rsa key 保存内存
 * @date: 13/11/2023-下午 8:41
 * @version: 1.0
 */

@Configuration
public class TokenKeySingleBean {
    /**
     * 根据公钥证书返回公钥对象
     * @return
     */
    @Bean
    public RSAPublicKey parsePublicKey(){
        Resource resource = new ClassPathResource("public_key.pem");
        String publicKeyString;
        try {
            publicKeyString = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            String publicKeyStr = publicKeyString
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decodedPublicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            return RsaUtil.readPublicKeyByByte(decodedPublicKeyBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return
     */
    @Bean
    public RSAPrivateKey parsePrivateKey(){
        Resource resource = new ClassPathResource("private_key_pkcs8.pem");
        String privateKeyString ;
        try {
            privateKeyString  = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            String privateKeyStr = privateKeyString
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decodedPrivateKeyBytes  = Base64.getDecoder().decode(privateKeyStr);
            return RsaUtil.readPrivateKeyByByte(decodedPrivateKeyBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


    