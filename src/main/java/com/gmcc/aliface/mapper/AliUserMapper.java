package com.gmcc.aliface.mapper;

import com.gmcc.aliface.entity.AliRegistor;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AliUserMapper {

    int insertUser(AliRegistor aliRegistor);

    String getUniqueId(String aesIdCard);

    AliRegistor getUserInfo(String unique_id);

    void insertQueryMsg(@Param("bizId") String bizId,
                        @Param("uniqueId") String uniqueId,
                        @Param("queryMsg") String queryMsg);

    void insertNotifyMsg(@Param("faceId") String faceId,
                         @Param("bizId") String bizId,
                         @Param("uniqueId") String uniqueId,
                         @Param("notifyMsg") String notifyMsg);

    int insertNotifyRequest(@Param("requestMap") Map<String, String> sortedParams);
}
