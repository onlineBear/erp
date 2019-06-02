package org.zmqy.erp.model.mis.flow;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tFlow")
public class Flow {
    private String id;

    private String upFlowId;

    private Integer minLevel;

    private String menuId;
    public static final String MENUID = "menuId";

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;



}
