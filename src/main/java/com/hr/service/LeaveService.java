package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.LeaveModel;

public interface LeaveService {
    JSONObject page(LeaveModel leaveModel);
    JSONObject Add(LeaveModel leaveModel);
    JSONObject Approval(Approval approval);
}
