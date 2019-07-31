package org.anson.miniProject.tool.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.reflectasm.FieldAccess;
import org.anson.miniProject.domain.base.BasePO;
import org.anson.miniProject.tool.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class POHelper {
    private static final String defaultSeparator = ",";

    public static <T extends BasePO> String getRepStr(List<T> list, Class<T> clazz, String key, String separator){
        if (CollUtil.isEmpty(list)){
            return null;
        }

        List<Object> keyList = new ArrayList<>();
        FieldAccess fieldAccess = FieldAccess.get(clazz);
        for (T t : list){
            keyList.add(fieldAccess.get(t, key));
        }

        Set<Object> repVal = CollectionUtil.findRepeatedVal(keyList);

        if (CollUtil.isEmpty(repVal)){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Object one : repVal){
            sb.append(one.toString()).append(StrUtil.isBlank(separator)?defaultSeparator:separator);
        }

        return sb.toString();
    }
}
