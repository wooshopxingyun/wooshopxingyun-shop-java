
package com.wooshop.modules.template.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignDto;
import com.wooshop.modules.template.vo.WooshopFreightTemplateAssignVo;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplateAssign;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignQueryCriteria;

/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopFreightTemplateAssignService  extends BaseService<WooshopFreightTemplateAssign>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopFreightTemplateAssignDto>  queryAll(WooshopFreightTemplateAssignQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopFreightTemplateAssignDto>
    */
    List<WooshopFreightTemplateAssign> queryAll(WooshopFreightTemplateAssignQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopFreightTemplateAssignDto> all, HttpServletResponse response) throws IOException;

    /**
     * 物理删除
     * 通过 模板id进行删除
     */
    void removeByTempId(Long tempId);


    /**
     * 通过模板 id 查询所有数据
     * @param tempId  模板id
     * @return
     */
    List<WooshopFreightTemplateAssignVo> selectByTempId(Long tempId);

    /**
     * 包含新增 和 更新
     * @param resources
     * @return
     */
    WooshopFreightTemplateAssignVo addAndUpdate(WooshopFreightTemplateAssign resources);
}
