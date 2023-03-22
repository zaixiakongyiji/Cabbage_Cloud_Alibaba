package com.cabbage.core.domain.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class PageResult<T> {

    @ApiModelProperty("记录总数")
    private long total;
    @ApiModelProperty("页面总数")
    private long size;
    @ApiModelProperty("当前页面")
    private long current;
    @ApiModelProperty("值")
    private List<T> records;

    private static <T> PageResult<T> copy(Page<T> page){
        PageResult<T> pageResult=new PageResult<>();
        pageResult.setSize(page.getPages());
        pageResult.setCurrent(page.getCurrent());
        pageResult.setTotal(page.getTotal());
        if(StringUtils.checkValNotNull(page.getRecords())){
            pageResult.setRecords(page.getRecords());
        }
        return pageResult;
    }





}
