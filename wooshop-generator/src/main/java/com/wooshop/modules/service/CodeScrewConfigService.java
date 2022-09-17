package com.wooshop.modules.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.domain.CodeScrewConfig;
import com.wooshop.modules.service.dto.CodeScrewConfigDto;
import com.wooshop.modules.service.dto.CodeScrewConfigQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author fanglei
* @date 2021-08-11
*/
public interface CodeScrewConfigService extends CommonService<CodeScrewConfig>  {

    static final String CACHE_KEY = "codeScrewConfig";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<CodeScrewConfigDto>
    */
    PageInfo<CodeScrewConfigDto> queryAll(CodeScrewConfigQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<CodeScrewConfigDto>
    */
    List<CodeScrewConfigDto> queryAll(CodeScrewConfigQueryParam query);

    CodeScrewConfig getById(Long id);
    CodeScrewConfigDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(CodeScrewConfigDto resources);
    int updateById(CodeScrewConfigDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param response /
    * @throws IOException /
    */
    void download(CodeScrewConfigDto configDto, String fileType, HttpServletResponse response) throws IOException;
}
