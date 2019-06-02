package org.zmqy.erp.tool.util.common.dataType;

import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;

public class DoubleUtil {

    /**
     * 将一个基本数据类型(含包装类型)转换成 Double 类型
     * @param obj
     * @return
     */
    public static Double parseDouble(Object obj){

        if(obj == null){
            return null;
        }

        Double doubleVal = null;

        if(obj instanceof Double){
            doubleVal = (Double)obj;
        }else if(obj instanceof String){
            String strVal = (String)obj;

            // null 空串
            if(StringUtil.isEmpty(strVal)){
                return null;
            }

            // 去空
            //strVal = strVal.trim();

            try{
                doubleVal = Double.parseDouble(strVal);
            }catch (NumberFormatException e){
                throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 String/Double");
            }
        }else if(obj instanceof Date){
            throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 Date/Double");
        }else if(obj instanceof Boolean){
            Boolean boolVal = (Boolean)obj;
            doubleVal = BooleanUtil.parseDouble(boolVal);
        }else if(obj instanceof Integer){
            Integer intVal = (Integer)obj;
            doubleVal = intVal.doubleValue();
        }else {
            throw new RuntimeException("实际数据类型未知");
        }

        return doubleVal;
    }

    /**
     * 将 Double 类型转成成 Boolean 类型
     * @param val
     * @return
     */
    public static Boolean parseBoolean(Double val){
        if(val == null){
            return null;
        }

        if(val.compareTo(0.0) == 0){
            return false;
        }else {
            return true;
        }
    }
}
