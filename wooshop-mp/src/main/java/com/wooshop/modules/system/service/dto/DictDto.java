package com.wooshop.modules.system.service.dto;

import com.wooshop.base.CommonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author jinjin
* @date 2020-09-24
*/
@Data
@NoArgsConstructor
public class DictDto extends CommonDto implements Serializable {

    private Long id;

    //     private List<DictDetailDto> dictDetails;

    private String name;

    private String description;
}
