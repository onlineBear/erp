package org.anson.miniProject.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BasePO {
    /**
     * 主键id
     */
    @TableField
    private String id;

    /**
     * 创建用户id
     */
    private String createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 主键id
     */
    public static final String ID = "id";

    /**
     * 创建用户id
     */
    public static final String CREATEUSERID = "createUserId";
    /**
     * 创建时间
     */
    public static final String CREATETIME = "createTime";
    /**
     * 最后更新时间
     */
    public static final String LASTUPDATETIME = "lastUpdateTime";

    public abstract String TABLENAME();
}
