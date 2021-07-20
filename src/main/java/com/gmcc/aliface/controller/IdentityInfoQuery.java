package com.gmcc.aliface.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.gmcc.aliface.response.FaceUser;
import com.gmcc.aliface.response.Institution;
import com.gmcc.aliface.response.JsonRootBean;
import com.gmcc.aliface.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value="/api/v1/aliUserReigstor")
public class IdentityInfoQuery {

    final String aliPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl738H533ua5pVm9Y3/syjosi9y4BxprQjp4fYvqf5WpTtPPUW8BeSmdSRHT58QMJ+M68w7o65MAHTMXiX53C5YT0Kb4zRrdBb1EUm7WCvdTXS7NxOUTybhinW6UIjZTE2OhYbt9/wNc90tWBYMBaTWyPEzh6LlkOGG6yum/Cyy7Hy72Z9LT4F1va9GXK2SFFMhuWIxgIGxn78wKF7kLfpUXBUOIsZZJwNWRseIGpTBpRJelgEXatBLv3vGDMqCClhmfXQW1K6l3jXURdzOkCSKeL7/E4Z3bLN+9Kx6Q4i6v51wY9q2s1PsYeRUvRkPsR9LeMAtvnqbIiJRzNkPsMZwIDAQAB";

    final String appPrivKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1LKxhVY0wJtbebHxxt6xr8ujrpT9MXFTIy2QAy7s/QZgDgOp0fUnufSkGnAOis8ybD3OGCDNCN4HLhEhIF0TXnMYGigff97A5LS9qtrIYzroCX6q8nY9tnkNqSGjUg/sqTMhnYus6kABLu6f+zhQmivqIDB654XZEy4gc7tNQK/E4086ZVORj5a5lsE52L9mQxvRZNptdJu6FxnutpAFaQY51PQFsbTybGFY9jHiUozmmc6UFY5eo0vh21I7xyiJ4ObV8AoWxFfJd/IogK1sGPnhfWFaWZlcPpKVxMI0SmHLBVvVE+cGozYhzQ9pO8eb0/SypCOlE4unCDeqWSmttAgMBAAECggEAaXmqmbf+zIuwCCnMgT/XR7lRVO8NXN1cFtIiVDxHtNnxMAj7dfoC8q83FTv6+dwHQDbMoLlhbil2jQ9kLWQulgAVYXU2hHNkysS9mDjokny32e3sLobkG4NJ6DRjo2V68fNlsJrsK1nucii78yHypUW/+ThlIv5Zpk9/3KhFqaQPvL68mIt/AxttpvxkIGxTpfDsIiB55nXuA+my1txanBQGOph/bJjoVEonXaI5WXeDqyvrksMeBL1h4uUyQxbEhqwrdWrI+nGcWD1RSEFsDWcLk59r919BKvLudeq99fgRmIs4JjsX6NDQlJjgrgcQXlmuDPKVxWK4PbsAPxKzAQKBgQDrOn7AYK0iHzEL7XMLURmtc9iyYnYQZtBewfikGAa8mqHwRpXQvkF6iRWghxOUtsOBppDilI9mYSf9w48g9RJAuXW8Xhfh/OTFjuNVtddI9bZ2Kz6YmDCvpA/6vFBE6qLDiBu1DAc50A+QV03ftuWl/fYsKre314cnm65DWLaloQKBgQDFLD/6bsLlSejKVe0FVIHjePhICSjIpP3RCZKr3796V6ISUnbd+7gGGSIL02ugD6B6uPDRZg/c5n1E2n0SW7hTxdHEbArGqpR1iudh0NH+xoenM+futpG07p6Z34BLXzheLiexvXDz9AFWjuaa72HkoXO/zuVS7AUZW+Go3SBaTQKBgHdIDVhebc0cxhFHUoxNjtjPIvaqSTZd/JHgiMYlTN09CJtAH95fa/LIKwhhFsPVveQz48qSDPmckNv029KZk6zwnsxrZm75f8TShWNEN6xOtTzNBwv4ONAIQPYjogygcvL2RR6Uv+FIKWxHaTzlkSSQWNHiw5DdITMrlb2e8dehAoGAIzKB/T+WdP3bm3r5Q7FROn0WvS09ZYvmb1FpKFtZwAoyuViVNY1AzX3hEQFdvgXsHNw0yPySKlnO1OK33vYWkoXGq2yqA8rhk5YHNfPj+LoR7ywZ5AgdPJI4J7m5GjtWQaG1nkn2BuZw8rWxvsiqzfhwRDY3w7S0OFGpl85ZfyUCgYEAoHzVnhvN16iq4TFHFfH/oE7ZwKQvKcPQq5hMF7sj4CYux7PPPIrVFgkp5SYK1Wfp8tNcAV1HWh5VHxVfF52eg5R8ukg+K9XHOwRMmA14YZh54tjlzgztAFrtK/Or3bGilrb6980txGYiDraMKKLTGqrMFcLLaYBJ59JQlfF0jUA=";

    @PostMapping("/regist")
    public Object regist(HttpServletRequest request) throws AlipayApiException {
        Map requestParams = request.getParameterMap();
        Map<String, String> sortedParams = new TreeMap<String, String>();
        String sign = "";
        for(Object key : requestParams.keySet()) {
            String[] values = (String[])requestParams.get(key);
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
        institutions.add( institution);
        faceUser.setInstitution_list(institutions);

        Response response = new Response();
        response.setCode("10000");
        response.setMsg("Success");
        response.setFace_user_list(new ArrayList<FaceUser>());
        response.getFace_user_list().add(faceUser);

        System.out.println(JSON.toJSON(response));

        String responseSign = AlipaySignature.sign(JSON.toJSON(response).toString(), appPrivKey,  StandardCharsets.UTF_8.name(), AlipayConstants.SIGN_TYPE_RSA2);

        jsonBean.setSign(responseSign);
        jsonBean.setResponse(response);

        return jsonBean;

//        LocalDateTime now = LocalDateTime.now();
//        String nowFormat = now.format(dateTimeFormatter);
//        AliRegistor.setUniqueId(nowFormat);
//
//        int i = aliUserMapper.insertUser(AliRegistor);
//
//        return AliRegistor.getUniqueId();
    }
}
