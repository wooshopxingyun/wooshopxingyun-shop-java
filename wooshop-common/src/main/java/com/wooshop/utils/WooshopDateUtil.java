/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wooshop.utils;

import com.wooshop.security.exception.ServiceException;
import com.wooshop.utils.enums.WooshopConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: liaojinlong
 * @date: 2020/6/11 16:28
 * @apiNote: JDK 8  新日期类 格式化与字符串转换 工具类
 */
public class WooshopDateUtil {

    public static final DateTimeFormatter DFY_MD_HMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DFY_MD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime /
     * @return /
     */
    public static Long getTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timeStamp /
     * @return /
     */
    public static LocalDateTime fromTimeStamp(Long timeStamp) {
        return LocalDateTime.ofEpochSecond(timeStamp, 0, OffsetDateTime.now().getOffset());
    }

    /**
     * LocalDateTime 转 Date
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param localDateTime /
     * @return /
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转 Date
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param localDate /
     * @return /
     */
    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atTime(LocalTime.now(ZoneId.systemDefault())));
    }


    /**
     * Date转 LocalDateTime
     * Jdk8 后 不推荐使用 {@link Date} Date
     *
     * @param date /
     * @return /
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 日期 格式化
     *
     * @param localDateTime /
     * @param patten /
     * @return /
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, String patten) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(patten);
        return df.format(localDateTime);
    }

    /**
     * 日期 格式化
     *
     * @param localDateTime /
     * @param df /
     * @return /
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, DateTimeFormatter df) {
        return df.format(localDateTime);
    }

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime /
     * @return /
     */
    public static String localDateTimeFormatyMdHms(LocalDateTime localDateTime) {
        return DFY_MD_HMS.format(localDateTime);
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public String localDateTimeFormatyMd(LocalDateTime localDateTime) {
        return DFY_MD.format(localDateTime);
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormat(String localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.from(dateTimeFormatter.parse(localDateTime));
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormat(String localDateTime, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.from(dateTimeFormatter.parse(localDateTime));
    }

    /**
     * 字符串转 LocalDateTime ，字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime /
     * @return /
     */
    public static LocalDateTime parseLocalDateTimeFormatyMdHms(String localDateTime) {
        return LocalDateTime.from(DFY_MD_HMS.parse(localDateTime));
    }

    /**
     * 获取当前日期,指定格式
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    public static String nowDate(String DATE_FORMAT) {
        SimpleDateFormat dft = new SimpleDateFormat(DATE_FORMAT);
        return dft.format(new Date());
    }

    /**
     * 获取当前日期,指定格式
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    public static String nowDateTime(String DATE_FORMAT) {
        SimpleDateFormat dft = new SimpleDateFormat(DATE_FORMAT);
        return dft.format(new Date());
    }

    /**
     * 获取当前日期,指定格式
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    public static Date nowDateTime() {
        return strToDate(nowDateTimeStr(), WooshopConstants.DATE_FORMAT);
    }
    /**
     * 获取当前日期,指定格式
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    public static String nowDateTimeStr() {
        return nowDate(WooshopConstants.DATE_FORMAT);
    }

    //获取时间戳11位
    public static int getSecondTimestamp(String date){
        if (null == date) {
            return 0;
        }
        Date date1 = strToDate(date, WooshopConstants.DATE_FORMAT);
        if(date1 == null){
            return 0;
        }
        String timestamp = String.valueOf(date1.getTime()/1000);
        return Integer.parseInt(timestamp);
    }
    /**
     * parse a String to Date in a specifies fromat.
     *
     * @param dateStr
     * @param DATE_FORMAT
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String dateStr, String DATE_FORMAT) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return myFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 获取当前时间小时
     * @return 当前时间小时 默认24小时
     */
    public static int getCurrentHour(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 指定日期加上天数后的日期
     *
     * @param num     为增加的天数
     * @param newDate 创建时间
     * @return
     */
    public static final String addDay(String newDate, int num, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date currdate = format.parse(newDate);
            Calendar ca = Calendar.getInstance();
            ca.setTime(currdate);
            ca.add(Calendar.DATE, num);
            return format.format(ca.getTime());
        } catch (ParseException e) {
//            log.error("转化时间出错,", e);
            return null;
        }
    }


    /**
     * 指定日期加上天数后的日期
     *
     * @param num     为增加的天数
     * @param newDate 创建时间
     * @return
     */
    public static final String addDay(Date newDate, int num, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar ca = Calendar.getInstance();
        ca.setTime(newDate);
        ca.add(Calendar.DATE, num);
        return format.format(ca.getTime());
    }


    /**
     * convert a date to string in a specifies fromat.
     *
     * @param date
     * @param DATE_FORMAT
     * @return
     */
    public static String dateToStr(Date date, String DATE_FORMAT) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT);
        return myFormat.format(date);
    }

    /**
     * compare two date String with a pattern
     *
     * @param date1
     * @param date2
     * @param pattern
     * @return
     */
    public static int compareDate(String date1, String date2, String pattern) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(pattern);
        try {
            Date dt1 = DATE_FORMAT.parse(date1);
            Date dt2 = DATE_FORMAT.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前日期,指定格式
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    public static Date nowDateTimeReturnDate(String DATE_FORMAT) {
        SimpleDateFormat dft = new SimpleDateFormat(DATE_FORMAT);
        return strToDate(dft.format(new Date()), DATE_FORMAT);
    }

    /**
     * 获得某天0点时间
     * @return
     */
    public static Date getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 计算时间范围
     * @param data string 类型
     * @author Mr.Zhang
     * @since 2020-05-06
     * @return dateLimitUtilVo
     */
    public static DateLimitUtilVo getDateLimit(String data){
        //时间计算
        String startTime = null;
        String endTime = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT);
        String day = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT_START);
        String end = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT_END);

        if(!StringUtils.isBlank(data)){
            switch (data){
                case WooshopConstants.SEARCH_DATE_DAY:
                    startTime = day;
                    break;
                case WooshopConstants.SEARCH_DATE_YESTERDAY:
                    startTime = WooshopDateUtil.addDay(day, -1, WooshopConstants.DATE_FORMAT_START);
                    endTime = WooshopDateUtil.addDay(end, -1, WooshopConstants.DATE_FORMAT_END);
                    break;
                case WooshopConstants.SEARCH_DATE_LATELY_7:
                    startTime = WooshopDateUtil.addDay(day, -6, WooshopConstants.DATE_FORMAT_START);
                    break;
                case WooshopConstants.SEARCH_DATE_WEEK:
                    startTime = getWeekStartDay();
                    endTime = getWeekEndDay();
                    break;
                case WooshopConstants.SEARCH_DATE_PRE_WEEK:
                    startTime = getLastWeekStartDay();
                    endTime = getLastWeekEndDay();
                    break;
                case WooshopConstants.SEARCH_DATE_LATELY_30:
                    startTime = WooshopDateUtil.addDay(day, -30, WooshopConstants.DATE_FORMAT_START);
                    break;
                case WooshopConstants.SEARCH_DATE_MONTH:
                    startTime = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT_MONTH_START);
                    endTime = getMonthEndDay();
                    break;
                case WooshopConstants.SEARCH_DATE_PRE_MONTH:
                    startTime = getLastMonthStartDay();
                    endTime = getLastMonthEndDay();
                    break;
                case WooshopConstants.SEARCH_DATE_YEAR:
                    startTime = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT_YEAR_START);
                    endTime = WooshopDateUtil.nowDateTime(WooshopConstants.DATE_FORMAT_YEAR_END);
                    break;
                case WooshopConstants.SEARCH_DATE_PRE_YEAR:
                    startTime = getLastYearStartDay();
                    endTime = getLastYearEndDay();
                    break;
                default:
                    List<String> list = WooUtil.stringToArrayStr(data);
                    if(list.size() == 1){
                        throw new ServiceException("选择时间参数格式错误，请在 " +
                                WooshopConstants.SEARCH_DATE_DAY + "|" +
                                WooshopConstants.SEARCH_DATE_YESTERDAY + "|" +
                                WooshopConstants.SEARCH_DATE_LATELY_7 + "|" +
                                WooshopConstants.SEARCH_DATE_LATELY_30 + "|" +
                                WooshopConstants.SEARCH_DATE_MONTH + "|" +
                                WooshopConstants.SEARCH_DATE_YEAR + "|自定义时间范围（格式：yyyy-MM-dd HH:mm:ss，两个时间范围用逗号分割）");
                    }
                    startTime = list.get(0);
                    endTime = list.get(1);

