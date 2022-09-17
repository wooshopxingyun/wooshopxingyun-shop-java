
package com.wooshop.modules.wooshop_wechat_menu.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.wooshop_wechat_menu.domain.WooshopWechatMenu;

import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuQueryCriteria;

/**
* @author woo
* @date 2022-07-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopWechatMenuService  extends BaseService<WooshopWechatMenu>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopWechatMenuDto>  queryAll(WooshopWechatMenuQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopWechatMenuDto>
    */
    List<WooshopWechatMenu> queryAll(WooshopWechatMenuQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopWechatMenuDto> all, HttpServletResponse response) throws IOException;

    /**
    * 包含新增和更新功能
    * @param resources
    * @return
    */
    PageResult<WooshopWechatMenuDto> addAndUpdate(WooshopWechatMenu resources);


    /**
    * 根据id 进行删除
     * @param id
     */
    void cacheRemoveById(String id);

    /**
     * 查询是否存在
     * @param wechatMenus 名称
     * @return
     */
    Boolean isExist(String wechatMenus);
}
