
package com.dyys.hr.utils;

import com.alibaba.fastjson.JSONObject;
import com.dyys.hr.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 响应数据
 *
 * @author
 * @since 1.0.0
 */
@ApiModel(value = "响应")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编码：1表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0表示成功，其他值表示失败")
    private int code = 0;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success(){
        return code == 0 ? true : false;
    }

    public Result<T> success(String msg) {
        this.code = 0;
        this.msg = msg;
        return this;
    }

    /**
     * 2022-11-24新增
     * @param msg
     * @param data
     * @return
     */
    public Result<T> success(String msg, T data) {
        this.code = 0;
        this.msg = msg;
        if (data != null) {
            this.data = data;
        }
        return this;
    }

    public Result<T> error() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return this;
    }

    /**
     * 2022-11-24新增
     * @param msg
     * @param data
     * @return
     */
    public Result<T> error(String msg, T data) {
        this.code = ErrorCode.CHECK_ERR_DATA;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return JSONObject.toJSON(this).toString();
    }
}
