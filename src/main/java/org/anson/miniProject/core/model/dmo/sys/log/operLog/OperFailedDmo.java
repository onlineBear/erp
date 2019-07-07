package org.anson.miniProject.core.model.dmo.sys.log.operLog;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class OperFailedDmo {
    private String operMenuId;

    private String operTypeKey;
    private String clientKey;

    private String description;
    private String failReason;
    private String pk;
    private String mainTableName;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier dmoToOperLogCopier = BeanCopier.create(OperFailedDmo.class, OperLog.class, false);

    public static OperLog toOperLog(OperFailedDmo dmo){
        if(dmo == null){
            return null;
        }

        OperLog po = new OperLog();

        dmoToOperLogCopier.copy(dmo, po, null);

        return po;
    }
}
