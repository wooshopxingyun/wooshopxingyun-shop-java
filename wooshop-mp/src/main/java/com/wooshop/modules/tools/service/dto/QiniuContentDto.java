package com.wooshop.modules.tools.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QiniuContentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String bucket;

    private String name;

    private String size;

    private String type;

    private String url;

    private String suffix;

    private Date updateTime;
}
