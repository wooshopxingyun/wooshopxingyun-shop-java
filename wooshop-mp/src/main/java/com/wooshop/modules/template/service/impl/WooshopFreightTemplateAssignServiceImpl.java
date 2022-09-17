


package com.wooshop.modules.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.template.vo.WooshopFreightTemplateAssignVo;
import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.wooshop.modules.template.domain.WooshopFreightTemplateAssign;
import org.springframework.stereotype.Service;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignQueryCriteria;
import com.wooshop.modules.template.service.mapper.WooshopFreightTemplateAssignMapper;
import com.wooshop.modules.template.service.WooshopFreightTemplateAssignService;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignDto;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "assignTemp")
public class WooshopFreightTemplateAssignServiceImpl extends BaseServiceImpl<WooshopFreightTemplateAssignMapper, WooshopFreightTemplateAssign> implements WooshopFreightTemplateAssignService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopFreightTemplateAssignDto> queryAll(WooshopFreightTemplateAssignQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopFreightTemplateAssign> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopFreightTemplateAssignDto.class);
    }


    @Override
    public List<WooshopFreightTemplateAssign> queryAll(WooshopFreightTemplateAssignQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopFreightTemplateAssign.class, criteria));
    }


    @Override
    public void download(List<WooshopFreightTemplateAssignDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopFreightTemplateAssignDto wooshopFreightTemplateAssign : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("模板ID", wooshopFreightTemplateAssign.getTemplateId());
            map.put("城市ID", wooshopFreightTemplateAssign.getCityId());
            map.put("城市id和城市表父类id", wooshopFreightTemplateAssign.getArea());
            map.put("首件商品", wooshopFreightTemplateAssign.getFirstPart());
            map.put("首件运费", wooshopFreightTemplateAssign.getFirstMoney());
            map.put("续件", wooshopFreightTemplateAssign.getRenewal());
            map.put("续件运费", wooshopFreightTemplateAssign.getRenewalMoney());
            map.put("运费计费类型：1按体积计费、2按重量计费、3按件数计费", wooshopFreightTemplateAssign.getType());
            map.put("分组唯一值", wooshopFreightTemplateAssign.getPinkageUuid());
            map.put("是否生效", wooshopFreightTemplateAssign.getIsStart());
            map.put("创建时间", wooshopFreightTemplateAssign.getCreateTime());
            map.put("更新时间", wooshopFreightTemplateAssign.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @CacheEvict(key = "'id:' + #tempId")
    public void removeByTempId(Long tempId) {
        baseMapper.delByTempId(tempId);
    }


    /**
     * 查找指定区域数据
     * @param tempId  模板id
     * @return
     */
    @Override
    @Cacheable(key = "'id:' + #tempId")
    public List<WooshopFreightTemplateAssignVo> selectByTempId(Long tempId) {
        LambdaQueryWrapper<WooshopFreightTemplateAssign> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
        objectLambdaQueryWrapper.eq(WooshopFreightTemplateAssign::getTemplateId,tempId);
        List<WooshopFreightTemplateAssign> wooshopFreightTemplateAssigns = baseMapper.selectList(objectLambdaQueryWrapper);
        List<WooshopFreightTemplateAssignVo> assignVoList = generator.convert(wooshopFreightTemplateAssigns, WooshopFreightTemplateAssignVo.class);
        return assignVoList;
    }

    @Override
//    @CachePut(key = "'id:' + #resources.id")
    public WooshopFreightTemplateAssignVo addAndUpdate(WooshopFreightTemplateAssign resources) {
        saveOrUpdate(resources);
        return generator.convert(resources,WooshopFreightTemplateAssignVo.class);
    }
}
