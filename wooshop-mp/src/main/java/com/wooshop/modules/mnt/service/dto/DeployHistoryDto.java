package com.wooshop.modules.mnt.service.dto;

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
public class DeployHistoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String appName;

    private Date deployDate;

    private String deployUser;

    private String ip;

    private Long deployId;
}
