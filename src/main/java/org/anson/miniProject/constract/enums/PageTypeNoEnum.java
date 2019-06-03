package org.anson.miniProject.constract.enums;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-10 16:24
 */
public enum PageTypeNoEnum {

    /**
     * 子页面
     */
    PAGETYPE_CHILD("pageType-child"),
    /**
     * 父页面
     */
    PAGETYPE_PARENT("pageType-parent");

    private String code;

    private PageTypeNoEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
