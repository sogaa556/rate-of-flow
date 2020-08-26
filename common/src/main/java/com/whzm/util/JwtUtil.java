package com.whzm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.whzm.pojo.User;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: springcloud-demo2
 * @BelongsPackage: com.example.gateway1.util
 * @Author: hq
 * @CreateTime: 2020-08-03 15:33
 * @Description:
 */
public class JwtUtil {
    //密钥
    public static final String SECRET = "sdjhakdhajdklsl;o653632";
    //过期时间:秒
    public static final int EXPIRE = 60*30;

    /**
     * 生成Token
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public static String createToken(          User user) throws Exception {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("id", user.getId())
               // .withClaim("userName", user.getUsername())
                .withClaim("phone", user.getPhone())
                .withClaim("status", user.getStatus())
                .withSubject("登录")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }
}
