package com.wooshop.modules.tools.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.tools.domain.QiniuConfig;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QiniuConfigMapper extends CommonMapper<QiniuConfig> {

    int updateType(String type);
}
