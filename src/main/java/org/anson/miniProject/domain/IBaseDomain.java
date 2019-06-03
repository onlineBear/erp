package org.anson.miniProject.domain;

import java.util.Date;

public interface IBaseDomain<T> {
    /**
     * 新增
     * @param bo
     * @param operUserId 操作人
     * @param operTime 操作时间
     * @return
     * @throws Exception
     */
    public String add(T bo, String operUserId, Date operTime) throws Exception;

    /**
     * 根据主键id修改
     * @param bo
     * @param operUserId 操作人
     * @param operTime 操作时间
     * @throws Exception
     */
    public void mdfById(T bo, String operUserId, Date operTime) throws Exception;
}
