package com.gmcc.aliface.Service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;

public interface AlipayFaceService {

    String generateUniqueId(HttpServletRequest request);

    Object alipayInfoQuery(HttpServletRequest request) throws AlipayApiException;
}
