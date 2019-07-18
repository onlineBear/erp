package org.anson.miniProject.core.model.param.sys.log.operLog;

import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
@Builder
public class OperFailedParam {
    private String operMenuId;

    // private String operTypeKey;
    private ClientEnum clientKey;

    private String description;
    private String failReason;
    private String pk;
    private String mainTableName;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier toBOCopier = BeanCopier.create(OperFailedParam.class, OperLog.class, false);

    public OperLog toBO() throws InstantiationException, IllegalAccessException {
        OperLog po = BeanHelper.beanToBean(this, OperLog.class, toBOCopier);

        if (this.clientKey != null){
            po.setClientKey(this.clientKey.getKey());
        }

        return po;
    }
}
