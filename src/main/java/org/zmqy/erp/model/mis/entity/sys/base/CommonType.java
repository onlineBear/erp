package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tCommonType")
public class CommonType {
    private String id;

    private String comTypeNo;
    public static final String COMTYPENO = "comTypeNo";

    private Boolean canAdd;

    private Boolean canMdf;

    private Boolean canDel;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}