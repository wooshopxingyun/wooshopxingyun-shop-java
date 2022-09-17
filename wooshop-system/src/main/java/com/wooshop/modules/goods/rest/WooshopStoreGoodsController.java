
package com.wooshop.modules.goods.rest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsAttrDetails;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsSuk;
import com.wooshop.modules.goods.service.WooshopStoreGoodsAttrDetailsService;
import com.wooshop.modules.goods.service.WooshopStoreGoodsSukService;
import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsQueryCriteria;

import java.util.Arrays;

import com.wooshop.utils.CacheKey;
import com.wooshop.utils.RedisUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.goods.domain.WooshopStoreGoods;
import com.wooshop.modules.goods.service.WooshopStoreGoodsService;


import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsDto;
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
* @date 2021-11-30
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "商品信息管理")
@RestController
@RequestMapping("/api/wooshopStoreGoods")
public class WooshopStoreGoodsController {

    private final WooshopStoreGoodsService wooshopStoreGoodsService;
    private final IGenerator generator;
    private final WooshopStoreGoodsSukService wooshopStoreGoodsSukService;
    private final WooshopStoreGoodsAttrDetailsService goodsAttrDetailsService;
    private final RedisUtils redisUtils;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreGoods:list')")
    public void download(HttpServletResponse response, WooshopStoreGoodsQueryCriteria criteria) throws IOException {
        wooshopStoreGoodsService.download(generator.convert(wooshopStoreGoodsService.queryAll(criteria), WooshopStoreGoodsDto.class), response);
    }

    @GetMapping
    @Log("查询商品信息")
    @ApiOperation("查询商品信息")
    @PreAuthorize("@el.check('admin','wooshopStoreGoods:list')")
    public ResponseEntity<PageResult<WooshopStoreGoodsDto>> getWooshopStoreGoodss(WooshopStoreGoodsQueryCriteria criteria, Pageable pageable){
        switch (criteria.getGoodsStartType()){
            case 0://全部
                if (criteria.getId()!=null){
                    //根据商品id查询 忽略逻辑删除
                }else {
                 criteria.setIsDel(0);
                }
                break;
            case 1://出售中
                criteria.setIsStart(1);
                break;
            case 2://下架
                criteria.setIsStart(0);
                criteria.setIsDel(0);
                break;
            case 3://售罄
                criteria.setQuantity(0);
                break;
            case 4://回收站
                criteria.setIsDel(1);
                break;
        }
        PageResult<WooshopStoreGoodsDto> wooshopStoreGoodsDtoPageResult = wooshopStoreGoodsService.queryAll(criteria, pageable);
        return new ResponseEntity<>(wooshopStoreGoodsDtoPageResult,HttpStatus.OK);
    }

    @PostMapping
    @Log("新增修改商品信息")
    @ApiOperation("新增修改商品信息")
    @PreAuthorize("@el.check('admin','wooshopStoreGoods:list')")
    @CacheRemove(value = CacheKey.WOOSHOP_GOODS,key = "*")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreGoods resources){
        if (resources.getId()!=null){//通过id判断是否新品
            wooshopStoreGoodsSukService.delByGoodsAndType(resources.getId(), resources.getActivityType());
            goodsAttrDetailsService.delByGoodsAndType(resources.getId(),resources.getActivityType());//删除
        }
        boolean b1 = wooshopStoreGoodsService.saveOrUpdate(resources);
        resources.getSpecTypeListData().forEach(item->{
            item.setGoodsId(resources.getId());
        });
        JSONArray jsonArray=new JSONArray(resources.getSpecTypeListDataOrig());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WooshopStoreGoodsSuk convertGoodsSuk = generator.convert(jsonObject, WooshopStoreGoodsSuk.class);
            convertGoodsSuk.setGoodsId(resources.getId());
            convertGoodsSuk.setSpecificationValue(jsonArray.getJSONObject(i).getStr("specification_value"));
            wooshopStoreGoodsSukService.saveOrUpdate(convertGoodsSuk);
            jsonArray.getJSONObject(i).set("id",convertGoodsSuk.getId());
            jsonArray.getJSONObject(i).set("goodsId",resources.getId());
        }
        WooshopStoreGoodsAttrDetails goodsAttrDetails=new WooshopStoreGoodsAttrDetails();
        goodsAttrDetails.setGoodsId(resources.getId());
        goodsAttrDetails.setActivityType(resources.getActivityType());
        goodsAttrDetails.setAttrText(jsonArray.toString());//规格不带商品属性
        goodsAttrDetails.setSprcificationparlams(jsonArray.toString());//规格带商品属性
        goodsAttrDetails.setSpecorig(resources.getSprcificationParlams());

        boolean b = goodsAttrDetailsService.saveOrUpdate(goodsAttrDetails);
        System.out.println(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商品信息")//修改也是在新增方法
    @ApiOperation("修改商品信息")
    @PreAuthorize("@el.check('admin','wooshopStoreGoods:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreGoods resources){
        wooshopStoreGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品信息")
    @ApiOperation("删除商品信息")
    @PreAuthorize("@el.check('admin','wooshopStoreGoods:del')")
    @CacheRemove(value = CacheKey.WOOSHOP_GOODS,key = "*")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            WooshopStoreGoods byId = wooshopStoreGoodsService.getById(id);

            if (byId.getIsDel()==1){
                wooshopStoreGoodsService.delByGoodsId(byId,0);
                wooshopStoreGoodsSukService.delByGoodsAndType(byId.getId(),0);
                goodsAttrDetailsService.delByGoodsAndType(byId.getId(),0);
            }else {
                byId.setIsDel(1);
                byId.setIsStart(0);
                wooshopStoreGoodsService.saveOrUpdate(byId);
            }
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
