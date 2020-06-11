package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.KaoQinData;
import com.hr.dao.DeptDao;
import com.hr.model.Tree;
import com.hr.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class DeptImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public JSONObject DeList(Tree t) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            int page = t.getPage();
            int Limit = t.getLimit();
            if (t.getTitle() == "") {
                t.setTitle(null);
            }
            List<Tree> list = deptDao.DeList(t);
            for (Tree tree : list) {
                tree.setPage(page);
                tree.setLimit(Limit);
                array.add(JSON.parseObject(JSON.toJSONString(tree)));
            }
            json.put("code", 0);
            json.put("data", array);
            json.put("count", array.size());
            json.put("mes", "部门获取完毕。");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject Add(Tree tree) {
        JSONObject json = new JSONObject();
        deptDao.Add(tree);
        json.put("code", 200);
        json.put("mes", "添加部门成功！");
        return json;
    }


    @Override
    public JSONObject synchro() {
        JSONObject json = new JSONObject();
        List<Tree> list = KaoQinData.GetDept();
        List<Tree> treeList = deptDao.list();
        int size = 0;
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getTime();
            if (treeList.size() == 0) {
                deptDao.Add(list.get(i));
            } else {
                for (int a = 0; a < treeList.size(); a++) {
                    String daptId = treeList.get(a).getTime();
                    if (id.equals(daptId)) {

                    } else {
                        size = size + 1;
                        deptDao.Add(list.get(i));
                    }
                }
            }
        }
        json.put("code", 200);
        json.put("mes", "同步成功！新增：" + size + " 部门");
        return json;
    }
}