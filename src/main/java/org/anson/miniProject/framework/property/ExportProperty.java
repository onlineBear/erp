package org.anson.miniProject.framework.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 导出配置
 */
@Component
public class ExportProperty {

    /**
     * 密钥
     */
    private static Long MAXTOTAL;

    public static Long getMaxTotal(){
        return ExportProperty.MAXTOTAL;
    }

    @Value("${comstom.export.maxTotal}")
    private void setMaxTotal(Long maxTotal) {
        ExportProperty.MAXTOTAL = maxTotal;
    }
}
