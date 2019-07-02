package org.anson.miniProject.model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dataDictionaryType")
public class DictType {
    @TableField
    private String id;
    private String no;
    private String name;
    private String description;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    public static final String ID = "id";
    public static final String NO = "no";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CREATEUSERID = "createUserId";
    public static final String CREATETIME = "createTime";
    public static final String LASTUPDATETIME = "lastUpdateTime";
}
