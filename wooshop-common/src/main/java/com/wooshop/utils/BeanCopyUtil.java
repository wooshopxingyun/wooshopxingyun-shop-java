package com.wooshop.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Bean复制工具类
 *
 * @author fanglei
 * @date 2021/08/09
 **/
public class BeanCopyUtil {
    public static <T> T copy(final Object source, final Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (InstantiationException e) {
            throw new RuntimeException("copy error", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("copy error", e);
        }
    }

    public static <E, V> List<V> copyListToList(List<E> source, Class<V> clazz) {
        return source.stream().map(pojo -> copy(pojo, clazz)).collect(toList());
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
