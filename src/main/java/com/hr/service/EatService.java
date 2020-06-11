package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Eat;

public interface EatService {
    JSONObject list(Eat eat);

    JSONObject Add(Eat eat);

    JSONObject App(Approval approval);
}
