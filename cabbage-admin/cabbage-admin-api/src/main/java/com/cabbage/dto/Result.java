package com.cabbage.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("错误信息")
    private String ero;

    @ApiModelProperty("结果数据")
    private T data;

    public static <D> Result<D> ok(D data) {
        return new Result<>(0, "成功", "", data);
    }

    public static Result<Void> ok() {
        return new Result<>(0, "成功", "", null);
    }

    public <D> Result<D> paramEro(String eroMes) {
        return new Result<>(-2, "参数异常", eroMes, null);
    }

    public <D> Result<D> ero(String eroMes) {
        return new Result<>(-1, "操作失败", eroMes, null);
    }

    public static <D> Result<D> ero(String msg, String eroMsg) {
        return new Result<>(-1, msg, eroMsg, null);
    }

    public static <D> Result<D> ero(Integer code, String msg, String eroMsg, D data) {
        return new Result<>(code, msg, eroMsg, data);
    }

}

