package com.gmcc.aliface.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckinNotify {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        final Date dateForTable = new Date();
        calendar.setTime(dateForTable);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        if(calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            System.out.println(calendar.get(Calendar.MONTH) );
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            final String dumpTableNameNext = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
            System.out.println(dumpTableNameNext);
        }
    }
}
