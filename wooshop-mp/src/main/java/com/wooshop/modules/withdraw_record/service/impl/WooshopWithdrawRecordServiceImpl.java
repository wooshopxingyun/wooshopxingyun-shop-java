


package com.wooshop.modules.withdraw_record.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.brokerage_record.domain.WooshopUserBrokerageRecord;
import com.wooshop.modules.brokerage_record.service.WooshopUserBrokerageRecordService;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.WooshopConstants;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.wooshop.dozer.service.IGenerator;

import java.util.List;
import java.util.Map;
import com.wooshop.modules.withdraw_record.domain.WooshopWithdrawRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.withdraw_record.service.dto.WooshopWithdrawRecordQueryCriteria;
import com.wooshop.modules.withdraw_record.service.mapper.WooshopWithdrawRecordMapper;
import com.wooshop.modules.withdraw_record.service.WooshopWithdrawRecordService;
import com.wooshop.modules.withdraw_record.service.dto.WooshopWithdrawRecordDto;


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
public class WooshopWithdrawRecordServiceImpl extends BaseServiceImpl<WooshopWithdrawRecordMapper, WooshopWithdrawRecord> implements WooshopWithdrawRecordService {

    private final IGenerator generator;
    private final WooshopUserBrokerageRecordService userBrokerageRecordService;

    @Override
    public PageResult<WooshopWithdrawRecordDto> queryAll(WooshopWithdrawRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopWithdrawRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopWithdrawRecordDto.class);
    }


    @Override
    public List<WooshopWithdrawRecord> queryAll(WooshopWithdrawRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopWithdrawRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopWithdrawRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopWithdrawRecordDto wooshopWithdrawRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("会员id", wooshopWithdrawRecord.getUid());
            map.put("提款名称", wooshopWithdrawRecord.getWithdrawName());
            map.put("提现方式:1支付宝,2微信", wooshopWithdrawRecord.getWithdrawType());
            map.put("支付宝账号", wooshopWithdrawRecord.getAlipayCode());
            map.put("提款微信号", wooshopWithdrawRecord.getWechatCode());
            map.put("提款金额", wooshopWithdrawRecord.getWithdrawMoney());
            map.put("备注", wooshopWithdrawRecord.getMark());
            map.put("剩余余额", wooshopWithdrawRecord.getAfterBalance());
            map.put("审核情况", wooshopWithdrawRecord.getAuditMsg());
            map.put("审核状态:0审核中、1已提现、2未通过、3会员撤销", wooshopWithdrawRecord.getIsStart());
            map.put("申请时间", wooshopWithdrawRecord.getCreateTime());
            map.put("更新时间", wooshopWithdrawRecord.getUpdateTime());
            map.put("逻辑删除 1删除", wooshopWithdrawRecord.getIsDel());
            map.put("提现失败时间", wooshopWithdrawRecord.getFailTime());
            map.put("微信/支付宝收款二维码", wooshopWithdrawRecord.getQrCode());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

}
