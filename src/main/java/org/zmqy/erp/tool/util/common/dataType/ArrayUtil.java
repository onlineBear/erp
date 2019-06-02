package org.zmqy.erp.tool.util.common.dataType;

public class ArrayUtil {

    public static Boolean isEmpty(Object[] objArray){
        if(objArray == null || objArray.length <= 0){
            return true;
        }else{
            return false;
        }
    }

    public static Boolean isNotEmpty(Object[] objArray){
        return !ArrayUtil.isEmpty(objArray);
    }
}
