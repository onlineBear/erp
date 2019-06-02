package org.zmqy.erp.framework.JasperReports;

import lombok.Data;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-20 11:31
 */
@Data
public class PrintPageConfig {
    private int width;
    private int height;
    private String url;

    public PrintPageConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public PrintPageConfig setHeight(int height) {
        this.height = height;
        return this;
    }
}
