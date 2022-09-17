
package com.wooshop.modules.template.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateDto;
import com.wooshop.modules.template.vo.WooshopFreightTemplateVo;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplate;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplateQueryCriteria;

/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopFreightTemplateService  extends BaseService<WooshopFreightTemplate>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopFreightTemplateVo>  queryAll(WooshopFreightTemplateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopFreightTemplateDto>
    */
    List<WooshopFreightTemplate> queryAll(WooshopFreightTemplateQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopFreightTemplateDto> all, HttpServletResponse response) throws IOException;

    /**
     * 保存或更新数据
     * @param resources
     * @return
     */
    String saveAndUpdate( WooshopFreightTemplateVo resources );

    /**
     * 物理删
     * @param id 表id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 通过id 查询
     * @param id 主键id
     * @return
     */
    WooshopFreightTemplateVo findById(Long id);

}
