package org.anson.miniProject.constract.enums;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-10 16:24
 */
public enum LoginClientNoEnum {

    /**
     * pc端
     */
    LOGINCLIENT_PC("loginClient-pc"),
    /**
     * 移动端
     */
    LOGINCLIENT_MOBILE("loginClient-mobile");

    private String code;

    private LoginClientNoEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
