
package com.wooshop.modules.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wooshop.modules.service.BaseService;
import com.wooshop.common.web.OrderQueryParam;
import com.wooshop.common.web.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author woo
 * @since 2021.10.24
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected Page setPageParam(QueryParam queryParam) {
        return setPageParam(queryParam,null);
    }

    protected Page setPageParam(QueryParam queryParam, OrderItem defaultOrder) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(queryParam.getPage());
        // 设置页大小
        page.setSize(queryParam.getLimit());
        /**
         * 如果是queryParam是OrderQueryParam，并且不为空，则使用前端排序
         * 否则使用默认排序
         */
        if (queryParam instanceof OrderQueryParam){
            OrderQueryParam orderQueryParam = (OrderQueryParam) queryParam;
            List<OrderItem> orderItems = orderQueryParam.getOrders();
            if (CollectionUtil.isEmpty(orderItems)){
                page.setOrders(Arrays.asList(defaultOrder));
            }else{
                page.setOrders(orderItems);
            }
        }else{
            page.setOrders(Arrays.asList(defaultOrder));
        }

        return page;
    }

    protected Page getPageParam(Integer pages,Integer limit) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(pages);
        // 设置页大小
        page.setSize(limit);
        return page;
    }

    protected void getPage(Pageable pageable) {
        String order=null;
        if(pageable.getSort()!=null){
            order= pageable.getSort().toString();
            order=order.replace(":","");
            if("UNSORTED".equals(order)){
                order="id desc";
            }
        }
        PageHelper.startPage(pageable.getPageNumber()+1, pageable.getPageSize(),order);
    }

    /**
     *  自定义分页
     * @param page 页数
     * @param limit 数量
     * @param order 排序  order="id desc";
     */
    protected void setPageAndOrder(int page, int limit, String order) {
        if("UNSORTED".equals(order)){
            PageHelper.startPage(page+1, limit);
//            order="id desc";
        }else {
            order=order.replace(":","");
            PageHelper.startPage(page+1, limit,order);
        }
    }

    /**
     *  自定义分页 没有排序
     * @param page 页数
     * @param limit 数量
     *
     */
    protected void setPageNotOrder(int page, int limit) {
        PageHelper.startPage(page+1, limit);
    }

}
