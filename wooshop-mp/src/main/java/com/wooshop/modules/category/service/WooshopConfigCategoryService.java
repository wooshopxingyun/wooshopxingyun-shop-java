
package com.wooshop.modules.category.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.category.domain.WooshopConfigCategory;

import com.wooshop.modules.category.service.dto.WooshopConfigCategoryQueryCriteria;

/**
* @author woo
* @date 2021-11-15
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopConfigCategoryService  extends BaseService<WooshopConfigCategory>{

    /**
    * 查询树形结构数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopConfigCategoryDto>  queryAll(WooshopConfigCategoryQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopConfigCategoryDto>
    */
    List<WooshopConfigCategory> queryAll(WooshopConfigCategoryQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopConfigCategoryDto> all, HttpServletResponse response) throws IOException;

    /**
     * 条件查询分类表
     * @param type 类型
     * @param enabled 状态
     * @param name 分类名称
     * @param categoryIdList
     * @return
     */
    List<WooshopConfigCategoryDto> getCategory(Integer type, Integer enabled, String name,String path, List<Integer> categoryIdList);

    /**
     * 新增分类
     * @param categoryId
     * @param name
     * @param sort
     */
    void addCategory(Integer categoryId, String name, Integer sort,Integer type);

    /**
     * 所有新增分类入口
     * @param configCategory
     * @return
     */
    boolean addNewCategory(WooshopConfigCategory configCategory);
}
