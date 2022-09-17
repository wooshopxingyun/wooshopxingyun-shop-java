


package com.wooshop.modules.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.template.vo.WooshopFreightTemplatePinkageVo;
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
import com.wooshop.modules.template.domain.WooshopFreightTemplatePinkage;
import org.springframework.stereotype.Service;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageQueryCriteria;
import com.wooshop.modules.template.service.mapper.WooshopFreightTemplatePinkageMapper;
import com.wooshop.modules.template.service.WooshopFreightTemplatePinkageService;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageDto;


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
@CacheConfig(cacheNames = "pinkageTemp")
public class WooshopFreightTemplatePinkageServiceImpl extends BaseServiceImpl<WooshopFreightTemplatePinkageMapper, WooshopFreightTemplatePinkage> implements WooshopFreightTemplatePinkageService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopFreightTemplatePinkageDto> queryAll(WooshopFreightTemplatePinkageQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopFreightTemplatePinkage> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopFreightTemplatePinkageDto.class);
    }


    @Override
    public List<WooshopFreightTemplatePinkage> queryAll(WooshopFreightTemplatePinkageQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopFreightTemplatePinkage.class, criteria));
    }


    @Override
    public void download(List<WooshopFreightTemplatePinkageDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopFreightTemplatePinkageDto wooshopFreightTemplatePinkage : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("模板ID", wooshopFreightTemplatePinkage.getTemplateId());
            map.put("城市ID", wooshopFreightTemplatePinkage.getCityId());
            map.put("城市id和城市表父类id", wooshopFreightTemplatePinkage.getArea());
            map.put("包邮件数", wooshopFreightTemplatePinkage.getNumber());
            map.put("包邮金额", wooshopFreightTemplatePinkage.getPrice());
            map.put("运费计费类型：1按体积计费、2按重量计费、3按件数计费", wooshopFreightTemplatePinkage.getType());
            map.put("免运费分组唯一值", wooshopFreightTemplatePinkage.getPinkageUuid());
            map.put("是否开启", wooshopFreightTemplatePinkage.getIsStart());
            map.put("创建时间", wooshopFreightTemplatePinkage.getCreateTime());
            map.put("更新时间", wooshopFreightTemplatePinkage.getUpdateTime());
            map.put("物理删除 1表示删除", wooshopFreightTemplatePinkage.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 物理删除
     * @param tempId
     */
    @Override
    @CacheEvict(key = "'id:' + #tempId",allEntries = true)
    public void pinkageRemoveByTempId(Long tempId) {
        baseMapper.delByTempId(tempId);
    }

    /**
     * 查找免邮数据
     * @param tempId 模板id
     * @return
     */
    @Override
    @Cacheable(key = "'id:'+#tempId")
    public List<WooshopFreightTemplatePinkageVo> pinkageSeletctByTempId(Long tempId) {
        LambdaQueryWrapper<WooshopFreightTemplatePinkage> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(WooshopFreightTemplatePinkage::getTemplateId,tempId);
        List<WooshopFreightTemplatePinkage> templatePinkage = baseMapper.selectList(lambdaQueryWrapper);
        List<WooshopFreightTemplatePinkageVo> pinkageVoList = generator.convert(templatePinkage, WooshopFreightTemplatePinkageVo.class);
        return pinkageVoList;
    }

    @Override
//    @CachePut(key = "'id:' + #resources.id")
    public WooshopFreightTemplatePinkageVo addAndUpdate(WooshopFreightTemplatePinkage resources) {
        saveOrUpdate(resources);
        return generator.convert(resources,WooshopFreightTemplatePinkageVo.class);
    }
}
