package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Substitute;

public interface SubstituteService {
    JSONObject list(Substitute substitute) throws Exception;

    JSONObject Add(Substitute substitute);

    JSONObject Approval(Approval approval);

    JSONObject GetSubCaList(String userid);
}
