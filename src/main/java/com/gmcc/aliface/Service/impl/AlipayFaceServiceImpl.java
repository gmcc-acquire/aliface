package com.gmcc.aliface.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.gmcc.aliface.Service.AlipayFaceService;
import com.gmcc.aliface.entity.AliRegistor;
import com.gmcc.aliface.mapper.AliUserMapper;
import com.gmcc.aliface.response.*;
import com.gmcc.aliface.utils.AESutil;
import com.gmcc.aliface.utils.GetAge;
import com.gmcc.aliface.utils.SnowFlake;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AlipayFaceServiceImpl implements AlipayFaceService {
    protected Log logger = LogFactory.getLog(this.getClass());

    @Value("${aliface.aliPubKey}")
    private String aliPubKey;

    @Value("${aliface.appPrivKey}")
    private String appPrivKey;

    @Value("${aliface.aesKey}")
    private String aesKey;

    @Value("${aliface.institutionCode}")
    private String institutionCode;

    @Autowired
    private AliUserMapper aliUserMapper;

    @Override
    public String generateUniqueId(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String idcard = request.getParameter("idcard");
        // 加密
        String aesIdCard = AESutil.aesEncrypt(idcard, aesKey);
        AliRegistor aliRegistor = new AliRegistor();
        aliRegistor.setUserType("0");
        aliRegistor.setCertType("1");
        aliRegistor.setCertNo(aesIdCard);
        aliRegistor.setUserName(name);
        String uniqueId = SnowFlake.snowFlakeGenerate();
        aliRegistor.setUniqueId(uniqueId);
        int count = 0;
        try {
            count = aliUserMapper.insertUser(aliRegistor);
        } catch (DuplicateKeyException e) {
            // 返回库里已经存储的uniqueId
            uniqueId = aliUserMapper.getUniqueId(aesIdCard);
        }

        System.out.println(uniqueId);
        return uniqueId;
    }

    @Override
    public Object alipayInfoQuery(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> sortedParams = new TreeMap<>();

        for (Object key : requestParams.keySet()) {
            String[] values = requestParams.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            System.out.println("key : " + key.toString() + "------- value : " + valueStr);
            sortedParams.put(key.toString(), valueStr);
        }
//        boolean verify_result = false;
//        try {
//            verify_result = AlipaySignature.rsaCheckV1(sortedParams, aliPubKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
        boolean verify_result = true;
        System.out.println(verify_result);
        JsonRootBean jsonBean = new JsonRootBean();

        // 验签失败 返回错误
        if (!verify_result) {
            ResponseQuery responseQuery = new ResponseQuery();
            responseQuery.setCode("40004");
            responseQuery.setMsg("Business Failed");
            responseQuery.setSub_code("ISV_VERIFICATION_FAILED");
            responseQuery.setSub_msg("验签失败");
            String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseQuery), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
            jsonBean.setSign(responseSign);
            jsonBean.setResponse(responseQuery);
            System.out.println(JSONObject.toJSONString(jsonBean));
            return JSONObject.toJSONString(jsonBean);
        }

        // 获取用户信息
        AliRegistor aliRegistor = aliUserMapper.getUserInfo(sortedParams.get("unique_id"));
        // null 返回错误
        if (aliRegistor == null) {
            ResponseQuery responseQuery = new ResponseQuery();
            responseQuery.setCode("40004");
            responseQuery.setMsg("Business Failed");
            responseQuery.setSub_code("NO_DATA");
            responseQuery.setSub_msg("查询结果为空");
            String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseQuery), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
            jsonBean.setSign(responseSign);
            jsonBean.setResponse(responseQuery);
            System.out.println(JSONObject.toJSONString(jsonBean));
            return JSONObject.toJSONString(jsonBean);
        }

        // 解密
        String certNoDecode = AESutil.aesDecrypt(aliRegistor.getCertNo(), aesKey);
        // 人脸入库机构ID集合
        Institution institution = new Institution();
        institution.setId(institutionCode);
        institution.setFace_in_time("2020-07-29 16:30:32");

        final String bizId = SnowFlake.snowFlakeGenerate();
        FaceUser faceUser = new FaceUser();
        faceUser.setBiz_id(bizId);
        faceUser.setFace_id(certNoDecode);
        faceUser.setName(aliRegistor.getUserName());
        faceUser.setUser_type(aliRegistor.getUserType());
        faceUser.setCert_type(aliRegistor.getCertType());
        faceUser.setCert_no(certNoDecode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int age = GetAge.getAge(sdf.parse(certNoDecode.substring(6, 14)));
        if (age > 60) {
            faceUser.setAlipay_uid_selector("1");
        } else {
            faceUser.setAlipay_uid_selector("0");
        }
        faceUser.setExt_info("{}");
        List<Institution> institutions = new ArrayList();
        institutions.add(institution);
        faceUser.setInstitution_list(institutions);

        // 返回成功
        ResponseQuery responseQuery = new ResponseQuery();
        responseQuery.setCode("10000");
        responseQuery.setMsg("Success");
        responseQuery.setUnique_id(aliRegistor.getUniqueId());
        responseQuery.setFace_user_list(new ArrayList<>());
        responseQuery.getFace_user_list().add(faceUser);

        String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseQuery), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
        jsonBean.setSign(responseSign);
        jsonBean.setResponse(responseQuery);

        // 存储发送的报文
        aliUserMapper.insertQueryMsg(bizId, sortedParams.get("unique_id"), JSONObject.toJSONString(jsonBean));
        System.out.println(JSONObject.toJSONString(jsonBean));
        return JSONObject.toJSONString(jsonBean);
    }

    @Override
    public Object alipayCheckinNotify(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> sortedParams = new TreeMap<>();

        for (Object key : requestParams.keySet()) {
            String[] values = requestParams.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            System.out.println("key : " + key.toString() + "------- value : " + valueStr);
            sortedParams.put(key.toString(), valueStr);
        }
        boolean verify_result = false;
        try {
            verify_result = AlipaySignature.rsaCheckV1(sortedParams, aliPubKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(verify_result);
        JsonRootBean jsonBean = new JsonRootBean();
        // 验签失败 返回错误
        if (!verify_result) {
            ResponseNotify responseNotify = new ResponseNotify();
            responseNotify.setCode("40004");
            responseNotify.setMsg("Business Failed");
            responseNotify.setSub_code("ISV_VERIFICATION_FAILED");
            responseNotify.setSub_msg("验签失败");
            String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseNotify), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
            jsonBean.setSign(responseSign);
            jsonBean.setResponse(responseNotify);
            System.out.println(JSONObject.toJSONString(jsonBean));
            return JSONObject.toJSONString(jsonBean);
        }

        // 入库
        int count = 0;
        try {
            count = aliUserMapper.insertNotifyRequest(sortedParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(count == 0){
            // 入库错误
            ResponseNotify responseNotify = new ResponseNotify();
            responseNotify.setCode("40004");
            responseNotify.setMsg("Business Failed");
            responseNotify.setSub_code("BIZ_ERROR");
            responseNotify.setSub_msg("业务异常");
            String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseNotify), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);
            jsonBean.setSign(responseSign);
            jsonBean.setResponse(responseNotify);
            System.out.println(JSONObject.toJSONString(jsonBean));
            return JSONObject.toJSONString(jsonBean);
        }

        ResponseNotify responseNotify = new ResponseNotify();
        responseNotify.setCode("10000");
        responseNotify.setMsg("Success");
        responseNotify.setResult(true);

        System.out.println(JSON.toJSON(responseNotify));

        String responseSign = AlipaySignature.sign(JSONObject.toJSONString(responseNotify), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);

        jsonBean.setSign(responseSign);
        jsonBean.setResponse(responseNotify);

        // 存储发送的报文
        aliUserMapper.insertNotifyMsg(sortedParams.get("face_id"), sortedParams.get("biz_id"),
                sortedParams.get("unique_id"), JSONObject.toJSONString(jsonBean));
        System.out.println(JSONObject.toJSONString(jsonBean));
        return JSONObject.toJSONString(jsonBean);
    }
}
