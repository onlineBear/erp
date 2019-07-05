package org.anson.miniProject.tool.helper;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

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
}
