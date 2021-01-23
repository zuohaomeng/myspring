package com.meng.zspring.framework.webmvc;

import java.util.Map;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZModelAndView {
    /**
     * 保存视图名
     */
    private String viewName;
    /**
     * 参数
     */
    private Map<String,?> model;

    public ZModelAndView(String viewName) { this.viewName = viewName; }

    public ZModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}
