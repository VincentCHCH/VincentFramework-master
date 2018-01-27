package com.vincent.network.http;

public class ResponseResult<T> {

    /**
     * 返回结果标识
     */
    private int result;

    /**
     * 结果标识所对应的信息
     */
    private String msg;

    /**
     * 结果数据
     */
    private T data;


    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
