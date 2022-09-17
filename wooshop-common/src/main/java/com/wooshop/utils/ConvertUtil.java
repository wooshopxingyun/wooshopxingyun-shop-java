package com.wooshop.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wooshop.base.PageInfo;
import org.apache.commons.beanutils.PropertyUtils;


import javax.xml.transform.ErrorListener;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 转换
 * Created by jinjin on 2020-09-22.
 */
public class ConvertUtil {
    public static <T, S> T convert(final S s, Class<T> clz) {
        return s == null ? null : BeanUtil.copyProperties(s, clz);
    }

    public static <T, S> List<T> convertList(List<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> BeanUtil.copyProperties(vs, clz)).collect(Collectors.toList());
    }

    public static <T, S> Set<T> convertSet(Set<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> BeanUtil.copyProperties(vs, clz)).collect(Collectors.toSet());
    }

    public static <T, S> PageInfo<T> convertPage(IPage<S> page, Class<T> clz) {
        if (page == null) {
            return null;
        }
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setTotalElements(page.getTotal());
        pageInfo.setContent(convertList(page.getRecords(), clz));
        return pageInfo;
    }

    /**
     * 对象复制
     * @param s
     * @param clz
     * @param <T>
     * @param <S>
     * @return
     */
 /*   public static <T, S> T pConvert(final S s, Class<T> clz) {
        try{
            PropertyUtils.copyProperties(clz, s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s == null ? null : (T) clz;
    }

    public static <T, S> List<T> pConvertList(List<S> s, Class<T> clz) {
        s.stream().map(vs ->{
                try{
            PropertyUtils.copyProperties(clz, vs);
                }catch (Exception e){
                    e.printStackTrace();
                }
                    return vs;
                }
        ).collect(Collectors.toList());
        return s == null ? null : List<clz> clz;
    }

    public static <T, S> Set<T> pConvertSet(Set<S> s, Class<T> clz) {

        s.stream().map(vs -> {
                    try{
                        PropertyUtils.copyProperties(clz, vs);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return vs;
        }).collect(Collectors.toSet());
        return s == null ? null : (Set<T>) s;
    }*/

}
