package com.gmcc.aliface.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：JiBenWuJie
 * @date ：Created in 2021/7/26 14:53
 * @description：
 */
public class GetAge {
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR); //当前年份
        int monthNow = cal.get(Calendar.MONTH); //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);//当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    //当前日期在生日之前，年龄减一
                    age--;
                }
            } else {
                //当前月份在生日之前，年龄减一
                age--;
            }
        }
        return age;
    }
}
