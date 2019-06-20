package org.anson.miniProject.model.entity.account;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:40
 * @Version 1.0
 **/
@Data
public class User {
    private String id;
    public static String ID = "id";

    private String no;
    public static String NO = "no";

    private String name;
    public static String NAME = "name";

    private String password;
    public static String PASSWORD = "password";

    private String headPortrait;
    public static String HEADPORTRAIT = "headPortrait";

    private String createUserId;
    public static String CREATEUSERID = "createUserId";

    private Date createTime;
    public static String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static String LASTUPDATETIME = "lastUpdateTime";
}
