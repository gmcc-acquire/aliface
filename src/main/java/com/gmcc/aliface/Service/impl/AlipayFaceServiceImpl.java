package com.gmcc.aliface.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.gmcc.aliface.Service.AlipayFaceService;
import com.gmcc.aliface.SnowFlake;
import com.gmcc.aliface.response.FaceUser;
import com.gmcc.aliface.response.Institution;
import com.gmcc.aliface.response.JsonRootBean;
import com.gmcc.aliface.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
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

    @Override
    public String generateUniqueId(HttpServletRequest request) {
        return SnowFlake.snowFlakeGenerate();
    }

    @Override
    public Object alipayInfoQuery(HttpServletRequest request) throws AlipayApiException {
        Map requestParams = request.getParameterMap();
        Map<String, String> sortedParams = new TreeMap<>();

        for (Object key : requestParams.keySet()) {
            String[] values = (String[]) requestParams.get(key);
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
        Institution institution = new Institution();
        institution.setId("2088200144875724");
        institution.setFace_in_time("2020-07-09 16:30:32");
        FaceUser faceUser = new FaceUser();
        faceUser.setBiz_id("15984406295888074");
        faceUser.setFace_id("2088200455641481");
        faceUser.setName("诸葛亮");
        faceUser.setUser_type("1");
        faceUser.setCert_type("A");
        faceUser.setCert_no("2088200455641481");
        faceUser.setAlipay_uid_selector("0");
        faceUser.setExt_info("{\"key\":\"value\"}");
        List<Institution> institutions = new ArrayList();
        institutions.add(institution);
        faceUser.setInstitution_list(institutions);

        Response response = new Response();
        response.setCode("10000");
        response.setMsg("Success");
        response.setFace_user_list(new ArrayList<FaceUser>());
        response.getFace_user_list().add(faceUser);

        System.out.println(JSON.toJSON(response));

        String responseSign = AlipaySignature.sign(JSON.toJSON(response).toString(), appPrivKey, StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);

        jsonBean.setSign(responseSign);
        jsonBean.setResponse(response);

        return jsonBean;
    }

}
