package com.gmcc.aliface.Service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;

public interface AlipayFaceService {

    String generateUniqueId(HttpServletRequest request) throws Exception;

    Object alipayInfoQuery(HttpServletRequest request) throws AlipayApiException;

    Object alipayCheckinNotify(HttpServletRequest request) throws AlipayApiException;
}
