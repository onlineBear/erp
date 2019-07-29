package org.anson.miniProject.domain.account.user.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.domain.base.BasePO;

import java.util.Date;

@Data
@TableName("user")
class User extends BasePO {
    private String no;
    private String name;
    private String password;
    private String headPortrait;
    private String email;
    private String serveStatus;
    private Date registrationTime;

    private static final String __TABLENAME = "user";

    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String HEADPORTRAIT = "headPortrait";
    public static final String EMAIL = "email";
    public static final String SERVESTATUS = "serveStatus";
    public static final String REGISTRATIONTIME = "registrationTime";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
