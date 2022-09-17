


package com.wooshop.modules.sign_record.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.WooshopDateUtil;
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
import com.wooshop.modules.sign_record.domain.WooshopSignRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.sign_record.service.dto.WooshopSignRecordQueryCriteria;
import com.wooshop.modules.sign_record.service.mapper.WooshopSignRecordMapper;
import com.wooshop.modules.sign_record.service.WooshopSignRecordService;
import com.wooshop.modules.sign_record.service.dto.WooshopSignRecordDto;


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
public class WooshopSignRecordServiceImpl extends BaseServiceImpl<WooshopSignRecordMapper, WooshopSignRecord> implements WooshopSignRecordService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopSignRecordDto> queryAll(WooshopSignRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopSignRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopSignRecordDto.class);
    }


    @Override
    public List<WooshopSignRecord> queryAll(WooshopSignRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopSignRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopSignRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopSignRecordDto wooshopSignRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopSignRecord.getUid());
            map.put("签到说明", wooshopSignRecord.getSignTitle());
            map.put("获得", wooshopSignRecord.getGainNumber());
            map.put("剩余", wooshopSignRecord.getAfterBalance());
            map.put("获得类型，1积分，2经验", wooshopSignRecord.getSignType());
            map.put("添加时间/签到时间", wooshopSignRecord.getCreateTime());
            map.put("更新时间", wooshopSignRecord.getUpdateTime());
            map.put("逻辑删除 1删除 默认0", wooshopSignRecord.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
