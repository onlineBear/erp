package org.anson.miniProject.tool.util.common.dataType;

import org.anson.miniProject.tool.util.common.StringUtil;

public class IntegerUtil {

    /**
     * 将一个基本数据类型(含包装类型)转换成 Integer 类型
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj){
        if(obj == null){
            return null;
        }

        Integer intVal = null;

        if(obj instanceof Integer){
            intVal = (Integer) obj;
        }else if(obj instanceof String){
            String strVal = (String)obj;

            // null 空串
            if(StringUtil.isEmpty(strVal)){
                return null;
            }

            // 去空
            //strVal = strVal.trim();

            try{
                intVal = Integer.parseInt(strVal);
            }catch (NumberFormatException e){
                throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 String/Integer");
            }
        }else if(obj instanceof Boolean){
            Boolean boolVal = (Boolean)obj;
            intVal = BooleanUtil.paresInteger(boolVal);
        }else if(obj instanceof Double){
            Double doubleVal = (Double)obj;
            intVal = doubleVal.intValue();

            if(doubleVal.compareTo(intVal.doubleValue()) != 0){
                throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 Double/Integer");
            }

        }else {
            throw new RuntimeException("实际数据类型未知");
        }

        return intVal;
    }

    /**
     * 将 Integer 类型转成成 Boolean 类型
     * @param val
     * @return
     */
    public static Boolean parseBoolean(Integer val){
        if(val == null){
            return null;
        }

        if(val == 0){
            return false;
        }else {
            return true;
        }
    }
}
