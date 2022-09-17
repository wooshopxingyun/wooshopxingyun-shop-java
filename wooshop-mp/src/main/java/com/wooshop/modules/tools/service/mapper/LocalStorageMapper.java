package com.wooshop.modules.tools.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.common.CoreMapper;
import com.wooshop.modules.tools.domain.LocalStorage;
import com.wooshop.modules.tools.domain.QiniuContent;
import org.springframework.stereotype.Repository;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface LocalStorageMapper extends CoreMapper<LocalStorage> {

    LocalStorage findByKey(String key);
}
