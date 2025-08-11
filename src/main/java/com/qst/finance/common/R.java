package com.qst.finance.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 返回结果集
 *
 * @author javadog
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 全参数方法
     *
     * @param code    状态码
     * @param data    返回数据
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    private static <T> R<T> response(Integer code, String message, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(message);
        r.setData(data);
        return r;
    }

    /**
     * 全参数方法
     *
     * @param code    状态码
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    private static <T> R<T> response(Integer code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(message);
        return r;
    }

    /**
     * 成功返回（无参）
     *
     * @param <T> 泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success() {
        return response(HttpStatusEnum.SUCCESS.getCode(), HttpStatusEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回（枚举参数）
     *
     * @param httpResponseEnum 枚举参数
     * @param <T>              泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(HttpStatusEnum httpResponseEnum) {
        return response(httpResponseEnum.getCode(), httpResponseEnum.getMessage());
    }

    /**
     * 成功返回（状态码+返回信息）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(Integer code, String message) {
        return response(code, message);
    }

    /**
     * 成功返回（返回信息 + 数据）
     *
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(String message, T data) {
        return response(HttpStatusEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回（状态码+返回信息+数据）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(Integer code, String message, T data) {


        return response(code, message, data);
    }

    /**
     * 成功返回（数据）
     *
     * @param data 数据
     * @param <T>  泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(T data) {
        return response(HttpStatusEnum.SUCCESS.getCode(), HttpStatusEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回（返回信息）
     *
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> success(String message) {


        return response(HttpStatusEnum.SUCCESS.getCode(), message, null);
    }

    /**
     * 失败返回（无参）
     *
     * @param <T> 泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail() {
        return response(HttpStatusEnum.ERROR.getCode(), HttpStatusEnum.ERROR.getMessage(), null);
    }

    /**
     * 失败返回（枚举）
     *
     * @param httpResponseEnum 枚举
     * @param <T>              泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(HttpStatusEnum httpResponseEnum) {
        return response(httpResponseEnum.getCode(), httpResponseEnum.getMessage());
    }

    /**
     * 失败返回（状态码+返回信息）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(Integer code, String message) {
        return response(code, message);
    }

    /**
     * 失败返回（返回信息+数据）
     *
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(String message, T data) {
        return response(HttpStatusEnum.ERROR.getCode(), message, data);
    }

    /**
     * 失败返回（状态码+返回信息+数据）
     *
     * @param code    状态码
     * @param message 返回消息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(Integer code, String message, T data) {
        return response(code, message, data);
    }

    /**
     * 失败返回（数据）
     *
     * @param data 数据
     * @param <T>  泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(T data) {
        return response(HttpStatusEnum.ERROR.getCode(), HttpStatusEnum.ERROR.getMessage(), data);
    }

    /**
     * 失败返回（返回信息）
     *
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link R <T>}
     */
    public static <T> R<T> fail(String message) {
        return response(HttpStatusEnum.ERROR.getCode(), message, null);
    }
}
