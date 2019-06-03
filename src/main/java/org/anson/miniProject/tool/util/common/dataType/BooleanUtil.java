package org.anson.miniProject.tool.util.common.dataType;

import org.anson.miniProject.tool.util.common.StringUtil;

import java.util.Date;

public class BooleanUtil {

    /**
     * 将一个基本数据类型(含包装类型)转换成布尔值
     * 若是空字符串, 则返回 null
     * @param obj
     * @return
     */
    public static Boolean parseBoolean(Object obj){
        if(obj == null){
            return null;
        }

        Boolean boolVal = null;

        if(obj instanceof Boolean){
            boolVal = (Boolean)obj;
        }else if(obj instanceof Date){
            throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 Date/Boolean");
        }else if(obj instanceof String){
            String strVal = (String)obj;
            boolVal = StringUtil.parseBoolean(strVal);
        }else if(obj instanceof Integer){
            Integer intVal = (Integer)obj;
            boolVal = IntegerUtil.parseBoolean(intVal);
        }else if(obj instanceof Double){
            Double doubleVal = (Double)obj;
            boolVal = DoubleUtil.parseBoolean(doubleVal);
        }else {
            throw new RuntimeException("数据类型不正确, 实际数据类型未知");
        }

        return boolVal;
    }

    /**
     * 将 Boolean 类型 转换成 Double 类型
     * @param boolVal
     * @return
     */
    public static Double parseDouble(Boolean boolVal){
        if(boolVal == null){
            return null;
        }

        if(boolVal){
            return 1.0;
        }else {
            return 0.0;
        }
    }

    /**
     * 将 Boolean 类型 转换成 Integer 类型
     * @param boolVal
     * @return
     */
    public static Integer paresInteger(Boolean boolVal){
        if(boolVal == null){
            return null;
        }

        if(boolVal){
            return 1;
        }else {
            return 0;
        }
    }
}
