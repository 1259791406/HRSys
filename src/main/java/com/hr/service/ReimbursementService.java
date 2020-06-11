package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Reimbursement;

public interface ReimbursementService {

    JSONObject list(Reimbursement reimbursement);

    JSONObject Add(Reimbursement reimbursement);

    JSONObject App(Approval approval);

    JSONObject type();

    JSONObject detil(String code);
}
