package org.anson.miniProject.tool.util.common.dataType;

import java.util.Map;

public class MapUtil {
    public static Boolean isEmpty(Map map){
        if(map == null || map.size() < 1){
            return true;
        }

        return false;
    }

    public static Boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }
}
