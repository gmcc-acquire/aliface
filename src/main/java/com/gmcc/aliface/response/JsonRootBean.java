package com.gmcc.aliface.response;

public class JsonRootBean {

    private Response response;
    private String sign;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

}