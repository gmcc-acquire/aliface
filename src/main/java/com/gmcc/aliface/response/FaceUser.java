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
public class FaceUser {

    private String biz_id;
    private String face_id;
    private String name;
    private String user_type;
    private String cert_type;
    private String cert_no;
    private List<Institution> institution_list;
    private String alipay_uid_selector;
    private String ext_info;
    public void setBiz_id(String biz_id) {
         this.biz_id = biz_id;
     }
     public String getBiz_id() {
         return biz_id;
     }

    public void setFace_id(String face_id) {
         this.face_id = face_id;
     }
     public String getFace_id() {
         return face_id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setUser_type(String user_type) {
         this.user_type = user_type;
     }
     public String getUser_type() {
         return user_type;
     }

    public void setCert_type(String cert_type) {
         this.cert_type = cert_type;
     }
     public String getCert_type() {
         return cert_type;
     }

    public void setCert_no(String cert_no) {
         this.cert_no = cert_no;
     }
     public String getCert_no() {
         return cert_no;
     }

    public void setInstitution_list(List<Institution> institution_list) {
         this.institution_list = institution_list;
     }
     public List<Institution> getInstitution_list() {
         return institution_list;
     }

    public void setAlipay_uid_selector(String alipay_uid_selector) {
         this.alipay_uid_selector = alipay_uid_selector;
     }
     public String getAlipay_uid_selector() {
         return alipay_uid_selector;
     }

    public void setExt_info(String ext_info) {
         this.ext_info = ext_info;
     }
     public String getExt_info() {
         return ext_info;
     }

}