package com.gmcc.aliface.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.gmcc.aliface.Service.AlipayFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "/api/v1/aliUserReigstor", produces="application/json;charset=UTF-8")
public class AlipayFaceBackend {

    @Autowired
    private AlipayFaceService alipayFaceService;

    @PostMapping("/regist" )
    public void regist(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam String method) throws Exception {
        System.out.println("method:---" + method);
        Object returnObj = null;
        switch (method) {
            case "spi.alipay.commerce.frservice.generateUniqueId":
                returnObj = alipayFaceService.generateUniqueId(request);
                break;
            case "spi.alipay.commerce.frservice.identityinfo.query":
                returnObj = alipayFaceService.alipayInfoQuery(request);
                break;
            case "spi.alipay.commerce.frservice.checkin.notify":
                returnObj = alipayFaceService.alipayCheckinNotify(request);
                break;
            default:
                break;
        }

        System.out.println("++++++++++++"+returnObj+"++++++++++++++");
        System.out.println("++++++++++++"+JSON.toJSONString(returnObj)+"++++++++++++++");


        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "application/json;charset=UTF-8");
        byte[] dataByteArr = ((String)returnObj).getBytes("UTF-8");
        outputStream.write(dataByteArr);
        outputStream.flush();
        outputStream.close();
    }
}
