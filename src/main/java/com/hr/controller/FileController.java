package com.hr.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.FileUtil;
import com.hr.Util.Util;
import com.hr.dao.FileDao;
import com.hr.model.ToolModal;
import com.hr.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UtilService utilService;

    /**
     * 文件上传
     *
     * @param file    文件流
     * @param request 请求对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/FileUpload")
    public JSONObject FileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        FileUtil fileUtil = new FileUtil();
        String name = file.getOriginalFilename();
        String suffixName = name.substring(name.lastIndexOf("."));
        String fileName = Util.GetUUID() + suffixName;
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<>();
        map.put("oldName", name);
        map.put("newName", fileName);
        map.put("type", suffixName.substring(1, suffixName.length()));
        map.put("size", file.getSize());
        map.put("uuid", id);
        fileDao.AddFile(map);
        if ("all".equals(id)) {
            return utilService.importUtil(file);
        } else {
            return fileUtil.FUtil(file, request, fileName);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/fileList")
    public JSONObject fileList(String uuid) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<ToolModal> list = fileDao.list(uuid);
        for (ToolModal toolModal : list) {
            array.add(JSON.parseObject(JSON.toJSONString(toolModal)));
        }
        json.put("code", 200);
        json.put("data", array);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public JSONObject delete(String id) {
        JSONObject json = new JSONObject();
        fileDao.delete(id);
        json.put("code", 200);
        json.put("mes", "删除此附件成功！");
        return json;
    }
}