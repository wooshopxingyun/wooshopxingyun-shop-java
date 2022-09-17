


package com.wooshop.modules.sys_attachment.service.impl;

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
import com.wooshop.modules.sys_attachment.domain.WooshopSystemAttachment;
import org.springframework.stereotype.Service;
import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentQueryCriteria;
import com.wooshop.modules.sys_attachment.service.mapper.WooshopSystemAttachmentMapper;
import com.wooshop.modules.sys_attachment.service.WooshopSystemAttachmentService;
import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentDto;


/**
* @author woo
* @date 2022-06-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopSystemAttachmentServiceImpl extends BaseServiceImpl<WooshopSystemAttachmentMapper, WooshopSystemAttachment> implements WooshopSystemAttachmentService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopSystemAttachmentDto> queryAll(WooshopSystemAttachmentQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopSystemAttachment> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopSystemAttachmentDto.class);
    }


    @Override
    public List<WooshopSystemAttachment> queryAll(WooshopSystemAttachmentQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopSystemAttachment.class, criteria));
    }


    @Override
    public void download(List<WooshopSystemAttachmentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopSystemAttachmentDto wooshopSystemAttachment : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("附件名称", wooshopSystemAttachment.getAttName());
            map.put("附件路径", wooshopSystemAttachment.getAttDir());
            map.put("压缩图片路径", wooshopSystemAttachment.getSattDir());
            map.put("附件大小", wooshopSystemAttachment.getAttSize());
            map.put("附件类型", wooshopSystemAttachment.getAttType());
            map.put("分类ID0编辑器,1产品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图", wooshopSystemAttachment.getAttPid());
            map.put("图片上传类型 1本地 2七牛云 3OSS 4COS ", wooshopSystemAttachment.getImageType());
            map.put("图片上传模块类型 1 后台上传 2 用户生成", wooshopSystemAttachment.getModuleType());
            map.put("用户id", wooshopSystemAttachment.getUid());
            map.put("邀请码", wooshopSystemAttachment.getInviteCode());
            map.put(" createTime",  wooshopSystemAttachment.getCreateTime());
            map.put(" updateTime",  wooshopSystemAttachment.getUpdateTime());
            map.put(" isDel",  wooshopSystemAttachment.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopSystemAttachmentDto> addAndUpdate(WooshopSystemAttachment resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopSystemAttachment> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopSystemAttachment> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopSystemAttachmentDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

    /**
     * 通过附件名称 查询数据
     * @param attName 附件名称
     * @return
     */
    @Override
    public WooshopSystemAttachment findByName(String attName) {
        LambdaQueryChainWrapper<WooshopSystemAttachment> queryWrapper=new LambdaQueryChainWrapper<>(baseMapper);
        queryWrapper.eq(WooshopSystemAttachment::getAttName, attName);
        return queryWrapper.one();
    }

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     */
    @Override
    public void attachmentAdd(String name, String attSize, String attDir, String sattDir) {
        WooshopSystemAttachment attachment =  WooshopSystemAttachment.builder()
                .attName(name)
                .attSize(attSize)
                .attDir(attDir)
                .attType("image/jpeg")
                .sattDir(sattDir)
                .build();
        baseMapper.insert(attachment);
    }

    /**
     * 删除海报数据
     * @param id 主键id
     * @return
     */
    @Override
    public int delById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     * @param uid 用户id
     * @param code 邀请码
     */
    @Override
    public void newAttachmentAdd(String name, String attSize, String attDir, String sattDir, Long uid, String code) {

        WooshopSystemAttachment attachment =  WooshopSystemAttachment.builder()
                .attName(name)
                .attSize(attSize)
                .attDir(attDir)
                .attType("image/jpeg")
                .sattDir(sattDir)
                .uid(uid)
                .inviteCode(code)
                .build();
        baseMapper.insert(attachment);
    }
}
