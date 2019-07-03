package org.anson.miniProject.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseEntity {
    @TableField
    private String id;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    public static String __TABLENAME = null;
    public static final String ID = "id";
    public static final String CREATEUSERID = "createUserId";
    public static final String CREATETIME = "createTime";
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
