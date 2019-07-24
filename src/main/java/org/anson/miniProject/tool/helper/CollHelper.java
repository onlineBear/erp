package org.anson.miniProject.tool.helper;

import cn.hutool.core.collection.CollUtil;

import java.util.*;

public class CollHelper {
    public static <T> List<T> findRepeatedVal(Collection<T> c){
        if (CollUtil.isEmpty(c)){
            return null;
        }

        Iterator<T> it = c.iterator();
        List<T> repeatedVal = new ArrayList<>();

        while(it.hasNext()){
            T next = it.next();
            if (Collections.frequency(c, next) > 1){
                repeatedVal.add(next);
            }
        }

        return repeatedVal;
    }
}
