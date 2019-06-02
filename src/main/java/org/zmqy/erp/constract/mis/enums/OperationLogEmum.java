package org.zmqy.erp.constract.mis.enums;

/**
 * 日志操作类型
 */
public enum OperationLogEmum {

    /**
     * 新增
     */
    OPERTYPE_ADD("operType-add"),

    /**
     * 修改
     */
    OPERTYPE_MDF("operType-mdf"),

    /**
     * 删除
     */
    OPERTYPE_DEL("operType-del"),

    /**
     * 审核
     */
    OPERTYPE_CHECK("operType-check"),

    /**
     * 审核回退
     */
    OPERTYPE_ANTICHECK("operType-antiCheck");

    private String OperationLogEmum;

    private OperationLogEmum(String OperationLogEmum) {
        this.OperationLogEmum = OperationLogEmum;
    }

    public String getOperationLogEmum() {
        return this.OperationLogEmum;
    }
}
