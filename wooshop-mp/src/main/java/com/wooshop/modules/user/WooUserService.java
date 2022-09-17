
package com.wooshop.modules.user;


import com.wooshop.modules.service.BaseService;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.brokerage_record.service.vo.UserBrokeragePersonAppVo;
import com.wooshop.modules.system.service.dto.UserDto;
import com.wooshop.modules.user.domain.WooUser;
import com.wooshop.modules.user.vo.UserSing;
import com.wooshop.modules.user.vo.UserSpreadPeopleItemAppvo;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
* @author woo
* @date 2021-10-26
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooUserService extends BaseService<WooUser>{


    /**
     * 更新用户信息
     * @param wooUser
     * @return
     */
    boolean updataUser(WooUser wooUser);

    /**
     * 获取当天新增用户数量
     * @return
     */
    Integer getTodayNewUser();

    /**
     * 获取总用户数量
     * @return
     */
    Integer getTotalUserNUm();

    WooUser byUserId(Long uid);
}
