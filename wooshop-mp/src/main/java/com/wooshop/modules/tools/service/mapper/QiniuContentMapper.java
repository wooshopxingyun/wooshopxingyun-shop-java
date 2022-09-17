package com.wooshop.modules.tools.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.tools.domain.QiniuContent;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QiniuContentMapper extends CommonMapper<QiniuContent> {

    QiniuContent findByKey(String key);
}
