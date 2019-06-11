package com.duan.video.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duan.video.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 字典接口
 *
 * @author duanjw
 */
@Slf4j
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 查询所有代码
     *
     * @return
     */
    @GetMapping("list")
    public List selectList() {
        return dictService.list();
    }
}
