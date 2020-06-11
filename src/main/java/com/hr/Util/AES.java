package com.hr.Util;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.ErrorFileUtil;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class AES {
    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/',};

    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
            60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
            -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
            -1, -1};

    /**
     * 解密
     *
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
        return buf.toByteArray();
    }

    /**
     * 权限系统登陆账号和用户名
     *
     * @param map
     * @return
     */
    public static JSONObject GetNamePass(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            String username = map.get("username").toString();
            String name = "";
            String password = map.get("password").toString();
            String pass = "";
//            for (int i = 1; i < 5; i++) {
            name = new String(decode(new String(decode(new String(decode(username))))));
            pass = new String(decode(new String(decode(new String(decode(password))))));
//            }
            if (name.equals("administrator") && pass.equals("admin")) {
                String PowerToken = Util.Token();
                jsonObject.put("code", 200);
                jsonObject.put("mes", "欢迎进入权限管理系统！");
                jsonObject.put("PowerToken", PowerToken);
                jedis.set(PowerToken, PowerToken);
                jedis.expire(PowerToken, 7200);
                return jsonObject;
            } else {
                jsonObject.put("code", 500);
                jsonObject.put("mes", "账号或密码错误！");
                return jsonObject;
            }
        } catch (Exception e) {
            return ErrorFileUtil.Ex(e);
        }
    }
}
