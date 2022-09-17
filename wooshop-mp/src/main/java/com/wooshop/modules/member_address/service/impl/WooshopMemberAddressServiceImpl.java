


package com.wooshop.modules.member_address.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import com.wooshop.modules.member_address.domain.WooshopMemberAddress;
import org.springframework.stereotype.Service;
import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressQueryCriteria;
import com.wooshop.modules.member_address.service.mapper.WooshopMemberAddressMapper;
import com.wooshop.modules.member_address.service.WooshopMemberAddressService;
import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressDto;


/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopMemberAddressServiceImpl extends BaseServiceImpl<WooshopMemberAddressMapper, WooshopMemberAddress> implements WooshopMemberAddressService {

    private final IGenerator generator;

    @Override
    @Caching(cacheable= {@Cacheable(cacheNames = CacheKey.WOOSHOP_ADDRESS_QUERY,
            key = "#criteria.uid+'-'+#criteria.id+'-'+#criteria.memberName+'-'+#criteria.memberMobile+'-'+#criteria.isDefault+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
    })
    public PageResult<WooshopMemberAddressDto> queryAll(WooshopMemberAddressQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopMemberAddress> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopMemberAddressDto.class);
    }


    @Override
    public List<WooshopMemberAddress> queryAll(WooshopMemberAddressQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopMemberAddress.class, criteria));
    }


    @Override
    public void download(List<WooshopMemberAddressDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopMemberAddressDto wooshopMemberAddress : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("会员id", wooshopMemberAddress.getUid());
            map.put("姓名", wooshopMemberAddress.getMemberName());
            map.put("电话", wooshopMemberAddress.getMemberMobile());
            map.put("所在省", wooshopMemberAddress.getProvinceName());
            map.put("所在市", wooshopMemberAddress.getCityName());
            map.put("城市id", wooshopMemberAddress.getCityId());
            map.put("所在区", wooshopMemberAddress.getDistrict());
            map.put("详细地址", wooshopMemberAddress.getDetailedAddress());
            map.put("邮编", wooshopMemberAddress.getPostCode());
            map.put("经度", wooshopMemberAddress.getLongitude());
            map.put("纬度", wooshopMemberAddress.getLatitude());
            map.put("是否为默认收货地址", wooshopMemberAddress.getIsDefault());
            map.put("是否删除", wooshopMemberAddress.getIsDel());
//            map.put("创建时间", wooshopMemberAddress.getCreateTime());
//            map.put("更新时间", wooshopMemberAddress.getUpdateTime());
            map.put("排序", wooshopMemberAddress.getSort());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    @CacheRemove(value = CacheKey.WOOSHOP_ADDRESS, key = "*")
    public PageResult<WooshopMemberAddressDto> addAndUpdate(WooshopMemberAddress resources) {
        /**if (resources.getId()==null){
         save(resources);
         }else {
         updateById(resources);
         }**/
        if (resources.getIsDefault()==1){
            isNotDefault(resources.getUid());
        }
        saveOrUpdate(resources);
        List<WooshopMemberAddress> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopMemberAddress> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopMemberAddressDto.class);
    }

    @Override
    public void isNotDefault(Long uid) {
        LambdaUpdateChainWrapper<WooshopMemberAddress> userMapper=new LambdaUpdateChainWrapper<>(baseMapper);
        userMapper.eq(WooshopMemberAddress::getUid,uid).set(WooshopMemberAddress::getIsDefault,0);
        userMapper.update();
    }

}
