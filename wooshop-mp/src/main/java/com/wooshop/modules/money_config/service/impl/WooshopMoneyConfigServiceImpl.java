


package com.wooshop.modules.money_config.service.impl;

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
import java.util.LinkedHashMap;
import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.wooshop.dozer.service.IGenerator;

import java.util.List;
import java.util.Map;
import com.wooshop.modules.money_config.domain.WooshopMoneyConfig;
import org.springframework.stereotype.Service;
import com.wooshop.modules.money_config.service.dto.WooshopMoneyConfigQueryCriteria;
import com.wooshop.modules.money_config.service.mapper.WooshopMoneyConfigMapper;
import com.wooshop.modules.money_config.service.WooshopMoneyConfigService;
import com.wooshop.modules.money_config.service.dto.WooshopMoneyConfigDto;


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
public class WooshopMoneyConfigServiceImpl extends BaseServiceImpl<WooshopMoneyConfigMapper, WooshopMoneyConfig> implements WooshopMoneyConfigService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopMoneyConfigDto> queryAll(WooshopMoneyConfigQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopMoneyConfig> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopMoneyConfigDto.class);
    }


    @Override
    public List<WooshopMoneyConfig> queryAll(WooshopMoneyConfigQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopMoneyConfig.class, criteria));
    }


    @Override
    public void download(List<WooshopMoneyConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopMoneyConfigDto wooshopMoneyConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("充值金额", wooshopMoneyConfig.getMoney());
            map.put("赠送金额", wooshopMoneyConfig.getGiveMoney());
            map.put("排序 数字越小越靠前", wooshopMoneyConfig.getSort());
            map.put("逻辑删除 1删除", wooshopMoneyConfig.getIsDel());
            map.put("状态:1显示 0不显示", wooshopMoneyConfig.getIsStart());
            map.put("创建时间", wooshopMoneyConfig.getCreateTime());
            map.put("更新时间", wooshopMoneyConfig.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
