
package ${package}.service;


import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import ${package}.service.dto.${className}Dto;
import org.springframework.data.domain.Pageable;
import com.wooshop.common.service.BaseService;

import com.wooshop.domain.PageResult;
import ${package}.domain.${className};

import ${package}.service.dto.${className}QueryCriteria;

/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface ${className}Service  extends BaseService<${className}>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<${className}Dto>  queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}Dto>
    */
    List<${className}> queryAll(${className}QueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<${className}Dto> all, HttpServletResponse response) throws IOException;

    /**
    * 包含新增和更新功能
    * @param resources
    * @return
    */
    PageResult<${className}Dto> addAndUpdate(${className} resources);


    /**
    * 根据id 进行删除
    * @param id
    */
    void cacheRemoveById(Long id);
}
