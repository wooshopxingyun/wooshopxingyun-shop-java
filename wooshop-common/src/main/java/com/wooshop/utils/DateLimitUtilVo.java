package com.wooshop.utils;

import lombok.Data;

@Data
public class DateLimitUtilVo {
    public DateLimitUtilVo() {}
    public DateLimitUtilVo(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String startTime; //开始时间

    private String endTime; //结束时间
}
