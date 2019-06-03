package org.anson.miniProject.tool.util.common.dataType;

import org.anson.miniProject.tool.util.common.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static String dateFormatPattern_1 = "yyyy-MM-dd";
    private static String dateFormatPattern_2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将一个基本数据类型(含包装类型)转换成 Date 类型
     * @param obj
     * @return
     */
    public static Date parseDate(Object obj){
        if(obj == null){
            return null;
        }

        Date dateVal = null;

        if(obj instanceof Date){
            dateVal = (Date)obj;
        }else if(obj instanceof String){
            String strVal = (String)obj;

            // 空串
            if(StringUtil.isEmpty(strVal)){
                return null;
            }

            // 去空
            strVal = strVal.trim();

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern_1);
            try{
                dateVal = sdf.parse(strVal);
            }catch (ParseException e_1){
                sdf = new SimpleDateFormat(dateFormatPattern_2);
                try{
                    dateVal = sdf.parse(strVal);
                }catch (ParseException e_2){
                    throw new RuntimeException("日期格式不正确, 应为 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss");
                }
            }
        }else if(obj instanceof Boolean || obj instanceof Integer || obj instanceof Double){
            throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 Boolean(Integer/Double) / Date");
        }else {
            throw new RuntimeException("数据类型未知");
        }

        return dateVal;
    }
}
