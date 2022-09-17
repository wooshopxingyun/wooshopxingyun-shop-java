


package com.wooshop.modules.wechat_reply.service.impl;

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
import com.wooshop.modules.wechat_reply.domain.WooshopWechatReply;
import org.springframework.stereotype.Service;
import com.wooshop.modules.wechat_reply.service.dto.WooshopWechatReplyQueryCriteria;
import com.wooshop.modules.wechat_reply.service.mapper.WooshopWechatReplyMapper;
import com.wooshop.modules.wechat_reply.service.WooshopWechatReplyService;
import com.wooshop.modules.wechat_reply.service.dto.WooshopWechatReplyDto;


/**
* @author woo
* @date 2022-02-23
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopWechatReplyServiceImpl extends BaseServiceImpl<WooshopWechatReplyMapper, WooshopWechatReply> implements WooshopWechatReplyService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopWechatReplyDto> queryAll(WooshopWechatReplyQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopWechatReply> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopWechatReplyDto.class);
    }


    @Override
    public List<WooshopWechatReply> queryAll(WooshopWechatReplyQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopWechatReply.class, criteria));
    }


    @Override
    public void download(List<WooshopWechatReplyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopWechatReplyDto wooshopWechatReply : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关键字", wooshopWechatReply.getReplyKey());
            map.put("回复类型", wooshopWechatReply.getReplyType());
            map.put("回复数据", wooshopWechatReply.getReplyData());
            map.put("0=不可用  1 =可用", wooshopWechatReply.getIsStatus());
            map.put("是否隐藏", wooshopWechatReply.getReplyHide());
            map.put("逻辑删除:1删除", wooshopWechatReply.getIsDel());
            map.put(" createTime",  wooshopWechatReply.getCreateTime());
            map.put(" updateTime",  wooshopWechatReply.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopWechatReplyDto> addAndUpdate(WooshopWechatReply resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopWechatReply> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopWechatReply> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopWechatReplyDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

    @Override
    public WooshopWechatReply queryKey(String key) {
//        getOne(new LambdaQueryChainWrapper<>(baseMapper).eq(WooshopWechatReply::getReplyKey,key));
        LambdaQueryChainWrapper<WooshopWechatReply> eq = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(WooshopWechatReply::getReplyKey, key);

        return eq.one();
    }
}
