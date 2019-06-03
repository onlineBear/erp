package org.anson.miniProject.framework.property;

import org.springframework.beans.factory.annotation.Value;

/**
 * 分页属性配置
 */
public class PageProperty {
    /**
     * 默认分页大小
     */
    private static int DEFAULT_PAGE_SIZE;

    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    @Value("${comstom.page.defaultPageSize}")
    private void setDefaultPageSize(int defaultPageSize) {
        DEFAULT_PAGE_SIZE = defaultPageSize;
    }
}
