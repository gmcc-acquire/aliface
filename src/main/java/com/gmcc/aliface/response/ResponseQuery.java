/**
 * Copyright 2021 bejson.com
 */
package com.gmcc.aliface.response;

import java.util.List;

/**
 * Auto-generated: 2021-07-20 15:34:19
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ResponseQuery {

    private String code;
    private String msg;
    private String unique_id;
    private List<FaceUser> face_user_list;
    private String sub_code;
    private String sub_msg;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setFace_user_list(List<FaceUser> face_user_list) {
        this.face_user_list = face_user_list;
    }

    public List<FaceUser> getFace_user_list() {
        return face_user_list;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }
}