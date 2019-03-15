package com.xcl.webchat.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author :xiaochanglu
 * @Description :时间工具类
 * @date :2019/1/8 19:19
 */
public class DateUtils {
    /**
     * the milli seconds of a second
     * 一秒转换为毫秒数
     */
    public static final long SECONDMILLI = 1000;
    /**
     * the milli seconds of a minute
     * 一分钟转换为毫秒数
     */
    public static final long MINUTEMILLI = 60 * SECONDMILLI;
    /**
     * the milli seconds of an hour
     * 一小时转换为毫秒数
     */
    public static final long HOURMILLI = 60 * MINUTEMILLI;
    /**
     * the milli second of a day
     * 一天转换为毫秒数
     */
    public static final long DAYMILLI = 24 * HOURMILLI;
    /**
     * one day start time
     */
    public static final String STTIME = " 00:00:00";
    /**
     * one day end time
     */
    public static final String EDTIME = " 23:59:59";
    /**
     * flag before
     */
    public static final transient int BEFORE = 1;
    /**
     * flag after
     */
    public static final transient int AFTER = 2;
    /**
     * flag equal
     */
    public static final transient int EQUAL = 3;

    public static final String DATEFORMAT1 = "yyyyMMddHHmmss";
    public static final String DATEFORMAT2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT3 = "yyyy年MM月dd日 HH时mm分ss秒";
    /**
     * @Description  ：时间转换为字符串
     * 格式:
     * yyyy年MM月dd日 HH:mm:ss
     * yyyy-MM-dd HH:mm:ss
     * yyyyMMddHHmmss
     * @author       : xcl
     * @param        : [localDateTime, format] [时间,转换格式]
     * @return       : java.lang.String
     * @date         : 2018/12/14 15:35
     */
    public static String dataToString(LocalDateTime localDateTime, String format){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(formatter);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Description  ：LocalDateTime时间转换为Long型
     * @author       : xcl
     * @param        : [localDateTime]
     * @return       : java.lang.Long
     * @date         : 2019/1/10 10:04
     */
    public static Long dataToLong(LocalDateTime localDateTime){
        return localDateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }
    /**
     * @Description  ：获取系统当前时间
     * @author       : xcl
     * @return       : java.time.LocalDateTime
     * @date         : 2019/1/10 10:08
     */
    public static LocalDateTime now(){
        return LocalDateTime.now();
    }
    /**
     * @Description  ：字符串转换为时间
     * 格式:
     * yyyy年MM月dd日 HH:mm:ss
     * yyyy-MM-dd HH:mm:ss
     * yyyyMMddHHmmss
     * @author       : xcl
     * @param        : [localDateTime, format] [时间,转换格式]
     * @return       : java.lang.String
     * @date         : 2018/12/14 15:35
     */
    public static LocalDateTime strToData(String time, String format){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Description  ：java旧时间 Date 转换为新时间 LocalDateTime
     * @author       : xcl
     * @param        : [date]
     * @return       : java.time.LocalDateTime
     * @date         : 2019/1/8 19:48
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    /**
     * @Description  ：java新时间 LocalDateTime转换为 旧时间 Date
     * @author       : xcl
     * @param        : [localDateTime]
     * @return       : java.util.Date
     * @date         : 2019/1/8 19:50
     */
    public static Date localDateTimeToate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     * @Description  ：根据时间获取当月有多少天数
     * @author       : xcl
     * @param        : [localDateTime]
     * @return       : int
     * @date         : 2018/12/29 10:21
     */
    public static int getActualMaximum(LocalDateTime localDateTime) {
        return localDateTime.getMonth().length(localDateTime.toLocalDate().isLeapYear());
    }
    /**
     * @Description  ：计算两个时间LocalDateTime相差的天数，不考虑日期前后
     * @author       : xcl
     * @param        : [before, after]
     * @return       : int 返回结果>=0
     * @date         : 2018/12/29 10:23
     */
    public static int getAbsTimeDiffDay(LocalDateTime before, LocalDateTime after) {
        return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getDays());
    }
    /**
     * @Description  ：计算两个时间LocalDateTime相差的月数，不考虑日期前后
     * @author       : xcl
     * @param        : [before, after]
     * @return       : int 返回结果>=0
     * @date         : 2018/12/29 10:24
     */
    public static int getAbsTimeDiffMonth(LocalDateTime before, LocalDateTime after) {
        return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getMonths());
    }
    /**
     * @Description  ：计算两个时间LocalDateTime相差的年数，不考虑日期前后
     * @author       : xcl
     * @param        : [before, after]
     * @return       : int 返回结果>=0
     * @date         : 2018/12/29 10:42
     */
    public static int getAbsTimeDiffYear(LocalDateTime before, LocalDateTime after) {
        return Math.abs(Period.between(before.toLocalDate(), after.toLocalDate()).getYears());
    }
    /**
     * @Description  ：增加或减少年/月/周/天/小时/分/秒数
     * @author       : xcl
     * @param        : [localDateTime, chronoUnit, num] [例：ChronoUnit.DAYS,]
     * @return       : java.time.LocalDateTime
     * @date         : 2018/12/29 10:50
     */
    public static LocalDateTime addTime(LocalDateTime localDateTime, ChronoUnit chronoUnit, int num) {
        return localDateTime.plus(num, chronoUnit);
    }
    /**
     * @Description  ：比较两个时间LocalDateTime大小
     * @author       : xcl
     * @param        : [time1, time2]
     * @return       : int  1:第一个比第二个大；3：第一个与第二个相同；2：第一个比第二个小
     * @date         : 2018/12/29 10:55
     */
    public static int compareTwoTime(LocalDateTime time1, LocalDateTime time2) {
        if (time1.isAfter(time2)) {
            return BEFORE;
        } else if (time1.isBefore(time2)) {
            return AFTER;
        } else {
            return EQUAL;
        }
    }
    /**
     * @Description  ：判断指定时间now是否在时间范围内
     * @author       : xcl
     * @param        : [startTime, endTime,now]
     * @return       : boolean
     * @date         : 2018/12/29 10:57
     */
    public static boolean isTimeInRange(LocalDateTime startTime, LocalDateTime endTime,
        LocalDateTime now) throws Exception {
        return (startTime.isBefore(now) && endTime.isAfter(now)) || startTime.isEqual(now) || endTime.isEqual(now);
    }

//    public static void main(String[] args) {
//        String back1 = dataToString(LocalDateTime.now(),DateUtils.DATEFORMAT1);
//        System.out.println(back1);
//        String back2 = dataToString(LocalDateTime.now(),DateUtils.DATEFORMAT2);
//        System.out.println(back2);
//        String back3 = dataToString(LocalDateTime.now(),DateUtils.DATEFORMAT3);
//        System.out.println(back3);
//    }
}
