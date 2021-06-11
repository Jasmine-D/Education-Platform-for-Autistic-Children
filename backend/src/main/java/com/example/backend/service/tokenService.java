package com.example.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("tokenService")
public class tokenService {
    public String getToken(String user_id,String password) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 3000;//三个小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user_id).withIssuedAt(start).withExpiresAt(end)//将user_id保存到token里面，并设置token有效时长
                .sign(Algorithm.HMAC256(password));//以password作为 token 的密钥
        return token;
    }
}
