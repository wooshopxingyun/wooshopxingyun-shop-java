
package com.wooshop.modules.template.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageDto;
import com.wooshop.modules.template.vo.WooshopFreightTemplatePinkageVo;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplatePinkage;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageQueryCriteria;

/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopFreightTemplatePinkageService  extends BaseService<WooshopFreightTemplatePinkage>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopFreightTemplatePinkageDto>  queryAll(WooshopFreightTemplatePinkageQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopFreightTemplatePinkageDto>
    */
    List<WooshopFreightTemplatePinkage> queryAll(WooshopFreightTemplatePinkageQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopFreightTemplatePinkageDto> all, HttpServletResponse response) throws IOException;

    /**
     * 物理删除
     * 通过模板id 进行删除
     */
    void pinkageRemoveByTempId(Long tempId );

    /**
     * 查找免邮数据
     * @return tempId 模板id
     */
    List<WooshopFreightTemplatePinkageVo> pinkageSeletctByTempId(Long tempId );

    /**
     * 包含新增 和更新
     * @param resources
     * @return
     */
    WooshopFreightTemplatePinkageVo addAndUpdate(WooshopFreightTemplatePinkage resources);
}
