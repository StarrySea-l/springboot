package com.springboot.dome.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT(JSON WEB TOKEN)生成和解析工具类
 * 2017年2月7日
 */
public class JwtKit {

    /**
     * 加密密钥从
     */
    private static final String key = "jwt_key";

    /**
     * 根据用户信息生成jwt（用户信息应可以唯一标识一个用户，比如用户ID，用户登录名等等）
     *
     * @param sub
     * @return
     */
    public static String createJwt(String sub) {
        return Jwts.builder()
                .setSubject(sub)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * 根据用户信息和有效时间（秒）生成jwt
     *
     * @param sub
     * @param expiredInSeconds
     * @return
     */
    public static String createJwt(String sub, int expiredInSeconds) {
        if (expiredInSeconds <= 0) {
            return createJwt(sub);
        }
        return Jwts.builder()
                .setSubject(sub)
                .setExpiration(new Date(System.currentTimeMillis() + expiredInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * 根据用户信息和签发人信息生成jwt
     *
     * @param sub
     * @param iss
     * @return
     */
    public static String createJwt(String sub, String iss) {
        if (null == iss || iss.length() == 0) {
            return createJwt(sub);
        }
        return Jwts.builder()
                .setIssuer(iss)
                .setSubject(sub)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * 根据用户信息、签发人信息和有效时间生成jwt
     *
     * @param sub
     * @param iss
     * @param expiredInSeconds
     * @return
     */
    public static String createJwt(String sub, String iss, int expiredInSeconds) {
        if (expiredInSeconds <= 0) {
            return createJwt(sub, iss);
        }
        return Jwts.builder()
                .setIssuer(iss)
                .setSubject(sub)
                .setExpiration(new Date(System.currentTimeMillis() + expiredInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    /**
     * 解析jwt，返回解析出来的用户信息，调用该方法时需要使用try...catch捕获异常，如果发生异常，则说明该jwt无效或者已经过期
     *
     * @param jwt
     * @return
     */
    public static String parseJwt(String jwt) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
    }


}
