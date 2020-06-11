package com.hr.model;

import com.hr.Overall.PageException;

/**
 * 分页工具类
 */
public class PageUtil {
    private int page;
    private int limit;
    private String token;

    public int getPage() {
        try {
            if (this.page == 0) {
                throw new PageException("请求页码参数不合法！");
            } else if (this.page == 1) {
                return 1;
            } else {
                return this.limit * (this.page - 1) + 1;
            }
        }catch (Exception e){
            return 1;
        }
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit(){
        try {
            if (this.limit == 0) {
                throw new PageException("每页数量不能为0，请检查数据合法性！");
            } else {
                return this.page * this.limit;
            }
        } catch (Exception e) {
            return this.page * this.limit;
        }
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "page：" + page +
                ", limit：" + limit +
                ", token：" + token +
                '}';
    }
}