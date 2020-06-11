package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.ErrorFileUtil;
import com.hr.Util.Util;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/photo")
public class PhotoController {

    @ResponseBody
    @RequestMapping(value = "/file")
    public JSONObject photo(HttpServletRequest request,@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String name = multipartFile.getOriginalFilename();
        String suffixName = name.substring(name.lastIndexOf("."));
        String fileName = Util.GetUUID() + suffixName;
        return FUtil(multipartFile, request, fileName);
    }

    public JSONObject FUtil(MultipartFile file, HttpServletRequest request, String nleName) {
        JSONObject json = new JSONObject();
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
        try {
            String path = request.getSession().getServletContext().getRealPath("/") + "static\\uploadFile\\";
            String name = file.getOriginalFilename();
            File filepath = new File(path, nleName);
            System.out.println("上传文件名称：" + name);
            System.out.println("新文件名称：" + filepath.getName());
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            File tempFile = new File(path + File.separator + nleName);
            file.transferTo(tempFile);
            resUrl.put("src", tempFile.getPath());
            res.put("code", 0);
            res.put("msg", "");
            res.put("data", resUrl);
            json.put("code", 0);
            json.put("mes", "上传成功！");
            return res;
        } catch (Exception e) {
            ErrorFileUtil.Ex(e);
            res.put("code", 0);
            res.put("msg", "上传失败！");
            return res;
        }
    }
}
