package com.gmcc.aliface.controller;

import com.gmcc.aliface.entity.AliRegistor;
import com.gmcc.aliface.mapper.AliUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@RestController
//@RequestMapping(value="/api/v1/aliUserReigstor", method = RequestMethod.POST)
public class AliUserRegistor {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Autowired
    AliUserMapper aliUserMapper;

    @PostMapping("/regist")
    public Object regist(AliRegistor AliRegistor) {

        LocalDateTime now = LocalDateTime.now();
        String nowFormat = now.format(dateTimeFormatter);
        AliRegistor.setUniqueId(nowFormat);

        int i = aliUserMapper.insertUser(AliRegistor);

        return AliRegistor.getUniqueId();
    }

}
