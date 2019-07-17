package org.anson.miniProject.core.model.po.account;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.core.model.po.BasePO;

import java.util.Date;

@Data
@TableName("user")
public class User extends BasePO {
    private String no;
    public static final String NO = "no";

    private String name;
    public static final String NAME = "name";

    private String password;
    public static final String PASSWORD = "password";

    private String headPortrait;
    public static final String HEADPORTRAIT = "headPortrait";

    private String email;
    public static final String EMAIL = "email";

    private String serveStatus;
    public static final String SERVESTATUS = "serveStatus";

    private Date registrationTime;
    public static final String REGISTRATIONTIME = "registrationTime";

    public static final String __TABLENAME = "user";

    @Override
    public String TABLENAME() {
        return __TABLENAME;
    }
}
