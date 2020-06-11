package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.TiaoXiu;

public interface TiaoXiuService {
    JSONObject list(TiaoXiu tiaoXiu);

    JSONObject Add(TiaoXiu tiaoXiu);

    JSONObject App(Approval approval);

    JSONObject GetList(String userid);
}