//                    if (startTime.equals(endTime)) {
                    startTime = WooshopDateUtil.appointedDayStrToFormatStr(startTime, WooshopConstants.DATE_FORMAT_DATE, WooshopConstants.DATE_FORMAT_START);
                    endTime = WooshopDateUtil.appointedDayStrToFormatStr(endTime, WooshopConstants.DATE_FORMAT_DATE, WooshopConstants.DATE_FORMAT_END);
//                    }
                    break;
            }
        }
        return new DateLimitUtilVo(startTime, endTime);
    }

    /** 获得本周第一天:yyyy-MM-dd HH:mm:ss */
    public static String getWeekStartDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_MONTH, 0);
        c.set(Calendar.DAY_OF_WEEK, 2);
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return startSdf.format(c.getTime());
    }
    /** 获得本周最后一天:yyyy-MM-dd HH:mm:ss */
    public static String getWeekEndDay() {
        return addDay(getWeekStartDay(), 7, WooshopConstants.DATE_FORMAT);
    }

    /** 获得上周第一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastWeekStartDay() {
        return addDay(getWeekStartDay(), -7, WooshopConstants.DATE_FORMAT);
    }

    /** 获得上周最后一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastWeekEndDay() {
        return addDay(getLastWeekStartDay(), 7, WooshopConstants.DATE_FORMAT);
    }

    /** 获得本月最后一天:yyyy-MM-dd HH:mm:ss */
    public static String getMonthEndDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return endSdf.format(c.getTime());
    }
    /** 获得上月第一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastMonthStartDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        return startSdf.format(c.getTime());
    }
    /** 获得上月最后一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastMonthEndDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return endSdf.format(c.getTime());
    }

    /** 获得上年第一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastYearStartDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-01-01 00:00:00");
        return startSdf.format(c.getTime());
    }

    /** 获得上年最后一天:yyyy-MM-dd HH:mm:ss */
    public static String getLastYearEndDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-12-31 23:59:59");
        return endSdf.format(c.getTime());
    }

    /**
     * 获取指定日期指定格式字符串
     *
     * @param dateStr
     * @param DATE_FORMAT
     * @return
     * @throws ParseException
     */
    public static String appointedDayStrToFormatStr(String dateStr, String STR_DATE_FORMAT, String DATE_FORMAT) {
        Date date = WooshopDateUtil.strToDate(dateStr, STR_DATE_FORMAT);
        return WooshopDateUtil.dateToStr(date, DATE_FORMAT);
    }

}
