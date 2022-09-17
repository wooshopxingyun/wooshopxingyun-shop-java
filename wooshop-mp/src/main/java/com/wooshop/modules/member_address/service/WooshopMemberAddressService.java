
package com.wooshop.modules.member_address.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.member_address.domain.WooshopMemberAddress;

import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressQueryCriteria;

/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopMemberAddressService  extends BaseService<WooshopMemberAddress>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopMemberAddressDto>  queryAll(WooshopMemberAddressQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopMemberAddressDto>
    */
    List<WooshopMemberAddress> queryAll(WooshopMemberAddressQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopMemberAddressDto> all, HttpServletResponse response) throws IOException;

    /**
     * 新增获取更新地址
     * @param resources
     * @return
     */
    public PageResult<WooshopMemberAddressDto> addAndUpdate(WooshopMemberAddress resources);

    /**
     * 设置该用户所有的地址为不默认
     * @param uid
     */
    void isNotDefault(Long uid);
}
