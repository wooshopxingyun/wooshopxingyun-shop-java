


package com.wooshop.modules.money_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.wooshop.dozer.service.IGenerator;

import java.util.List;
import java.util.Map;
import com.wooshop.modules.money_record.domain.WooshopMoneyRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordQueryCriteria;
import com.wooshop.modules.money_record.service.mapper.WooshopMoneyRecordMapper;
import com.wooshop.modules.money_record.service.WooshopMoneyRecordService;
import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordDto;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopMoneyRecordServiceImpl extends BaseServiceImpl<WooshopMoneyRecordMapper, WooshopMoneyRecord> implements WooshopMoneyRecordService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopMoneyRecordDto> queryAll(WooshopMoneyRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopMoneyRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopMoneyRecordDto.class);
    }


    @Override
    public List<WooshopMoneyRecord> queryAll(WooshopMoneyRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopMoneyRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopMoneyRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopMoneyRecordDto wooshopMoneyRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", wooshopMoneyRecord.getUid());
            map.put("充值订单id", wooshopMoneyRecord.getOrderId());
            map.put("充值金额", wooshopMoneyRecord.getMoney());
            map.put("赠送金额", wooshopMoneyRecord.getGiveMoney());
            map.put("充值类型", wooshopMoneyRecord.getMoneyType());
            map.put("是否充值金额", wooshopMoneyRecord.getIsPaid());
            map.put("充值金额支付时间", wooshopMoneyRecord.getPayTime());
            map.put("充值金额时间", wooshopMoneyRecord.getCreateTime());
            map.put("退款金额", wooshopMoneyRecord.getRefundMoney());
            map.put("更新时间", wooshopMoneyRecord.getUpdateTime());
            map.put("逻辑删除 1删除", wooshopMoneyRecord.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

}
