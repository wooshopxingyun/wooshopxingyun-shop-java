


package com.wooshop.modules.wechat_template.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
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
import com.wooshop.utils.RedisUtils;
import com.wooshop.modules.wechat_template.domain.WooshopWechatTemplate;
import org.springframework.stereotype.Service;
import com.wooshop.modules.wechat_template.service.dto.WooshopWechatTemplateQueryCriteria;
import com.wooshop.modules.wechat_template.service.mapper.WooshopWechatTemplateMapper;
import com.wooshop.modules.wechat_template.service.WooshopWechatTemplateService;
import com.wooshop.modules.wechat_template.service.dto.WooshopWechatTemplateDto;


/**
* @author woo
* @date 2022-02-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopWechatTemplateServiceImpl extends BaseServiceImpl<WooshopWechatTemplateMapper, WooshopWechatTemplate> implements WooshopWechatTemplateService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopWechatTemplateDto> queryAll(WooshopWechatTemplateQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopWechatTemplate> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopWechatTemplateDto.class);
    }


    @Override
    public List<WooshopWechatTemplate> queryAll(WooshopWechatTemplateQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopWechatTemplate.class, criteria));
    }


    @Override
    public void download(List<WooshopWechatTemplateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopWechatTemplateDto wooshopWechatTemplate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("模板编号", wooshopWechatTemplate.getTempCode());
            map.put("模板名称", wooshopWechatTemplate.getTempName());
            map.put("回复内容", wooshopWechatTemplate.getTempContent());
            map.put("模板ID", wooshopWechatTemplate.getTempId());
            map.put("添加时间", wooshopWechatTemplate.getCreateTime());
            map.put("更新时间", wooshopWechatTemplate.getUpdateTime());
            map.put("状态:0不启用/1启用", wooshopWechatTemplate.getIsStatus());
            map.put(" isDel",  wooshopWechatTemplate.getIsDel());
            map.put("类型：temp:模板消息 sub:订阅消息", wooshopWechatTemplate.getTempType());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopWechatTemplateDto> addAndUpdate(WooshopWechatTemplate resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopWechatTemplate> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopWechatTemplate> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopWechatTemplateDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

}
