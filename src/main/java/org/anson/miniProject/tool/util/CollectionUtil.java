package org.anson.miniProject.tool.util;

import java.util.*;

public class CollectionUtil {
    public static <T> Set<T> findRepeatedVal(Collection<T> c){
        if (cn.hutool.core.collection.CollUtil.isEmpty(c)){
            return null;
        }

        Iterator<T> it = c.iterator();
        Set<T> repeatedVal = new HashSet<>();

        while(it.hasNext()){
            T next = it.next();
            if (Collections.frequency(c, next) > 1){
                repeatedVal.add(next);
            }
        }

        return repeatedVal;
    }
}
