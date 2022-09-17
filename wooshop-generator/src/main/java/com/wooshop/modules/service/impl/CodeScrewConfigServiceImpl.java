package com.wooshop.modules.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.domain.CodeScrewConfig;
import com.wooshop.modules.service.CodeScrewConfigService;
import com.wooshop.modules.service.dto.CodeScrewConfigDto;
import com.wooshop.modules.service.dto.CodeScrewConfigQueryParam;
import com.wooshop.modules.service.mapper.CodeScrewConfigMapper;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.PageUtil;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

// 默认不使用缓存

/**
* @author fanglei
* @date 2021-08-11
*/
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = CodeScrewConfigService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CodeScrewConfigServiceImpl extends CommonServiceImpl<CodeScrewConfigMapper, CodeScrewConfig> implements CodeScrewConfigService {
    private final DataSource dataSource;
    private final RedisUtils redisUtils;
    private final CodeScrewConfigMapper codeScrewConfigMapper;

    @Override
    public PageInfo<CodeScrewConfigDto> queryAll(CodeScrewConfigQueryParam query, Pageable pageable) {
        IPage<CodeScrewConfig> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<CodeScrewConfig> page = codeScrewConfigMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, CodeScrewConfigDto.class);
    }

    @Override
    public List<CodeScrewConfigDto> queryAll(CodeScrewConfigQueryParam query){
        return ConvertUtil.convertList(codeScrewConfigMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), CodeScrewConfigDto.class);
    }

    @Override
    public CodeScrewConfig getById(Long id) {
        return codeScrewConfigMapper.selectById(id);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public CodeScrewConfigDto findById(Long id) {
        return ConvertUtil.convert(getById(id), CodeScrewConfigDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(CodeScrewConfigDto resources) {
        CodeScrewConfig entity = ConvertUtil.convert(resources, CodeScrewConfig.class);
        return codeScrewConfigMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(CodeScrewConfigDto resources){
        CodeScrewConfig entity = ConvertUtil.convert(resources, CodeScrewConfig.class);
        int ret = codeScrewConfigMapper.updateById(entity);
        delCaches(resources.getConfigId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return codeScrewConfigMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }


    private void delCaches(Long id) {
        redisUtils.del(CACHE_KEY + "::id:" + id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }


    @Override
    public void download(CodeScrewConfigDto configDto, String fileType, HttpServletResponse response) throws IOException {
        EngineFileType engineFileType = EngineFileType.WORD;
        if (EngineFileType.HTML.name().equalsIgnoreCase(fileType)) {
            engineFileType = EngineFileType.HTML;
        } else if(EngineFileType.MD.name().equalsIgnoreCase(fileType)) {
            engineFileType = EngineFileType.MD;
        } else if(EngineFileType.EXCEL.name().equalsIgnoreCase(fileType)) {
            engineFileType = EngineFileType.EXCEL;
        }
        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 打开目录
                .openOutputDir(false)
                // 文件类型(目前支持html、doc、MD格式，个人体验后还是html格式生成后看起来比较舒服，建议使用)
                .fileType(engineFileType)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();
        // 将生成文件放入response流中
        engineConfig.setOutputStream(response.getOutputStream());
        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version(configDto.getVersion())
                .description(configDto.getDescription())
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig(configDto))
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
        IoUtil.close(response.getOutputStream());
    }
    /**
     * 配置想要生成的表+ 配置想要忽略的表
     * @return 生成表配置
     */
    private ProcessConfig getProcessConfig(CodeScrewConfigDto configDto) {
        // 忽略表名，需要忽略的表将表名放到list中即可
        List<String> ignoreTableName = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getIgnoreTableName())) {
            ignoreTableName = Arrays.asList(StringUtils.split(configDto.getIgnoreTableName(), ","));
        }

        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getIgnoreTablePrefix())) {
            ignorePrefix = Arrays.asList(StringUtils.split(configDto.getIgnoreTablePrefix(), ","));
        }
        // 忽略表后缀
        List<String> ignoreSuffix = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getIgnoreTableSuffix())) {
            ignoreSuffix = Arrays.asList(StringUtils.split(configDto.getIgnoreTableSuffix(), ","));
        }

        // 直接指定表名生成
        List<String> designatedTableName = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getDesignatedTableName())) {
            designatedTableName = Arrays.asList(StringUtils.split(configDto.getDesignatedTableName(), ","));
        }

        // 直接指定表前缀生成
        List<String> designatedTablePrefix = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getDesignatedTablePrefix())) {
            designatedTablePrefix = Arrays.asList(StringUtils.split(configDto.getDesignatedTablePrefix(), ","));
        }
        // 忽略表后缀
        List<String> designatedTableSuffix = new ArrayList<>();
        if (StringUtils.isNoneEmpty(configDto.getDesignatedTableSuffix())) {
            designatedTableSuffix = Arrays.asList(StringUtils.split(configDto.getDesignatedTableSuffix(), ","));
        }
        return ProcessConfig.builder()
                //根据名称指定表生成
                .designatedTableName(designatedTableName)
                //根据表前缀生成
                .designatedTablePrefix(designatedTablePrefix)
                //根据表后缀生成
                .designatedTableSuffix(designatedTableSuffix)
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }
}
