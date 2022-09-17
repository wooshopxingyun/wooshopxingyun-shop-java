package com.wooshop.base.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wooshop.base.CommonService;


/**
* 公共抽象service实现类
* @author fanglei
* @date 2021/07/28
*/
public abstract class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CommonService<T> {

}
