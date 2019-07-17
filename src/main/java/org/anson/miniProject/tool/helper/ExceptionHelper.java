package org.anson.miniProject.tool.helper;

import cn.hutool.core.util.StrUtil;

public class ExceptionHelper {
    public static final String getMsg(Exception e){
        if (e == null){
            return null;
        }

        if (e.getCause() != null){
            String msg = e.getCause().getMessage();
            if (StrUtil.isNotEmpty(msg)){
                return msg;
            }
        }

        return e.getMessage();
    }
}
