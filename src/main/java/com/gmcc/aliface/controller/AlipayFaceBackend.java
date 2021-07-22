package com.gmcc.aliface.controller;

import com.alipay.api.AlipayApiException;
import com.gmcc.aliface.Service.AlipayFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/aliUserReigstor")
public class AlipayFaceBackend {

    @Autowired
    private AlipayFaceService alipayFaceService;

    @PostMapping("/regist")
    public Object regist(HttpServletRequest request, @RequestParam String method) throws AlipayApiException {
        Object returnObj = null;
        switch (method) {
            case "spi.alipay.commerce.frservice.generateUniqueId":
                returnObj = alipayFaceService.generateUniqueId(request);
                break;
            case "spi.alipay.commerce.frservice.identityinfo.query":
                returnObj = alipayFaceService.alipayInfoQuery(request);
                break;
            case "spi.alipay.commerce.frservice.checkin.notify":
                break;
            default:
                break;
        }

        return returnObj;
    }
}
