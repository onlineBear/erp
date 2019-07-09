package org.anson.miniProject.core.model.dmo.sys.log.operLog;

import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
@Builder
public class OperFailedDmo {
    private String operMenuId;

    private String operTypeKey;
    private ClientEnum clientKey;

    private String description;
    private String failReason;
    private String pk;
    private String mainTableName;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier dmoToOperLogCopier = BeanCopier.create(OperFailedDmo.class, OperLog.class, false);

    public static OperLog toOperLog(OperFailedDmo dmo) throws InstantiationException, IllegalAccessException {
        OperLog po = BeanHelper.beanToBean(dmo, OperLog.class, dmoToOperLogCopier);

        if (dmo.getClientKey() != null){
            po.setClientKey(dmo.getClientKey().getKey());
        }

        return po;
    }
}
