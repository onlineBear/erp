package org.anson.miniProject.tool.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

public class InputParamHelper {
    /**
     * 必填检查
     */
    public static void required(Object[] valArray, String[] errArray){
        if(ArrayUtil.isEmpty(valArray) || ArrayUtil.isEmpty(errArray)){
            return;
        }

        if(valArray.length != errArray.length){
            throw new RuntimeException("valArray 数组 和 errArray 数组长度不一致");
        }

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<valArray.length;i++){
            Object obj = valArray[i];
            String errMsg = errArray[i];
            if (InputParamHelper.isBlank(obj)) {
                if(sb.length() > 0)
                    sb.append(";").append(errMsg);
                else
                    sb.append(errMsg);
            }
        }

        if(sb.length() > 0){
            throw new RuntimeException(sb.toString());
        }
    }

    public static Boolean isBlank(Object obj){
        if (obj == null){
            return true;
        }

        if (obj instanceof String) {
            String objStr = (String) obj;
            if (StrUtil.isBlank(objStr)) {
                return true;
            }
        }

        return false;
    }

    public static void nullToEmpty(String... str){
        if (ArrayUtil.isEmpty(str)) {
            return;
        }

        for (int i=0;i<str.length;i++) {
            str[i] = StrUtil.nullToEmpty(str[i]);
        }
    }
}
