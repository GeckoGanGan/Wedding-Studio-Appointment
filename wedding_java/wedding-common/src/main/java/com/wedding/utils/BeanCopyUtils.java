package com.wedding.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    //单个对象的拷贝
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性的拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //集合类型的Bean拷贝
    public static <V,O> List<V> cobyBeanList(List<O> sourceList,Class<V> clazz) {
        List<V> resultList = new ArrayList<>();
        try {
            for (Object o : sourceList) {
                V result = clazz.newInstance();
                BeanUtils.copyProperties(o, result);
                resultList.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
