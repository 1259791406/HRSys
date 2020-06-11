package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.OverType;
import com.hr.model.Overtime;
import com.hr.model.PageUtil;

import java.util.Map;

public interface OvertimeService {
    JSONObject TypeList(OverType overType);

    JSONObject page(Overtime overtime) throws Exception;

    JSONObject AddData(Overtime overtime);

    JSONObject Approval(Approval approval);

    JSONObject h5list(Overtime overtime);

    JSONObject IsApproval(Map<String, Object> map);
}