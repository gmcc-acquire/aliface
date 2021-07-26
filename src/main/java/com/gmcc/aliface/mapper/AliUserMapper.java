package com.gmcc.aliface.mapper;

import com.gmcc.aliface.entity.AliRegistor;
import org.apache.ibatis.annotations.Param;

public interface AliUserMapper {

    int insertUser(AliRegistor aliRegistor);

    String getUniqueId(String aesIdCard);

    AliRegistor getUserInfo(String unique_id);

    void insertQueryMsg(@Param("bizId") String bizId, @Param("queryMsg") String queryMsg);
}
