package com.bosha.user.server.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取当年的开始时间戳
     *
     * @return
     */
    public static Date getBeginTime(int year) {
        YearMonth yearMonth = YearMonth.of(year, 1);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }

    public static Date getEndTime(int year) {
        YearMonth yearMonth = YearMonth.of(year, 12);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    public static void main(String[] args) {
        int year = 2016;
        Date beginTime = getBeginTime(year);
        System.out.println(sdf.format(beginTime));
        Date endTime = getEndTime(year);
        System.out.println(sdf.format(endTime));

    }

}
