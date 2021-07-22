package com.gmcc.aliface.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.gmcc.aliface.Service.AlipayFaceService;
import com.gmcc.aliface.response.FaceUser;
import com.gmcc.aliface.response.Institution;
import com.gmcc.aliface.response.JsonRootBean;
import com.gmcc.aliface.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/api/v1/aliUserReigstor")
public class AlipayFaceBackend {


    @Autowired
    private AlipayFaceService alipayFaceService;

    @PostMapping("/regist")
    public Object regist(HttpServletRequest request, @RequestParam String method) throws AlipayApiException {
        Object returnObj = null;
        switch (method) {
            case "alipay.trade.generateUniqueId":
                returnObj = alipayFaceService.generateUniqueId(request);
                break;
            case "":
                returnObj = alipayFaceService.alipayInfoQuery(request);
                break;
            case "1":
                break;
            default:
                int a = 1;
                break;
        }

        return returnObj;
    }
}
