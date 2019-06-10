package com.duan.video.common;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询对象
 *
 * @param <T>
 */
public class Query<T> extends Page<T> {

    private Map<String, Object> params = new HashMap<>();
    public final static Long DEFAULT_CURRENT = 1L;
    public final static  Long DEFAULT_SIZE = 10L;

    public Query(Map<String, Object> params) {
        super(Long.parseLong(params.getOrDefault("current", DEFAULT_CURRENT).toString()), Long.parseLong(params.getOrDefault("size", DEFAULT_SIZE).toString()));
        String orderByField = params.getOrDefault("orderByField", "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
            if (isAsc) {
                this.setAsc(orderByField);
            } else {
                this.setDesc(orderByField);
            }
        }
        params.remove("current");
        params.remove("size");
        params.remove("orderByField");
        params.remove("isAsc");
        this.setParams(params);
    }

    public Query(){
        super(DEFAULT_CURRENT, DEFAULT_SIZE);
    }
    public Query(Long current, Long size){
        super(current, size);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
