package com.wooshop.modules.tools.service.dto;

import lombok.Data;
import com.wooshop.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
public class LocalStorageQueryParam{

    @Query(blurry = "name,suffix,type,createBy,size")
    private String blurry;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> createTime;

    //    @ApiModelProperty(value = "存储类型") 1本地 、2七牛云
    @Query(type = Query.Type.EQUAL)
    private Integer storageType;
}
