
package com.wooshop.modules.sys_config.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;
import com.wooshop.modules.sys_config.vo.SysConfigHomeVo;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.sys_config.domain.WooSysConfig;

import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;

/**
* @author woo
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooSysConfigService  extends BaseService<WooSysConfig>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooSysConfigDto>  queryAll(WooSysConfigQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooSysConfigDto>
    */
    List<WooSysConfig> queryAll(WooSysConfigQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooSysConfigDto> all, HttpServletResponse response) throws IOException;

    /**
     * 查询配置
     * @param menName
     * @return
     */
    WooSysConfigDto queryByMenuName(String menName);

    /**
     * 包含新增和更新
     * @param resources
     * @return
     */
    PageResult<WooSysConfigDto> addSaveOrUpdate(WooSysConfig resources);

    /**
     * 根据id进行删除
     * @param id
     */
    void cacheRemoveById(Integer id);

    /**
     * 查找移动端页面装修数据
     * @return
     */
    List<SysConfigHomeVo> getSysAppHome();
}
