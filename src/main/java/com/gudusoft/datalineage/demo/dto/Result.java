package com.gudusoft.datalineage.demo.dto;


public class Result<T> {

    private int code;
    private String msg;
    private T data;
    private long srvTime = System.currentTimeMillis();
    public static  <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    public static  <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
    public static  <T> Result<T> error(int code, String msg){
        return new Result<T>(code, msg);
    }
    private Result(T data) {
        this.code = CodeMsg.SUCCESS.getCode();
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
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
}