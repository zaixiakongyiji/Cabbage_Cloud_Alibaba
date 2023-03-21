package com.cabbage.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xppd 1239694214@qq.com
 * @date 2021/12/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> {
    private Integer code;
    /**
     * 前端显示参数
     */
    private String msg;
    private String ero;
    private T data;

    public static <D> ResultDTO<D> success(D data) {
        return new ResultDTO<D>(0, "成功", "", data);
    }

    public static ResultDTO<Void> success() {
        return new ResultDTO<>(0, "成功", "", null);
    }

    public static ResultDTO<Void> success$Msg(String msg) {
        return new ResultDTO<>(0, msg, "", null);
    }

    /**
     * 参数状态等异常
     *
     * @return ResultDTO
     */
    public static <D> ResultDTO<D> illegal(String msg) {
        return new ResultDTO<>(-2, msg, "参数异常", null);
    }

    /**
     * 系统错误
     *
     * @return ResultDTO
     */
    public static <D> ResultDTO<D> ero(String eroMsg) {
        return new ResultDTO<>(-1, "操作失败!", eroMsg, null);
    }


    public static <D> ResultDTO<D> ero(String msg, String eroMsg) {
        return new ResultDTO<>(-1, msg, eroMsg, null);
    }

    public static <D> ResultDTO<D> ero(Integer code, String msg, String eroMsg, D data) {
        return new ResultDTO<>(code, msg, eroMsg, data);
    }

    /**
     * 认证错误
     *
     * @return
     */
    public static <D> ResultDTO<D> noAuth() {
        return new ResultDTO<>(403, "请登录", "认证失败", null);
    }

    /**
     * 认证错误
     *
     * @param msg    前端显示消息
     * @param eroMsg 错消息
     * @return
     */

    public static <D> ResultDTO<D> noAuth(String msg, String eroMsg) {
        return new ResultDTO<>(403, msg, eroMsg, null);
    }

    public static <D> ResultDTO<D> noPermissions(String msg, String eroMsg) {
        return new ResultDTO<>(401, msg, eroMsg, null);
    }

    public static <D> ResultDTO<D> noPermissions() {
        return new ResultDTO<>(401, "权限不足", "权限不足", null);
    }

    public static <D> ResultDTO<D> other(int code, String msg, String eroMsg) {
        return new ResultDTO<>(code, msg, eroMsg, null);
    }

    public static <D> ResultDTO<D> otherWithData(int code, String msg, String eroMsg, D d) {
        return new ResultDTO<>(code, msg, eroMsg, d);
    }
}
