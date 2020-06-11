package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Workbench;

public interface WorkService {

    JSONObject list(Workbench workbench);

    JSONObject add(Workbench workbench);

    JSONObject update(Workbench workbench);
}
