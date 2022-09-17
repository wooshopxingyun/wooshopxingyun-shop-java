/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wooshop.utils;

/**
 * @author: woo
 * @date: 2022/5/11 15:49
 * @apiNote: 关于缓存的Key集合
 */
public interface CacheKey {

    /**
     * 用户
     */
    String USER_ID = "user::id:";

    /**
     * 数据
     */
    String DATA_USER = "data::user:";
    /**
     * 菜单
     */
    String MENU_ID = "menu::id:";
    String MENU_PID = "menu::pid:";
    String MENU_USER = "menu::user:";
    /**
     * 角色授权
     */
    String ROLE_AUTH = "role::auth:";
    /**
     * 角色信息
     */
    String ROLE_ID = "role::id:";

    /**
     * 部门
     */
    String DEPT_ID = "dept::id:";
    String DEPT_PID = "dept::pid:";
    /**
     * 岗位
     */
    String JOB_ID = "job::id:";
    /**
     * 数据字典
     */
    String DICT_NAME = "dict::name:";
    String DICTDEAIL_DICTID = "dictDetail::dictId:";
    String DICT_ID = "dict::id:";
    String DICTDEAIL_DICTNAME = "dictDetail::name:";

    /**
     * 系统配置
     */
    String SYSCONFIG_MENU_NAME= "sysConfig::menu:name";
    String SYSCONFIG_MENU_UNIAPP_HOME= "sysConfig::menu:uniapphome";
    String SYSCONFIG_MENU_QUERY= "sysConfig::menu:query";
    String SYSCONFIG_MENU= "sysConfig::menu:";


    /**
     * 商城数据
     */
    String WOOSHOP_STORES_ID="wooshop::stores:id";//店铺数据
    String WOOSHOP_STORES_QUERY ="wooshop::stores:query";//店铺数据
    String WOOSHOP_CITYTREE="wooshop::CityTree:";//城市数据 树形结构
    /* ------------------------  运费 ----------------------------------*/
    String WOOSHOP_ASSIGNTEMP_ID="assignTemp::id:";//指定区域运费
    String WOOSHOP_PINKAGETEMP_ID="pinkageTemp::id:";//免邮区域列表
    String WOOSHOP_FREIGHTTEMP_ID ="FreightTemp::id:";//运费模板id
    String WOOSHOP_FREIGHTTEMP_TTYPE="FreightTemp::ttype:";//运费模板id
    String WOOSHOP_FREIGHTTEMP_PAGE="FreightTemp::page:";//运费模板
    /* ------------------------  商品 ----------------------------------*/
    String WOOSHOP_GOODS="goods::";//普通商品
    String WOOSHOP_GOODS_ID="goods::id";//普通商品id
    String WOOSHOP_GOODS_QUERY="goods::query";//普通商品查询条件缓存
    String WOOSHOP_SUK_ID="suk::id:";//普通商品suk Id
    String WOOSHOP_ATTRDETAILS_ID="AttrDetails::id:";//普通商品suk Id
    /* ------------------------  限时抢购商品 ----------------------------------*/
    String WOOSHOP_SECKILL="Seckill::";//
    String WOOSHOP_SECKILL_QUERY="Seckill::query";//条件查询
    String WOOSHOP_SECKILL_ID="Seckill::id";//限时抢购商品
    String WOOSHOP_SECKILL_ACTIVITY="Seckill::activity";//开启了 限时抢购商品
    /* ------------------------  砍价活动商品 ----------------------------------*/
    String WOOSHOP_BARGAIN="Bargain::";//
    String WOOSHOP_BARGAIN_QUERY="Bargain::query";//条件查询
    String WOOSHOP_BARGAIN_ID="Bargain::id";//砍价活动商品
    String WOOSHOP_BARGAIN_ACTIVITY="Bargain::activity";//开启了 砍价活动商品
    /* ------------------------  团购活动商品 ----------------------------------*/
    String WOOSHOP_BUYING="Buying::";//
    String WOOSHOP_BUYING_QUERY="Buying::query";//条件查询
    String WOOSHOP_BUYING_ID="Buying::id";//团购活动商品
    String WOOSHOP_BUYING_ACTIVITY="Buying::activity";//开启了 团购活动商品
    /* ------------------------  优惠券 ----------------------------------*/
    String WOOSHOP_COUPONS="Coupons::";//
    String WOOSHOP_COUPONS_QUERY="Coupons::query";//条件查询
    String WOOSHOP_COUPONS_ID="Coupons::id";//优惠券id
    String WOOSHOP_COUPONS_ACTIVITY="Coupons::activity";//开启了 团购活动商品
    /* ------------------------  优惠券领取详情CouponsInfo ----------------------------------*/
    String WOOSHOP_COUPONSINFO="CouponsInfo::";//
    String WOOSHOP_COUPONSINFO_QUERY="CouponsInfo::query";//条件查询
    String WOOSHOP_COUPONSINFO_UID="CouponsInfo::uid";//用户id查询
    String WOOSHOP_COUPONSINFO_COUPONSINFOID="CouponsInfo::couponsInfoId";//优惠券id查询

    /* ------------------------  分类 ----------------------------------*/
    String WOOSHOP_CATEGORY="wooshop::category:";//商城分类
    String WOOSHOP_CATEGORY_ID="wooshop::category:id";//商城分类

    /* ------------------------  商品规格 ----------------------------------*/
    String PRODUCT_SPECIFICATION="product::specification:";//商品规格

    /* ------------------------  计量单位 ----------------------------------*/
    String PRODUCTUNIT_UNIT="ProductUnit::Unit:";//商品规格

    /* ------------------------  优惠券 ----------------------------------*/
    String MARKET="ProductUnit::Unit:";//优惠券

    /* ------------------------  保障服务 ----------------------------------*/
    String WOOSHOP_PRODUCTPROMISE_ID="ProductPromise::ID:";//id
    String WOOSHOP_PRODUCTPROMISE_PROMISE="ProductPromise::Promise:";//条件查询

    /* ------------------------  商品收藏GoodsRelation ----------------------------------*/
    String WOOSHOP_RELATION="Relation::";//
    String WOOSHOP_RELATION_QUERY="Relation::query";//条件查询
    String WOOSHOP_RELATION_ID="Relation::id";//收藏表id
    String WOOSHOP_RELATION_NUMBER="Relation::number";//推荐人数
    String WOOSHOP_RELATION_NUMBER_SET="Relation::number::";//redis.set()/get() 推荐人数

    /* ------------------------  购物车ShoppingCart ----------------------------------*/
    String WOOSHOP_SHOPPINGCART="ShoppingCart::";//
    String WOOSHOP_SHOPPINGCART_QUERY="ShoppingCart::query";//条件查询
    String WOOSHOP_SHOPPINGCART_ID="ShoppingCart::id";//主键id
    String WOOSHOP_SHOPPINGCART_UID="ShoppingCart::uid";//主键id

    /* ------------------------  评价GoodsEvaluation ----------------------------------*/
    String WOOSHOP_GOODSEVALUATION="GoodsEvaluation::";//
    String WOOSHOP_GOODSEVALUATION_QUERY="GoodsEvaluation::query";//条件查询
    String WOOSHOP_GOODSEVALUATION_ID="GoodsEvaluation::id";//收藏表id

    /* ------------------------  会员地址Address ----------------------------------*/
    String WOOSHOP_ADDRESS="Address::";//
    String WOOSHOP_ADDRESS_QUERY="Address::query";//条件查询
    String WOOSHOP_ADDRESS_UID="Address::Uid";//用户id
    String WOOSHOP_ADDRESS_ID="Address::id";//主键iD



}
