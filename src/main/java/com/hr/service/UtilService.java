package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UtilService {
    JSONObject EducationList();
    JSONObject importUtil(MultipartFile file) throws IOException, Exception;
}
