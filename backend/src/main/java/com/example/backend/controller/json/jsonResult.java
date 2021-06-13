package com.example.backend.controller.json;

public class jsonResult<T> {
    private T data;
    private String msg;

    //private Object totalNum;

    /*public Object getTotalNum() {
        return totalNum;
    }*/

    /*public void setTotalNum(Object totalNum) {
        this.totalNum = totalNum;
    }*/

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“操作成功！”
     */
    public jsonResult() {
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param msg
     */
    public jsonResult(String msg) {
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为 0，默认提示信息为“操作成功！”
     * @param data
     */
    public jsonResult(T data) {
        this.data = data;
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为 0，人为指定提示信息
     * @param data
     * @param msg
     */
    /*public jsonResult(T data, String msg,Object totalNum) {
        this.totalNum=totalNum;
        this.data = data;
        this.msg = msg;
    }*/


    public jsonResult(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public void printResult(){
        System.out.println("data:"+this.data);
        System.out.println("message:"+this.msg);
    }

    public void printmsg(){
        System.out.println(this.msg);
    }
}
