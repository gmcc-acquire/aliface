package com.gmcc.aliface.mapper;

import com.gmcc.aliface.entity.AliRegistor;

public interface AliUserMapper {

    int insertUser(AliRegistor aliRegistor);

    String getUniqueId(String aesIdCard);

    AliRegistor getUserInfo(String unique_id);
}
