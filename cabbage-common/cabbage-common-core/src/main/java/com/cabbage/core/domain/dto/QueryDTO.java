package com.cabbage.core.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author xppd 1239694214@qq.com
 * @date 2021/12/1
 */
@Data
public class QueryDTO<T> {
    @JSONField(serialize = false)
    private int pageSize = 10;
    @JSONField(serialize = false)
    private int pageNum = 1;

    @JSONField(serialize = false)
    public Page<T> page() {
        return new Page<T>(this.getPageNum(), this.getPageSize());
    }

    public Page<T> doNotGetCount() {
        return new Page<T>(this.getPageNum(), this.getPageSize(), false);
    }
}
