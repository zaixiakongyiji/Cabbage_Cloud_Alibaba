package com.cabbage.core.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xppd 1239694214@qq.com
 * @date 2021/12/1
 */
@Data
public class PageDTO<T> {
    @JSONField(name = "count")
    private long total;
    private long current;
    private long pages;
    private List<T> list = new ArrayList<>();

    public static <T, O> PageDTO<O> copyList(IPage<T> tiPage, Class<O> oClass) {
        PageDTO<O> oPageDTO = new PageDTO<>();
        oPageDTO.setPages(tiPage.getPages());
        oPageDTO.setCurrent(tiPage.getCurrent());
        oPageDTO.setTotal(tiPage.getTotal());
        if (CollectionUtil.isNotEmpty(tiPage.getRecords())) {
            List<O> ol = BeanUtil.copyToList(tiPage.getRecords(), oClass);
            oPageDTO.setList(ol);
        }
        return oPageDTO;
    }

    public static <T, O> PageDTO<T> copyList(IPage<O> oiPage) {
        PageDTO<T> tPageDTO = new PageDTO<>();
        tPageDTO.setPages(oiPage.getPages());
        tPageDTO.setCurrent(oiPage.getCurrent());
        tPageDTO.setTotal(oiPage.getTotal());
        return tPageDTO;
    }

    public static <T> PageDTO<T> copy(IPage<T> tiPage) {
        PageDTO<T> oPageDTO = new PageDTO<>();
        oPageDTO.setPages(tiPage.getPages());
        oPageDTO.setCurrent(tiPage.getCurrent());
        oPageDTO.setTotal(tiPage.getTotal());
        if (CollectionUtil.isNotEmpty(tiPage.getRecords())) {
            oPageDTO.setList(tiPage.getRecords());
        }
        return oPageDTO;
    }

    public static <T> PageDTO<T> empty() {
        PageDTO<T> tPageDTO = new PageDTO<>();
        tPageDTO.setPages(0);
        tPageDTO.setList(null);
        tPageDTO.setCurrent(0);
        tPageDTO.setTotal(0);
        return tPageDTO;
    }
}
