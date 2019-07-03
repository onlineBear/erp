package org.anson.miniProject.model.entity.account;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.anson.miniProject.model.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:40
 * @Version 1.0
 **/
@Data
@TableName("user")
public class User extends BaseEntity {
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
}
