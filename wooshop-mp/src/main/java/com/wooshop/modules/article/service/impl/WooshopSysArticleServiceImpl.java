


package com.wooshop.modules.article.service.impl;

import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.category.domain.WooshopConfigCategory;
import com.wooshop.modules.category.service.WooshopConfigCategoryService;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryQueryCriteria;
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
import com.wooshop.modules.article.domain.WooshopSysArticle;
import org.springframework.stereotype.Service;
import com.wooshop.modules.article.service.dto.WooshopSysArticleQueryCriteria;
import com.wooshop.modules.article.service.mapper.WooshopSysArticleMapper;
import com.wooshop.modules.article.service.WooshopSysArticleService;
import com.wooshop.modules.article.service.dto.WooshopSysArticleDto;


/**
* @author woo
* @date 2021-12-19
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopSysArticleServiceImpl extends BaseServiceImpl<WooshopSysArticleMapper, WooshopSysArticle> implements WooshopSysArticleService {

    private final IGenerator generator;
    private final WooshopConfigCategoryService configCategoryService;

    @Override
    public PageResult<WooshopSysArticleDto> queryAll(WooshopSysArticleQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopSysArticle> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopSysArticleDto> wooshopSysArticleDtoPageResult = generator.convertPageInfo(page, WooshopSysArticleDto.class);
        WooshopConfigCategoryQueryCriteria configCategoryQueryCriteria=new WooshopConfigCategoryQueryCriteria();
        configCategoryQueryCriteria.setType(3);
        List<WooshopConfigCategory> wooshopConfigCategories = configCategoryService.queryAll(configCategoryQueryCriteria);
        wooshopSysArticleDtoPageResult.getContent().forEach(item->{
                wooshopConfigCategories.forEach(itemList->{
                    if (itemList.getId().equals(item.getCategoryId().intValue())){
                        item.setCategoryName(itemList.getName());
                    }
                });

        });

        return wooshopSysArticleDtoPageResult;
    }


    @Override
    public List<WooshopSysArticle> queryAll(WooshopSysArticleQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopSysArticle.class, criteria));
    }


    @Override
    public void download(List<WooshopSysArticleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopSysArticleDto wooshopSysArticle : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类id", wooshopSysArticle.getCategoryId());
            map.put("文章标题", wooshopSysArticle.getArticleTitel());
            map.put("文章作者", wooshopSysArticle.getArticleAuthor());
            map.put("文章图片", wooshopSysArticle.getCoverImage());
            map.put("文章简介", wooshopSysArticle.getSynopsis());
            map.put("文章分享标题", wooshopSysArticle.getShareTitle());
            map.put("文章分享简介", wooshopSysArticle.getShareSynopsis());
            map.put("浏览次数", wooshopSysArticle.getVisitCount());
            map.put("排序", wooshopSysArticle.getSort());
            map.put("原文链接", wooshopSysArticle.getUrl());
            map.put("微信素材id", wooshopSysArticle.getMediaId());
            map.put("状态 1启用 0关闭", wooshopSysArticle.getIsStart());
            map.put("是否隐藏 0否", wooshopSysArticle.getHide());
            map.put("管理员id", wooshopSysArticle.getAdminId());
            map.put("商户id", wooshopSysArticle.getMerId());
            map.put("商品关联id", wooshopSysArticle.getGoodsId());
            map.put("是否热门(小程序)", wooshopSysArticle.getIsHot());
            map.put("是否轮播图(小程序)", wooshopSysArticle.getIsBanner());
            map.put("文章内容", wooshopSysArticle.getArticleContent());
            map.put("创建时间", wooshopSysArticle.getCreateTime());
            map.put("更新时间", wooshopSysArticle.getUpdateTime());
            map.put("逻辑删除 1表示删除", wooshopSysArticle.getIsDel());
            map.put("点赞数", wooshopSysArticle.getLikeNum());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
