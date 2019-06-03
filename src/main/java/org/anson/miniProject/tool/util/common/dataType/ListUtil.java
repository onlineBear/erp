package org.anson.miniProject.tool.util.common.dataType;

import java.util.List;

public class ListUtil {
    public static Boolean isEmpty(List list){
        if(list == null || list.isEmpty()){
            return true;
        }

        return false;
    }

    public static Boolean isNotEmpty(List list){
        return !isEmpty(list);
    }
}
