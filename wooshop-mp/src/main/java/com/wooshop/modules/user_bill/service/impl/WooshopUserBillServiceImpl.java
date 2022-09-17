


package com.wooshop.modules.user_bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.UserBillConstants;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.dozer.service.IGenerator;
import com.wooshop.utils.RedisUtils;
import com.wooshop.modules.user_bill.domain.WooshopUserBill;
import org.springframework.stereotype.Service;
import com.wooshop.modules.user_bill.service.dto.WooshopUserBillQueryCriteria;
import com.wooshop.modules.user_bill.service.mapper.WooshopUserBillMapper;
import com.wooshop.modules.user_bill.service.WooshopUserBillService;
import com.wooshop.modules.user_bill.service.dto.WooshopUserBillDto;


/**
* @author woo
* @date 2022-02-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopUserBillServiceImpl extends BaseServiceImpl<WooshopUserBillMapper, WooshopUserBill> implements WooshopUserBillService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopUserBillDto> queryAll(WooshopUserBillQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopUserBill> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopUserBillDto.class);
    }


    @Override
    public List<WooshopUserBill> queryAll(WooshopUserBillQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopUserBill.class, criteria));
    }


    @Override
    public void download(List<WooshopUserBillDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopUserBillDto wooshopUserBill : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopUserBill.getUid());
            map.put("关联id", wooshopUserBill.getLinkId());
            map.put("0 = 支出 1 = 获得", wooshopUserBill.getBillPm());
            map.put("会员账单标题", wooshopUserBill.getBillTitle());
            map.put("账单明细种类", wooshopUserBill.getCategory());
            map.put("账单明细类型", wooshopUserBill.getBillType());
            map.put("账单明细数字", wooshopUserBill.getBillNumber());
            map.put("账单剩余", wooshopUserBill.getBalance());
            map.put("账单备注", wooshopUserBill.getMark());
            map.put("0 = 带确定 1 = 有效 -1 = 无效", wooshopUserBill.getStatus());
            map.put("添加时间", wooshopUserBill.getCreateTime());
            map.put("更新时间", wooshopUserBill.getUpdateTime());
            map.put("逻辑删除: 1表示删除", wooshopUserBill.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopUserBillDto> addAndUpdate(WooshopUserBill resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopUserBill> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopUserBill> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopUserBillDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }


}
