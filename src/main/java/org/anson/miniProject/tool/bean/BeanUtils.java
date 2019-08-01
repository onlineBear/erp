package org.anson.miniProject.tool.bean;

import cn.hutool.core.collection.CollUtil;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtils {
    private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    public static <T> T beanToBean(Object source, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        // 取 beanCopier
        if (!beanCopierMap.containsKey(getBeanCopierName(source, clazz))){
            putBeanCopier(source, clazz);
        }

        BeanCopier beanCopier = beanCopierMap.get(getBeanCopierName(source, clazz));

        T target = clazz.newInstance();

        beanCopier.copy(source, target, null);

        return target;
    }

    public static <T> List<T> beansToBeans(List sourceList, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        if (CollUtil.isEmpty(sourceList)){
            return null;
        }

        List<T> targetList = new ArrayList<>();

        for (Object source : sourceList){
            targetList.add(beanToBean(source, clazz));
        }

        return targetList;
    }

    private static String getBeanCopierName(Object source, Class clazz){
        return source.getClass() + "-" + clazz.getName();
    }

    private static void putBeanCopier(Object source, Class clazz){
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), clazz, false);
        beanCopierMap.put(getBeanCopierName(source, clazz), beanCopier);
    }
}
