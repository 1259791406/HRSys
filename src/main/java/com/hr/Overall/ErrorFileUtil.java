package com.hr.Overall;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.Global;
import com.hr.Util.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorFileUtil {
    /**
     * 发生异常时将异常信息写入文件
     *
     * @param e 异常信息
     */
    public static JSONObject Ex(Exception e) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 500);
        jsonObject.put("mes", e.toString());
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        StackTraceElement[] stackTrace = e.getStackTrace();
        String FileName = stackTrace[(stackTrace.length - 1)].getFileName();
        String MethodName = stackTrace[(stackTrace.length - 1)].getMethodName();
        int lineNumber = stackTrace[(stackTrace.length - 1)].getLineNumber();
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        System.out.println(writer.toString());
        String filePath = Global.FilePath + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file);
            } else {
                fos = new FileOutputStream(file, true);
            }
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write("执行类名：" + FileName + "\t方法：" + MethodName + "\t行数：" + lineNumber + "\t\t方法运行时间：" + Util.GetTime() + "\t发成异常，错误信息如下：");
            osw.write("\r\n");
            osw.write(writer.toString());
            osw.write("\n");
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                osw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObject;
    }
}