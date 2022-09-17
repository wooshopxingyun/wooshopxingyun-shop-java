package com.wooshop.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 抽象实体类：无公共字段
 *
 * @author fanglei
 * @date 2021/07/28 15:26
 **/
public abstract class CommonModel<T extends Model<?>> extends Model<T> {

}
