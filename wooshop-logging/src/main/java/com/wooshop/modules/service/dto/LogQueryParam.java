package com.wooshop.modules.service.dto;

import com.wooshop.annotation.Query;
import lombok.Data;
//import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
public class LogQueryParam{

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    /** 精确 */
    @Query
    private String logType;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
