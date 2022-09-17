/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wooshop.modules.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.domain.GenConfig;
import com.wooshop.modules.service.GenConfigService;
import com.wooshop.modules.service.mapper.GenConfigMapper;
import com.wooshop.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends CommonServiceImpl<GenConfigMapper, GenConfig> implements GenConfigService {

    private final GenConfigMapper genConfigMapper;

    @Override
    public GenConfig find(String tableName) {
        GenConfig genConfig = lambdaQuery()
                .eq(GenConfig::getTableName, tableName)
                .one();
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if(StringUtils.isBlank(genConfig.getApiPath())){
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        if (genConfig.getId() == null) {
            genConfigMapper.insert(genConfig);
        } else {
            genConfigMapper.updateById(genConfig);
        }
        return genConfig;
    }
}
