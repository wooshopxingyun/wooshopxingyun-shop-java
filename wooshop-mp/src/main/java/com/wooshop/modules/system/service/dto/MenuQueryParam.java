package com.wooshop.modules.system.service.dto;

import com.wooshop.annotation.Query;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
@Data
public class MenuQueryParam{

    @Query(blurry = "title,component,permission")
    private String blurry;

    /** 精确 */
    @Query
    private Long menuId;

    /** 精确 */
    @Query
    private Long pid;

    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;
}
