package com.hr.Overall;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.ErrorFileUtil;
import com.hr.Util.Util;
import com.hr.dao.FileDao;
import com.hr.model.CalendarList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

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
