package org.anson.miniProject.model.entity.account;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user")
public class User {
    @TableId("id")
    private String id;
    public static String ID = "id";

    @TableField("no")
    private String no;
    public static String NO = "no";

    @TableField("name")
    private String name;
    public static String NAME = "name";

    private String password;
    public static String PASSWORD = "password";

    private String headPortrait;
    public static String HEADPORTRAIT = "headPortrait";

    private String email;
    public static String EMAIL = "email";

    private String serveStatus;
    public static String SERVESTATUS = "serveStatus";

    private String createUserId;
    public static String CREATEUSERID = "createUserId";

    private Date createTime;
    public static String CREATETIME = "createTime";

    private Date lastUpdateTime;
    public static String LASTUPDATETIME = "lastUpdateTime";
}
