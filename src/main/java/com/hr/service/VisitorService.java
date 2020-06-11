package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Visitor;

import java.util.Map;

public interface VisitorService {
    JSONObject Add(Visitor visitor);
    JSONObject Page(Visitor visitor) throws Exception;
    JSONObject TypeList();
    JSONObject Approval(Approval approval);
    JSONObject info(Map<String,Object> map);
}
