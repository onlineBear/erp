package org.zmqy.erp.tool.util.common;


import org.zmqy.erp.tool.util.common.dataType.DoubleUtil;
import org.zmqy.erp.tool.util.common.dataType.IntegerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @program: 字符串工具类
 * @description:
 * @author: lly
 * @create: 2018-07-17 10:14
 **/
public class StringUtil {

    // 首字母转换为大写
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getRandomID() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return str + rannum;// 当前时间
    }

    public static String getRandomIDBaseOnTime() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return str + rannum;// 当前时间
    }

    public static Boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static Boolean isNotEmpty(String str) {
       return !StringUtil.isEmpty(str);
    }

    public static String subString(String str, int beginIdx, int endIdx){
        if(str == null){
            return null;
        }

        int len = str.length();

        if(len < endIdx){
            return str.substring(beginIdx, len-1);
        }else {
            return str.substring(beginIdx, endIdx);
        }
    }

    public static String parseString(Object obj){
        if(obj == null){
            return null;
        }

        String strVal = null;

        if(obj instanceof String){
            strVal = (String)obj;
        }else if(obj instanceof Date){
            Date dateVal = (Date)obj;
            strVal = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(dateVal);
        }else if(obj instanceof Boolean){
            Boolean boolVal = (Boolean)obj;
            if(boolVal){
                strVal = "1";
            }else {
                strVal = "0";
            }
        }else if(obj instanceof Integer){
            Integer intVal = (Integer)obj;
            strVal = intVal.toString();
        }else if(obj instanceof Double){
            Double doubleVal = (Double)obj;
            strVal = doubleVal.toString();
        }else {
            throw new RuntimeException("数据类型不正确, 实际数据类型未知");
        }

        return strVal;
    }

    /**
     * 将字符串转换成布尔值
     * 1. 若是空字符串, 则返回 null
     * 2. 若是 TRUE 字符串(不区分大小写), 返回 true
     * 3. 若是 FALSE 字符串(不区分大小写), 返回 false
     * 4. 若是纯数字字符串, 等于0 返回 false, 非0 返回 true
     * @param strVal
     * @return
     */
    public static Boolean parseBoolean(String strVal){
        // null 空串
        if(StringUtil.isEmpty(strVal)){
            return null;
        }

        // 去空
        //strVal = strVal.trim();

        Boolean boolVal = null;

        if("TRUE".equals(strVal.toUpperCase())){
            boolVal = true;
        }else if("FALSE".equals(strVal.toUpperCase())){
            boolVal = false;
        }else{
            Integer intVal = null;
            try{
                intVal = Integer.parseInt(strVal);
            }catch (NumberFormatException e){
                Double doubleVal = null;
                try{
                    doubleVal = Double.parseDouble(strVal);
                }catch (NumberFormatException ne){
                    throw new RuntimeException("数据类型不正确, 实际数据类型/期待数据类型分别为 String(Double/int) / Boolean");
                }

                DoubleUtil.parseBoolean(doubleVal);
            }

            boolVal = IntegerUtil.parseBoolean(intVal);
        }

        return boolVal;
    }
}
