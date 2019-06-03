package org.anson.miniProject.constract.enums;

import lombok.Getter;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-10 16:24
 */
public enum ExceptionMsgEnum {

    // 内部异常信息
    INTERNAL_ERR("出错了,请刷新重试"),

    // 导出数据超过限制(导出excel/pdf等)
    EXPORT_LIMIT("导出数据过大,请缩小范围"),

    // 重复
    REPEAT_VENDORNO("供应商编码重复"),

    //
    NOTEXISTS_USERNO("没有这个用户"),

    PSDNOTCORRECT("密码不正确");

    @Getter
    private String errMsg;

    private ExceptionMsgEnum(String errMsg) {
        this.errMsg = errMsg;
    }
}
