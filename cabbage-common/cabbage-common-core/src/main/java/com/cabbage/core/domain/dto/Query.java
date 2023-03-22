package com.cabbage.core.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.security.DenyAll;

@Data
public class Query<T> {

    @JSONField(serialize = false)
    @ApiModelProperty("数量")
    private int pageSize = 10;
    @JSONField(serialize = false)
    @ApiModelProperty("页码")
    private int pageNum = 1;

    @JSONField(serialize = false)
    public Page<T> page() {
        return new Page<T>(this.getPageNum(), this.getPageSize());
    }

    public Page<T> doNotGetCount() {
        return new Page<T>(this.getPageNum(), this.getPageSize(), false);
    }
}
