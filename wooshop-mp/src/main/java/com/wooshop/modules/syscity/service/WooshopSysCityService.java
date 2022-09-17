
package com.wooshop.modules.syscity.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

import com.wooshop.modules.syscity.service.dto.WooshopSysCityDto;
import com.wooshop.modules.syscity.service.vo.WooshopSysCityVo;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.syscity.domain.WooshopSysCity;

import com.wooshop.modules.syscity.service.dto.WooshopSysCityQueryCriteria;

/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopSysCityService  extends BaseService<WooshopSysCity>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopSysCityDto>  queryAll(WooshopSysCityQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopSysCityDto>
    */
    List<WooshopSysCity> queryAll(WooshopSysCityQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopSysCityDto> all, HttpServletResponse response) throws IOException;

    /**
     * 获取行政区域 树形结构
     * @return List 树形结构
     */
   List<WooshopSysCityVo> getCityTree();

    /**
     * 新增和更新方法
     * @param resources
     * @return
     */
    WooshopSysCityDto addAndUpdate(WooshopSysCity resources);
}
