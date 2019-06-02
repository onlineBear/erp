package org.zmqy.erp.tool.helper.zmqy.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Slf4j
public class Permition {

    private static final String pre = "/pc/module/";

    /**
     * 检查 审核 权限
     * @param menuNo
     * @param nextLevel
     */
    public static void checkReviewPermition(String menuNo, Integer nextLevel){
        StringBuffer sb = new StringBuffer(pre).append(menuNo).append("/").append("ck").append(nextLevel);

        Subject currentUser = SecurityUtils.getSubject();

        if(!currentUser.isPermitted(sb.toString())){
            throw new RuntimeException("没有权限访问");
        }
    }

    public static void checkBackPermition(String menuNo, Integer preLevel){
        StringBuffer sb = new StringBuffer(pre).append(menuNo).append("/").append("unCk").append(preLevel);

        Subject currentUser = SecurityUtils.getSubject();

        if(!currentUser.isPermitted(sb.toString())){
            throw new RuntimeException("没有权限访问");
        }
    }
}
