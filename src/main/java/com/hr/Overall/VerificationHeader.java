package com.hr.Overall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Util.Util;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证请求信息头部信息得token是否存在，不存在则返回脏数据
 */
public class VerificationHeader {

    /**
     * 是否符合验签得要求
     *
     * @param request
     * @return
     */
    public static boolean isTrue(HttpServletRequest request) {
        Jedis jedis = new Jedis("127.0.0.1");
        String da = request.getHeader("token");
        String ip = request.getHeader("ip");
        if (da == null || da.isEmpty()) {
            return false;
        } else {
            return (jedis.exists(da)) ? true : false;
        }
    }

    public static JSONObject Verification(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        return null;
    }

    /**
     * 返回脏数据
     *
     * @return
     */
    public static JSONObject DirtyData() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < 10; i++) {
            array.add(Util.GetUUID());
        }
        json.put("code", 200);
        json.put("mes", "信息获取成功！");
        json.put("time", Util.GetTime());
        json.put("data", array);
        return json;
    }
}