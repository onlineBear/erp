package org.anson.miniProject.tool.helper;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class BeanHelper {

    public static <T> T mapToBo(Map map, String[] keyArray, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (ArrayUtil.isEmpty(keyArray) || MapUtil.isEmpty(map)) {
            return null;
        }

        T t = clazz.newInstance();

        BeanMap beanMap = BeanMap.create(t);

        for (String key : keyArray) {
            beanMap.put(key, map.get(key));
        }

        return t;
    }

    public static <T> T beanToBean(Object source, Class<T> clazz, BeanCopier beanCopier) throws IllegalAccessException, InstantiationException {
        if (source == null){
            return null;
        }

        T target = clazz.newInstance();

        beanCopier.copy(source, target, null);

        return target;
    }

    public static <T, S> List<T> beansToBeans(List<S> sourceList, Class<T> clazz, BeanCopier beanCopier) throws InstantiationException, IllegalAccessException {
        if (IterUtil.isEmpty(sourceList)){
            return null;
        }

        List<T> targetList = new ArrayList<>();

        for (Object source : sourceList){
            targetList.add(BeanHelper.beanToBean(source, clazz, beanCopier));
        }

        return targetList;
    }
}
