


package com.wooshop.modules.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.exception.BadRequestException;
import com.wooshop.modules.template.domain.WooshopFreightTemplateAssign;
import com.wooshop.modules.template.domain.WooshopFreightTemplatePinkage;
import com.wooshop.modules.template.service.WooshopFreightTemplateAssignService;
import com.wooshop.modules.template.service.WooshopFreightTemplatePinkageService;
import com.wooshop.modules.template.vo.WooshopFreightTemplateAssignVo;
import com.wooshop.modules.template.vo.WooshopFreightTemplatePinkageVo;
import com.wooshop.modules.template.vo.WooshopFreightTemplateVo;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.RedisUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.*;
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
import com.wooshop.modules.template.domain.WooshopFreightTemplate;
import org.springframework.stereotype.Service;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateQueryCriteria;
import com.wooshop.modules.template.service.mapper.WooshopFreightTemplateMapper;
import com.wooshop.modules.template.service.WooshopFreightTemplateService;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateDto;


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
@CacheConfig(cacheNames = "FreightTemp")
public class WooshopFreightTemplateServiceImpl extends BaseServiceImpl<WooshopFreightTemplateMapper, WooshopFreightTemplate> implements WooshopFreightTemplateService {

    private final IGenerator generator;
    private final WooshopFreightTemplateAssignService assignService;
    private final WooshopFreightTemplatePinkageService pinkageService;
    private final RedisUtils redisUtils;

    @Override
//    @Cacheable(key = "'FreightTemp:'+#criteria.id",condition = "#criteria.id!=null")
//    @Cacheable(key = "'FreightTemp:'+#p0.id")
    @Caching(cacheable= {@Cacheable(key = "'id:'+#criteria.id",condition = "#criteria.id!=null&&#criteria.ttype==null&&#criteria.name==null"),
            @Cacheable(key = "'ttype:'+#criteria.ttype",condition = "#criteria.ttype!=null"),
            @Cacheable(key = "'page:' +#pageable.getPageNumber()+'-'+ #pageable.getPageSize()"
                    ,condition="#criteria.id==null&&#criteria.ttype==null&&#criteria.name==null")})
    public PageResult<WooshopFreightTemplateVo> queryAll(WooshopFreightTemplateQueryCriteria criteria, Pageable pageable) {
//        System.out.println("criteria不是空"+ ObjectUtil.isNotEmpty(criteria));
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopFreightTemplate> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopFreightTemplateVo> templateVoPageResult = generator.convertPageInfo(page, WooshopFreightTemplateVo.class);
        for (int i = 0; i < templateVoPageResult.getContent().size(); i++) {
            Long id = templateVoPageResult.getContent().get(i).getId();
            //查询指定区域
           templateVoPageResult.getContent().get(i).setAssignData( assignService.selectByTempId(id));
            //查询免邮区域
            templateVoPageResult.getContent().get(i).setPinkageData(pinkageService.pinkageSeletctByTempId(id));
        }
        return templateVoPageResult;
    }


