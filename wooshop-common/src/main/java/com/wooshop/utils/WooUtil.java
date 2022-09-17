package com.wooshop.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class WooUtil {


    /**
     * 根据长度生成随机数字
     *
     * @param start 起始数字
     * @param end   结束数字
     * @return 生成的随机码
     */
    public static Integer randomCount(Integer start, Integer end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }


    /**
     * 订单号生成
     *
     * @return 生成的随机码
     */
    public static Long getOrderNo() {
        return IdUtil.getSnowflake(0, 0).nextId();
    }


    /**
     * 获取当前时间小时
     *
     * @return 当前时间小时 默认24小时
     */
    public static int getNowHour() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 判断负数重新赋值0
     * @param bigDecimal
     * @return
     */
    public static BigDecimal getDecideZeroBig(BigDecimal bigDecimal){
        if (bigDecimal.compareTo(BigDecimal.ZERO)<=0){
            //-1小于/1大于/0相同
            return new BigDecimal(0.00);
        }
        return bigDecimal;
    }
    /**
     * 判断负数重新赋值0
     * @param Integer
     * @return
     */
    public static Integer getDecideZeroInt(Integer integer){
        if (integer<=0){
            //-1小于/1大于/0相同
            return 0;
        }
        return integer;
    }


    /**  作废
     * 百分比计算
     *
     * @param detailTotalNumber 总
     * @param totalNumber       剩余
     * @return 百分比
     */
    public static int percentInstanceIntVal(Integer detailTotalNumber, Integer totalNumber) {
        BigDecimal sales = new BigDecimal(detailTotalNumber);
        BigDecimal total = new BigDecimal(totalNumber);
        int percentage = sales.divide(total, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
        return Math.min(percentage, 100);
    }


    /**
     * 销量计算百分比计算
     *
     * @param totalPeople 总
     * @param num       剩余
     * @return 百分比
     */
    public static  String getPercentInt(Integer num, Integer totalPeople) {
        String percent;
        Double p3 = 0.0;
        if (totalPeople == 0) {
            p3 = 0.0;
        } else {
            p3 = num * 1.0 / totalPeople;
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(p3);
        return percent;
    }

    /**
     * 销量计算百分比计算
     *
     * @param totalPeople 总
     * @param num       剩余
     * @return 百分比
     */
    public static  String getPercentBig(BigDecimal num, BigDecimal totalPeople) {
        String percent;
        Double p3 = 0.0;
        if (totalPeople.compareTo(BigDecimal.ZERO)==0) {
            p3 = 0.0;
        } else {
            p3 = num.doubleValue() * 1.0 / totalPeople.doubleValue();
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(p3).replace("%","");
        return percent;
    }
    /**
     * 字符串分割，转化为数组
     * @param str 字符串
     * @author Mr.Zhang
     * @since 2020-04-22
     * @return List<String>
     */
    public static List<String> stringToArrayStr(String str){
        return stringToArrayStrRegex(str, ",");
    }
    /**
     * 字符串分割，转化为数组
     * @param str 字符串
     * @param regex 分隔符有
     * @author Mr.Zhang
     * @since 2020-04-22
     * @return List<String>
     */
    public static List<String> stringToArrayStrRegex(String str, String regex ){
        List<String> list = new ArrayList<>();
        if (str.contains(regex)){

            String[] split = str.split(regex);

            for (String value : split) {
                if(!StringUtils.isBlank(value)){
                    list.add(value);
                }
            }
        }else {
            list.add(str);
        }
        return list;
    }

    /**
     * 生成邀请码
     *
     * @return
     */
    public static String createShareCode() {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 10) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
