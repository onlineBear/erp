package org.anson.miniProject.tool.helper;

import cn.hutool.core.collection.CollUtil;
import com.esotericsoftware.reflectasm.FieldAccess;
import org.anson.miniProject.domain.base.BasePO;

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

    public static <T extends BasePO> StringBuilder checkRepeated(List<T> list, Class<T> clazz, String key){
        if (CollUtil.isEmpty(list)){
            return null;
        }

        List<Object> keyList = new ArrayList<>();
        FieldAccess fieldAccess = FieldAccess.get(clazz);
        for (T t : list){
            keyList.add(fieldAccess.get(t, key));
        }

        findRepeatedVal(keyList);

        return null;
    }
}
