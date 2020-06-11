package com.hr.Overall;

import redis.clients.jedis.Jedis;

/**
 * 判断Token是否已存在！
 */
public class TokenIsTrue {
    public static boolean isTrue(String token) {
        Jedis jedis = new Jedis("127.0.0.1");
        if (jedis.exists(token)) {
            jedis.expire(token, Global.Time);
            return true;
        } else {
            return false;
        }
    }
}
