
package com.wooshop.modules.member_address.rest;

import com.wooshop.aspect.CacheRemove;
import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressQueryCriteria;
import java.util.Arrays;

import com.wooshop.utils.CacheKey;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.member_address.domain.WooshopMemberAddress;
import com.wooshop.modules.member_address.service.WooshopMemberAddressService;


import com.wooshop.modules.member_address.service.dto.WooshopMemberAddressDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;




/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员收货地址管理")
@RestController
@RequestMapping("/api/wooshopMemberAddress")
public class WooshopMemberAddressController {

    private final WooshopMemberAddressService wooshopMemberAddressService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopMemberAddress:list')")
    public void download(HttpServletResponse response, WooshopMemberAddressQueryCriteria criteria) throws IOException {
        wooshopMemberAddressService.download(generator.convert(wooshopMemberAddressService.queryAll(criteria), WooshopMemberAddressDto.class), response);
    }

    @GetMapping
    @Log("查询会员收货地址")
    @ApiOperation("查询会员收货地址")
    @PreAuthorize("@el.check('admin','wooshopMemberAddress:list')")
    public ResponseEntity<PageResult<WooshopMemberAddressDto>> getWooshopMemberAddresss(WooshopMemberAddressQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopMemberAddressService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员收货地址")
    @ApiOperation("新增会员收货地址")
    @PreAuthorize("@el.check('admin','wooshopMemberAddress:list')")
    @CacheRemove(value = CacheKey.WOOSHOP_ADDRESS, key = "*")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopMemberAddress resources){
        return new ResponseEntity<>(wooshopMemberAddressService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员收货地址")
    @ApiOperation("修改会员收货地址")
    @PreAuthorize("@el.check('admin','wooshopMemberAddress:list')")
    @CacheRemove(value = CacheKey.WOOSHOP_ADDRESS, key = "*")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopMemberAddress resources){
        wooshopMemberAddressService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员收货地址")
    @ApiOperation("删除会员收货地址")
    @PreAuthorize("@el.check('admin','wooshopMemberAddress:del')")
    @DeleteMapping
    @CacheRemove(value = CacheKey.WOOSHOP_ADDRESS, key = "*")
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopMemberAddressService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
