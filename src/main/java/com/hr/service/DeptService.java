package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Tree;

public interface DeptService {
    JSONObject DeList(Tree tree);

    JSONObject Add(Tree tree);

    JSONObject synchro();
}