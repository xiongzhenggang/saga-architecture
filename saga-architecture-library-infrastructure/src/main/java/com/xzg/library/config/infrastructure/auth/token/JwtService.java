package com.xzg.library.config.infrastructure.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * token的生成主要是使用工具包来实现,在本项目中Token中主要存储用户信息 & 用户权限,
 * 下面我们先看一下token工具包的代码.主要包括为: 生成token,从token中获取信息,以及验证token
 */
@Service
@Slf4j
public class JwtService {

    /**
     * 加密盐值 单key的情况
     */
    @Value("${application.security.jwt.secret-key:secret-key}")
    private String secretKey;

    /**
     * Token失效时间
     */
    @Value("${application.security.jwt.expiration:3600000}")
    private long jwtExpiration;

    /**
     * Token刷新时间
     */
    @Value("${application.security.jwt.refresh-token.expiration:7200000}")
    private long refreshExpiration;

    @Resource
    private RSAPrivateKey rsaPrivateKey;
    @Resource
    private RSAPublicKey rsaPublicKey;
    /**
     * 从Token中获取Username
     * @param token Token
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从Token中回去数据,根据传入不同的Function返回不同的数据
     * eg: String extractUsername(String token)
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractRsaAllClaims(token);//extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 生成Token无额外信息
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * 生成Token,有额外信息
     * @param extraClaims 额外的数据
     * @param userDetails 用户信息
     * @return String
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
//        return buildToken(extraClaims, userDetails, jwtExpiration);
        return buildRsaToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * 生成刷新用的Token
     * @param userDetails 用户信息
     * @return String
     */
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
//        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
            return buildRsaToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * 构建Token方法
     * @param extraClaims 额外信息
     * @param userDetails //用户信息
     * @param expiration //失效时间
     * @return String
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {

        return Jwts.builder()                     // (1)
                .header()                                   // (2) optional
                .keyId("aKeyId")
                .and()
                .subject(userDetails.getUsername())        // (3) JSON Claims, or
                //.content(aByteArray, "text/plain")        //     any byte[] content, with media type
                .signWith(getSignInKey())                       // (4) if signing, or
                //.encryptWith(key, keyAlg, encryptionAlg)  //     if encrypting
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(extraClaims)
                .compact();                                 // (5)

    }

    /**
     *  私钥签名
     * @param extraClaims
     * @param userDetails
     * @param expiration
     * @return
     */
    private String buildRsaToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()                     // (1)
                .header()                                   // (2) optional
//                .keyId("aKeyId")
                .and()
                .subject(userDetails.getUsername())        // (3) JSON Claims, or
                .signWith(rsaPrivateKey)                       // (4) if signing, or
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(extraClaims)
                .compact();                                 // (5)

    }
    /**
     * 验证Token是否有效
     * @param token Token
     * @param userDetails 用户信息
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * 判断Token是否过去
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 从Token中获取失效时间
     */
    private Date extractExpiration(String token) {
        //通用方法,传入一个Function,返回一个T
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从Token中获取所有数据
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // <----
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从Token中获取所有数据
     * 公钥解析
     */
    private Claims extractRsaAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(rsaPublicKey) // <----
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    /**
     * 获取签名Key
     * Token 加密解密使用
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}