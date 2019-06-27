package org.anson.miniProject.tool.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

public class InputParamHelper {
    /**
     * 必填检查
     */
    public static void required(String[] valArray, String[] errArray){
        if(ArrayUtil.isEmpty(valArray) || ArrayUtil.isEmpty(errArray)){
            return;
        }

        if(valArray.length != errArray.length){
            throw new RuntimeException("valArray 数组 和 errArray 数组长度不一致");
        }

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<valArray.length;i++){
            if(StrUtil.isEmpty(valArray[i])){
                if(sb.length() > 0)
                    sb.append(";").append(errArray[i]);
                else
                    sb.append(errArray[i]);
            }
        }

        if(sb.length() > 0){
            throw new RuntimeException(sb.toString());
        }
    }
}
