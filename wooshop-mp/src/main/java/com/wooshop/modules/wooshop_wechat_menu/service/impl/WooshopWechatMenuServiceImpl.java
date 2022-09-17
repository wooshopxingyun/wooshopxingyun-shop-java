


package com.wooshop.modules.wooshop_wechat_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.wooshop.modules.wooshop_wechat_menu.domain.WooshopWechatMenu;
import org.springframework.stereotype.Service;
import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuQueryCriteria;
import com.wooshop.modules.wooshop_wechat_menu.service.mapper.WooshopWechatMenuMapper;
import com.wooshop.modules.wooshop_wechat_menu.service.WooshopWechatMenuService;
import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuDto;


/**
* @author woo
* @date 2022-07-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopWechatMenuServiceImpl extends BaseServiceImpl<WooshopWechatMenuMapper, WooshopWechatMenu> implements WooshopWechatMenuService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopWechatMenuDto> queryAll(WooshopWechatMenuQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopWechatMenu> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopWechatMenuDto.class);
    }


    @Override
    public List<WooshopWechatMenu> queryAll(WooshopWechatMenuQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopWechatMenu.class, criteria));
    }


    @Override
    public void download(List<WooshopWechatMenuDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopWechatMenuDto wooshopWechatMenu : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("缓存数据", wooshopWechatMenu.getValueResult());
            map.put("缓存时间", wooshopWechatMenu.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopWechatMenuDto> addAndUpdate(WooshopWechatMenu resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopWechatMenu> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopWechatMenu> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopWechatMenuDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(String id) {
        removeById(id);
    }


    /**
     * 查询是否存在
     * @param wechatMenus 名称
     * @return
     */
    @Override
    public Boolean isExist(String wechatMenus) {
        WooshopWechatMenu wooshopWechatMenu = this.getOne(new LambdaQueryWrapper<WooshopWechatMenu>()
                .eq(WooshopWechatMenu::getNameKey,wechatMenus));
        if(wooshopWechatMenu == null){
            return false;
        }
        return true;
    }
}
