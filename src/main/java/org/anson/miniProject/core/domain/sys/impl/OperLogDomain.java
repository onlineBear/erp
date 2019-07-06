package org.anson.miniProject.core.domain.sys.impl;

import org.anson.miniProject.core.domain.sys.IOperLogDomain;
import org.anson.miniProject.core.model.dmo.sys.OperLogBo;
import org.anson.miniProject.core.repository.sys.OperLogRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class OperLogDomain implements IOperLogDomain {
    @Autowired
    private OperLogRep rep;

    @Override
    public String beginLogin(OperLogBo bo, String operUserId, Date operTime) {
        // 必填检查
        Object[] valArray = {bo.getClientKey(), bo.getIpv4()};
        String[] errArray = {"请输入操作用户id", "请选择操作类型key", "请选择客户端key",
                "请输入是否操作成功"};
        InputParamHelper.required(valArray, errArray);

        bo.setOperTypeKey("登录");
        return null;
    }
}
