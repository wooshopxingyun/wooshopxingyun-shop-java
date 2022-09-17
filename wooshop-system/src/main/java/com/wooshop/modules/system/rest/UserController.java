/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wooshop.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wooshop.domain.PageResult;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.integral_record.domain.WooshopIntegralRecord;
import com.wooshop.modules.integral_record.service.WooshopIntegralRecordService;
import com.wooshop.modules.system.service.dto.IntegralMoneyParam;
import com.wooshop.modules.user.WooUserService;
import com.wooshop.modules.user.domain.WooUser;
import com.wooshop.modules.user_bill.domain.WooshopUserBill;
import com.wooshop.modules.user_bill.service.WooshopUserBillService;
import com.wooshop.modules.user_grade.domain.SysUserGrade;
import com.wooshop.modules.user_grade.service.SysUserGradeService;
import com.wooshop.modules.user_level.domain.WooshopUserLevel;
import com.wooshop.modules.user_level.service.WooshopUserLevelService;
import com.wooshop.utils.enums.BillEnum;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.WooshopConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.wooshop.annotation.Log;
import com.wooshop.config.RsaProperties;
import com.wooshop.exception.BadRequestException;
import com.wooshop.modules.system.domain.User;
import com.wooshop.modules.system.domain.vo.UserPassVo;
import com.wooshop.modules.system.service.*;
import com.wooshop.dto.RoleSmallDto;
import com.wooshop.modules.system.service.dto.UserDto;
import com.wooshop.modules.system.service.dto.UserQueryParam;
import com.wooshop.utils.*;
import com.wooshop.utils.enums.CodeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final WooUserService woouserService;
    private final DataService dataService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;
    private final WooshopIntegralRecordService integralRecordService;
    private final IGenerator generator;
    private final WooshopUserBillService userBillService;
    private final SysUserGradeService sysUserGradeService;
    private final WooshopUserLevelService levelService;


    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','user:list')")
    public void download(HttpServletResponse response, UserQueryParam criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<PageResult<UserDto>> query(UserQueryParam criteria, Pageable pageable) {
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());
            criteria.getDeptIds().addAll(deptService.getDeptChildren(criteria.getDeptId(),
                    deptService.findByPid(criteria.getDeptId())));
        }
        // 数据权限
        List<Long> dataScopes = dataService.getDeptIds(userService.findByName(SecurityUtils.getCurrentUsername()));
        // criteria.getDeptIds() 不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)) {
            // 取交集
            criteria.getDeptIds().retainAll(dataScopes);
            if (!CollectionUtil.isEmpty(criteria.getDeptIds())) {
                return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
            }
        } else {
            // 否则取并集
            criteria.getDeptIds().addAll(dataScopes);
            return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(new PageResult<>(), HttpStatus.OK);
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('admin','user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody UserDto resources) {
        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        userService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('admin','user:edit')")
    public ResponseEntity<Object> update(@Validated(User.Update.class) @RequestBody UserDto resources) throws Exception {
        checkLevel(resources);
        userService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(User.Update.class) @RequestBody User resources) {
        if (!resources.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BadRequestException("不能修改他人资料");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','user:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel = Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + userService.findById(id).getUsername());
            }
        }
        userService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile avatar) {
        return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    }

    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code, @RequestBody User user) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, user.getPassword());
        UserDto userDto = userService.findByName(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + user.getEmail(), code);
        userService.updateEmail(userDto.getUsername(), user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources /
     */
    private void checkLevel(UserDto resources) {
        Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles().stream().map(RoleSmallDto::getId).collect(Collectors.toSet()));
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }


    @Log("获取用户详情信息")
    @ApiOperation("获取用户详情信息")
    @GetMapping(value = "/getMemberInfo/{userId}")
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<Object> getMemberInfo(@NotNull(message = "用户编码不能为空") @PathVariable Long userId) throws Exception {
        UserDto userDto = userService.findById(userId);
        if (ObjectUtil.isNull(userDto)) {
            throw new BadRequestException("用户信息不存在");
        }
        Map<String, Object> map = new HashMap<>();
        //头像
        map.put("avatarName", userDto.getAvatarName());
        //昵称
        map.put("username", userDto.getUsername());
        //余额
        map.put("nowMoney", userDto.getNowMoney());
        //总订单数
//        Integer sumOrderNUm = orderService.findSumOrderNUm(userId);
        map.put("sumOrderNUm", 2);
        //总消费金额
//        BigDecimal sumConsumePrice = orderService.findSumConsumePrice(userId);
        map.put("sumConsumePrice", 0.00);
        //剩余积分
        map.put("integral", userDto.getIntegral());
        //本月订单
//        Integer mothOrderCount = orderService.getOrderCountByUidAndDate(userId, WooshopConstants.SEARCH_DATE_MONTH);
        map.put("mothOrderCount", 20);
        //本月消费金额
//        BigDecimal mothConsumeCount = orderService.getSumPayPriceByUidAndDate(userId, WooshopConstants.SEARCH_DATE_MONTH);
        map.put("mothConsumeCount", 88.00);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @Log("获取好友关系")
    @ApiOperation("获取好友关系")
    @GetMapping(value = "/getUserFriends/{userId}")
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<List<User>> getUserFriends(@NotNull(message = "用户编码不能为空") @PathVariable Long userId) throws Exception {
        UserDto userDto = userService.findById(userId);
        if (ObjectUtil.isNull(userDto)) {
            throw new BadRequestException("用户信息不存在");
        }
        return new ResponseEntity<>(userService.getUserFriends(userId), HttpStatus.OK);
    }


    @Log("修改积分/余额")
    @ApiOperation("修改积分/余额")
    @PostMapping(value = "/updateIntegralMoney")
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<Object> updateIntegralMoney(@RequestBody IntegralMoneyParam integralMoneyParams) throws Exception {
        System.out.println(integralMoneyParams);
        UserDto byId = userService.findById(integralMoneyParams.getUid());
        //积分
        if (integralMoneyParams.getGenreType().equals(0)) {
            WooshopIntegralRecord integralRecord = new WooshopIntegralRecord();
            integralRecord.setUid(byId.getId());
            integralRecord.setIntegralType(MenuType.INTEGRAL_LINK_TYPE_3.getValue());//系统添加
            integralRecord.setIntegralTitle(WooshopConstants.INTEGRAL_RECORD_TITLE);
            integralRecord.setIsState(MenuType.INTEGRAL_RECORD_STATUS_3.getValue());

            WooshopUserBill wooshopUserBill = new WooshopUserBill();
            wooshopUserBill.setLinkId("0");
            wooshopUserBill.setUid(byId.getId());
            wooshopUserBill.setStatus(MenuType.BILL_STATUS_1.getValue());
            wooshopUserBill.setBillTitle(WooshopConstants.INTEGRAL_RECORD_TITLE);
            if (integralMoneyParams.getEditType().equals(1)) {
                //增加
                //更新积分记录
                integralRecord.setIntegralId(Integer.toString(MenuType.INTEGRAL_RECORD_LINK_TYPE_2.getValue()));
                integralRecord.setIntegralRecordType(MenuType.INTEGRAL_RECORD_ADD_1.getValue());
                integralRecord.setIntegral(integralMoneyParams.getNumber().intValue());//新增积分
                integralRecord.setBeforeIntegral(byId.getIntegral().add(integralMoneyParams.getNumber()).intValue());//剩余积分
                integralRecord.setRemarks(StrUtil.format(WooshopConstants.INTEGRAL_RECORD_REMARKS_ADD, integralMoneyParams.getNumber()));
                //更新用户积分
                byId.setIntegral(byId.getIntegral().add(integralMoneyParams.getNumber()));
                //会员账单记录
                wooshopUserBill.setBillPm(MenuType.BILL_MP_STATUS_1.getValue());
                wooshopUserBill.setCategory(BillEnum.BILL_CATEGORY_INTEGRAL.getKey());
                wooshopUserBill.setBillType(BillEnum.BILL_TYPE_7.getKey());
                wooshopUserBill.setBillNumber(integralMoneyParams.getNumber());//明细
                wooshopUserBill.setBalance(byId.getIntegral().add(integralMoneyParams.getNumber()));//剩余
                wooshopUserBill.setMark(StrUtil.format(WooshopConstants.INTEGRAL_RECORD_REMARKS_ADD, integralMoneyParams.getNumber()));//备注
            } else {
                //减少

                if (byId.getIntegral().compareTo(integralMoneyParams.getNumber()) != 1) {
                    //    结果是: -1：小于； 0 ：等于； 1 ：大于；
                    //设置为0
                    integralRecord.setBeforeIntegral(0);//剩余积分
                    //更新用户积分
                    byId.setIntegral(new BigDecimal(0.00));
                    //会员记录
                    wooshopUserBill.setBalance(new BigDecimal(0.00));//剩余
                } else {
                    integralRecord.setBeforeIntegral(byId.getIntegral().subtract(integralMoneyParams.getNumber()).intValue());//剩余积分
                    //更新用户积分
                    byId.setIntegral(byId.getIntegral().subtract(integralMoneyParams.getNumber()));
                    //会员账单记录
                    wooshopUserBill.setBalance(byId.getIntegral().subtract(integralMoneyParams.getNumber()));//剩余
                }
                //更新积分记录
                integralRecord.setIntegralId(Integer.toString(MenuType.INTEGRAL_RECORD_LINK_TYPE_2.getValue()));
                integralRecord.setIntegralRecordType(MenuType.INTEGRAL_RECORD_SUBTRACT_2.getValue());
                integralRecord.setIntegral(integralMoneyParams.getNumber().intValue());//减少积分
                integralRecord.setRemarks(StrUtil.format(WooshopConstants.INTEGRAL_RECORD_REMARKS_SUB, integralMoneyParams.getNumber()));

                //会员账单记录
                wooshopUserBill.setBillPm(MenuType.BILL_MP_STATUS_0.getValue());
                wooshopUserBill.setCategory(BillEnum.BILL_CATEGORY_INTEGRAL.getKey());
                wooshopUserBill.setBillType(BillEnum.BILL_TYPE_8.getKey());
                wooshopUserBill.setBillNumber(integralMoneyParams.getNumber());//明细
                wooshopUserBill.setMark(StrUtil.format(WooshopConstants.INTEGRAL_RECORD_REMARKS_SUB, integralMoneyParams.getNumber()));//备注
            }
            woouserService.updataUser(generator.convert(byId, WooUser.class));
            integralRecordService.addAndUpdate(integralRecord);
            userBillService.addAndUpdate(wooshopUserBill);
        } else {
            //余额
            WooshopUserBill wooshopUserBill = new WooshopUserBill();
            wooshopUserBill.setLinkId("0");
            wooshopUserBill.setUid(byId.getId());
            wooshopUserBill.setStatus(MenuType.BILL_STATUS_1.getValue());
            wooshopUserBill.setBillTitle(WooshopConstants.YUE_RECORD_TITLE);
            if (integralMoneyParams.getEditType().equals(1)) {
                //新增
                //更新用户余额
                byId.setNowMoney(byId.getNowMoney().add(integralMoneyParams.getNumber()));
                //会员账单记录
                wooshopUserBill.setBillPm(MenuType.BILL_MP_STATUS_1.getValue());
                wooshopUserBill.setCategory(BillEnum.BILL_CATEGORY_MONEY.getKey());
                wooshopUserBill.setBillType(BillEnum.BILL_TYPE_7.getKey());
                wooshopUserBill.setBillNumber(integralMoneyParams.getNumber());//明细
                wooshopUserBill.setBalance(byId.getNowMoney().add(integralMoneyParams.getNumber()));//剩余
                wooshopUserBill.setMark(StrUtil.format(WooshopConstants.YUE_RECORD_REMARKS_ADD, integralMoneyParams.getNumber()));//备注
            } else {
                //减少
                if (byId.getNowMoney().compareTo(integralMoneyParams.getNumber()) != 1) {
                    //结果是: -1：小于； 0 ：等于； 1 ：大于；
                    //设置为0
                    //更新用户积分
                    byId.setNowMoney(new BigDecimal(0.00));
                    //会员记录
                    wooshopUserBill.setBalance(new BigDecimal(0.00));//剩余
                }else {
                    //更新用户余额
                    byId.setNowMoney(byId.getNowMoney().subtract(integralMoneyParams.getNumber()));
                    //会员记录
                    wooshopUserBill.setBalance(byId.getNowMoney().subtract(integralMoneyParams.getNumber()));//剩余
                }
                //会员账单记录
                wooshopUserBill.setBillPm(MenuType.BILL_MP_STATUS_0.getValue());
                wooshopUserBill.setCategory(BillEnum.BILL_CATEGORY_MONEY.getKey());
                wooshopUserBill.setBillType(BillEnum.BILL_TYPE_8.getKey());
                wooshopUserBill.setBillNumber(integralMoneyParams.getNumber());//明细
                wooshopUserBill.setMark(StrUtil.format(WooshopConstants.YUE_RECORD_REMARKS_SUB, integralMoneyParams.getNumber()));//备注
            }
            woouserService.updataUser(generator.convert(byId, WooUser.class));
            userBillService.addAndUpdate(wooshopUserBill);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Log("修改会员等级")
    @ApiOperation("修改会员等级")
    @GetMapping(value = "/updateUserLevel")
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<Object> updateUserLevel(@NotNull(message = "用户编码不能为空") @RequestParam Long userId,
                                                  @NotNull(message = "用户等级不能为空") @RequestParam Integer levelId) throws Exception {
        UserDto byId = userService.findById(userId);
        SysUserGrade sysUserGrade = sysUserGradeService.findById(levelId);
        byId.setLevel(sysUserGrade.getId().intValue());
        byId.setExperience(sysUserGrade.getExperience());
        woouserService.updataUser(generator.convert(byId, WooUser.class));
        WooshopUserLevel userLevel=new WooshopUserLevel();
        userLevel.setUid(byId.getId());
        userLevel.setLevelId(byId.getLevel());
        userLevel.setGrade(sysUserGrade.getGradeWeight());
        userLevel.setIsStart(MenuType.IS_STATUS_1.getValue());
        Date date = WooshopDateUtil.nowDateTimeReturnDate(WooshopConstants.DATE_FORMAT);
        String mark = WooshopConstants.USER_LEVEL_UP_LOG_MARK_SYS.replace("【{$userName}】", byId.getNickName()).
                replace("{$date}", WooshopDateUtil.dateToStr(date, WooshopConstants.DATE_FORMAT)).
                replace("{$levelName}", sysUserGrade.getGradeName());
        userLevel.setMark(mark);
        userLevel.setDiscount(sysUserGrade.getGradeRights().intValue());
        levelService.addAndUpdate(userLevel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
