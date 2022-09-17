


package com.wooshop.modules.user.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.user.WooDeptService;
import com.wooshop.modules.user.WooUserService;
import com.wooshop.modules.user.WooUsersJobsService;
import com.wooshop.modules.user.WooUsersRolesService;
import com.wooshop.modules.user.domain.WooUser;
import com.wooshop.modules.user.mapper.WooUserMapper;
import com.wooshop.modules.user_grade.service.SysUserGradeService;
import com.wooshop.modules.user_level.service.WooshopUserLevelService;
import com.wooshop.utils.*;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.WooshopConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * @author woo
 * @date 2021-10-26
 * 注意：
 * 本软件为www.wooshop.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooUserServiceimpl extends BaseServiceImpl<WooUserMapper, WooUser> implements WooUserService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
    private final WooDeptService wooDeptService;
    private final WooUsersRolesService wooUsersRolesService;
    private final WooUsersJobsService wooUsersJobsService;
    private final SysUserGradeService gradeService;//用户等级配置信息
    private final TransactionTemplate transactionTemplate;
    private final WooshopUserLevelService userLevelService;//用户等级 记录 信息



    @Override
//    @CacheEvict(cacheNames=CacheKey.USER_ID,key = "#p0.id")
    public boolean updataUser(WooUser wooUser) {

        LambdaUpdateChainWrapper<WooUser> updateChainWrapper = new LambdaUpdateChainWrapper<>(baseMapper);
        updateChainWrapper.eq(WooUser::getId, wooUser.getId());

        if (wooUser.getDeptId() != null) {
            updateChainWrapper.set(WooUser::getDeptId, wooUser.getDeptId());
        }
        if (wooUser.getNickName() != null) {
            updateChainWrapper.set(WooUser::getNickName, wooUser.getNickName());
        }
        if (wooUser.getUsername() != null) {
            updateChainWrapper.set(WooUser::getUsername, wooUser.getUsername());
        }
        if (wooUser.getSex() != null) {
            updateChainWrapper.set(WooUser::getSex, wooUser.getSex());
        }
        if (wooUser.getPhone() != null) {
            updateChainWrapper.set(WooUser::getPhone, wooUser.getPhone());
        }
        if (wooUser.getEmail() != null) {
            updateChainWrapper.set(WooUser::getEmail, wooUser.getEmail());
        }
        if (wooUser.getAvatarName() != null) {
            updateChainWrapper.set(WooUser::getAvatarName, wooUser.getAvatarName());
        }
        if (wooUser.getAvatarPath() != null) {
            updateChainWrapper.set(WooUser::getAvatarPath, wooUser.getAvatarPath());
        }
        if (wooUser.getPassword() != null) {
            updateChainWrapper.set(WooUser::getPassword, wooUser.getPassword());
        }
        if (wooUser.getIsAdmin() != null) {
            updateChainWrapper.set(WooUser::getIsAdmin, wooUser.getIsAdmin());
        }
        if (wooUser.getEnabled() != null) {
            updateChainWrapper.set(WooUser::getEnabled, wooUser.getEnabled());
        }
        if (wooUser.getPwdResetTime() != null) {
            updateChainWrapper.set(WooUser::getPwdResetTime, wooUser.getPwdResetTime());
        }
        if (wooUser.getRealName() != null) {
            updateChainWrapper.set(WooUser::getRealName, wooUser.getRealName());
        }
        if (wooUser.getBirthday() != null) {
            updateChainWrapper.set(WooUser::getBirthday, wooUser.getBirthday());
        }
        if (wooUser.getCardId() != null) {
            updateChainWrapper.set(WooUser::getCardId, wooUser.getCardId());
        }
        if (wooUser.getMark() != null) {
            updateChainWrapper.set(WooUser::getMark, wooUser.getMark());
        }
        if (wooUser.getCreate_ip() != null) {
            updateChainWrapper.set(WooUser::getCreate_ip, wooUser.getCreate_ip());
        }
        if (wooUser.getLastIp() != null) {
            updateChainWrapper.set(WooUser::getLastIp, wooUser.getLastIp());
        }
        if (wooUser.getLoginupdateTime() != null) {
            updateChainWrapper.set(WooUser::getLoginupdateTime, wooUser.getLoginupdateTime());
        }
        if (wooUser.getNowMoney() != null) {
            updateChainWrapper.set(WooUser::getNowMoney, wooUser.getNowMoney());
        }
        if (wooUser.getBrokeragePrice() != null) {
            updateChainWrapper.set(WooUser::getBrokeragePrice, wooUser.getBrokeragePrice());
        }
        if (wooUser.getIntegral() != null) {
            updateChainWrapper.set(WooUser::getIntegral, wooUser.getIntegral());
        }
        if (wooUser.getSignNum() != null) {
            updateChainWrapper.set(WooUser::getSignNum, wooUser.getSignNum());
        }
        if (wooUser.getLevel() != null) {
            updateChainWrapper.set(WooUser::getLevel, wooUser.getLevel());
        }
        if (wooUser.getSpreadUid() != null) {
            updateChainWrapper.set(WooUser::getSpreadUid, wooUser.getSpreadUid());
        }
        if (wooUser.getSpreadTime() != null) {
            updateChainWrapper.set(WooUser::getSpreadTime, wooUser.getSpreadTime());
        }
        if (wooUser.getUserType() != null) {
            updateChainWrapper.set(WooUser::getUserType, wooUser.getUserType());
        }
        if (wooUser.getIsPromoter() != null) {
            updateChainWrapper.set(WooUser::getIsPromoter, wooUser.getIsPromoter());
        }
        if (wooUser.getPayCount() != null) {
            updateChainWrapper.set(WooUser::getPayCount, wooUser.getPayCount());
        }
        if (wooUser.getSpreadCount() != null) {
            updateChainWrapper.set(WooUser::getSpreadCount, wooUser.getSpreadCount());
        }
        if (wooUser.getAdminid() != null) {
            updateChainWrapper.set(WooUser::getAdminid, wooUser.getAdminid());
        }
        if (wooUser.getLoginType() != null) {
            updateChainWrapper.set(WooUser::getLoginType, wooUser.getLoginType());
        }
        if (wooUser.getShopId() != null) {
            updateChainWrapper.set(WooUser::getShopId, wooUser.getShopId());
        }
        if (wooUser.getHaveShop() != null) {
            updateChainWrapper.set(WooUser::getHaveShop, wooUser.getHaveShop());
        }
        if (wooUser.getLoginCount() != null) {
            updateChainWrapper.set(WooUser::getLoginCount, wooUser.getLoginCount());
        }
        if (wooUser.getIsCheked() != null) {
            updateChainWrapper.set(WooUser::getIsCheked, wooUser.getIsCheked());
        }
        if (wooUser.getInfoFull() != null) {
            updateChainWrapper.set(WooUser::getInfoFull, wooUser.getInfoFull());
        }
        if (wooUser.getOpenid() != null) {
            updateChainWrapper.set(WooUser::getOpenid, wooUser.getOpenid());
        }
        if (wooUser.getExperience() != null) {
            updateChainWrapper.set(WooUser::getExperience, wooUser.getExperience());
        }

        return updateChainWrapper.update();
    }



    /**
     * 获取当天新增用户数量
     *
     * @return
     */
    @Override
    public Integer getTodayNewUser() {
        //获取当前时间
        String date = WooshopDateUtil.nowDate(WooshopConstants.DATE_FORMAT_START);
        LambdaQueryChainWrapper<WooUser> lqcw = new LambdaQueryChainWrapper<>(baseMapper);
        lqcw.gt(WooUser::getCreateTime, date);
        return lqcw.count();
    }

    /**
     * 获取总用户数量
     *
     * @return
     */
    @Override
    public Integer getTotalUserNUm() {
        QueryWrapper<WooUser> qw = new QueryWrapper<>();
        qw.eq("is_del", MenuType.IS_DEL_STATUS_0.getValue());
        return baseMapper.selectCount(qw);
    }

    @Override
    public WooUser byUserId(Long uid) {
        return baseMapper.selectById(uid);
    }
}