    @Override
    public List<WooshopFreightTemplate> queryAll(WooshopFreightTemplateQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopFreightTemplate.class, criteria));
    }


    @Override
    public void download(List<WooshopFreightTemplateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopFreightTemplateDto wooshopFreightTemplate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("运费模板名称", wooshopFreightTemplate.getName());
            map.put("运费计费类型：1按体积计费、2按重量计费、3按件数计费", wooshopFreightTemplate.getTtype());
            map.put("指定包邮项是否开启", wooshopFreightTemplate.getPinkage());
            map.put("顺序", wooshopFreightTemplate.getSort());
            map.put("逻辑删除。1表示删除", wooshopFreightTemplate.getIsDel());
            map.put("创建时间", wooshopFreightTemplate.getCreateTime());
            map.put("更新时间", wooshopFreightTemplate.getUpdateTime());
            map.put("创建人id", wooshopFreightTemplate.getUid());
            map.put("storeId",  wooshopFreightTemplate.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override

    @Caching(evict = {@CacheEvict(key = "'page:'+'*'",allEntries = true),@CacheEvict(key = "'ttype:'+#resources.ttype",allEntries = true)})
    public String saveAndUpdate(WooshopFreightTemplateVo resources) {
//        redisUtils.del(CacheKey.WOOSHOP_FREIGHTTEMP_PAGE + "*");


        if (resources.getId()==null){
            LambdaQueryWrapper<WooshopFreightTemplate> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
            objectLambdaQueryWrapper.eq(WooshopFreightTemplate::getName,resources.getName())
                    .eq(WooshopFreightTemplate::getIsDel,0);
            List<WooshopFreightTemplate> wooshopFreightTemplates = baseMapper.selectList(objectLambdaQueryWrapper);
            if (wooshopFreightTemplates.size()!=0){
               throw new BadRequestException("运费模板名称已经存在");
            }
        }else {
            //id不是空
            redisUtils.del(CacheKey.WOOSHOP_FREIGHTTEMP_ID + resources.getId());
        }
        WooshopFreightTemplate template = generator.convert(resources, WooshopFreightTemplate.class);
        saveOrUpdate(template);
        if (template.getId()!=null){
            pinkageService.pinkageRemoveByTempId(template.getId());//物理删除
            assignService.removeByTempId(template.getId());//物理删除
        }

        //保存指定区域
        for (WooshopFreightTemplateAssignVo assignVo:resources.getAssignData() ) {
            WooshopFreightTemplateAssign assign = generator.convert(assignVo, WooshopFreightTemplateAssign.class);
            String uuida = DigestUtils.md5Hex(assignVo.toString());
            assign.setArea(assignVo.getArea());
            assign.setPinkageUuid(uuida);
            assign.setTemplateId(template.getId());
            assign.setType(template.getTtype());
//            assign.setStart(template.getStart());
            assignService.addAndUpdate(assign);
        }
        //保存免邮区域
        if (resources.getPinkage()==1){//开启了免邮区域
            for (WooshopFreightTemplatePinkageVo pinkageVo:resources.getPinkageData()) {
                WooshopFreightTemplatePinkage pinkage = generator.convert(pinkageVo, WooshopFreightTemplatePinkage.class);
                String uuidp = DigestUtils.md5Hex(pinkageVo.toString());
                pinkage.setArea(pinkageVo.getArea());
                pinkage.setPinkageUuid(uuidp);
                pinkage.setTemplateId(template.getId());
                pinkage.setType(template.getTtype());
//                pinkage.setStart(template.getStart());
                pinkageService.addAndUpdate(pinkage);
            }
        }

       return "添加成功";
    }

    @Override
    @Caching(evict = {@CacheEvict(key = "'page:'+'*'",allEntries = true),@CacheEvict(key = "'ttype:'+'*'",allEntries = true)
            ,@CacheEvict(key = "'id:'+#id",allEntries = true)})
    public boolean deleteById(Long id) {
        int i = baseMapper.deleteById(id);
        return i>0;
    }

    @Override
    @Caching(cacheable= {@Cacheable(cacheNames = CacheKey.WOOSHOP_FREIGHTTEMP_ID,
            key = "#id",
            condition = "#id!=null")})
    public WooshopFreightTemplateVo findById(Long id) {

        WooshopFreightTemplate freightTemplate = baseMapper.selectById(id);
        WooshopFreightTemplateVo freightTemplateVo = generator.convert(freightTemplate, WooshopFreightTemplateVo.class);
        //查询指定区域
        freightTemplateVo.setAssignData( assignService.selectByTempId(id));
        //查询免邮区域
        freightTemplateVo.setPinkageData(pinkageService.pinkageSeletctByTempId(id));
        return freightTemplateVo;
    }

    /**
     * 新增 或者 更新
     * @param resources
     * @return
     */
//    @CacheEvict(key = "'id:'+#p0.id",allEntries = true)
    public WooshopFreightTemplate addAndUpdateFreightTemplate(WooshopFreightTemplate resources) {
        saveOrUpdate(resources);
        return resources;
    }

}
