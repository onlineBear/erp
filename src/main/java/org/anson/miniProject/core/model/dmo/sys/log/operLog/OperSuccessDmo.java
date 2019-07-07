package org.anson.miniProject.core.model.dmo.sys.log.operLog;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class OperSuccessDmo {
    private String operMenuId;

    private String operTypeKey;
    private String clientKey;

    private String description;
    private String pk;
    private String mainTableName;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier dmoToPoCopier = BeanCopier.create(OperSuccessDmo.class, OperLog.class, false);

    public static OperLog toOperLog(OperSuccessDmo dmo){
        if(dmo == null){
            return null;
        }

        OperLog po = new OperLog();

        dmoToPoCopier.copy(dmo, po, null);

        return po;
    }
}
