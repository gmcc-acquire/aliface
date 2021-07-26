package com.gmcc.aliface.response;

public class JsonRootBean<T> {

    private T response;
    private String sign;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

}