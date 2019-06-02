package org.zmqy.erp.model.mis.flow;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tFlowDtl")
public class FlowDtl {
    private String id;

    private String flowId;
    public static final String FLOWID = "flowId";

    private Integer seq;
    public static final String SEQ = "seq";

    private Boolean areLastLevel;
    public static final String ARELASTLEVEL = "areLastLevel";

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;


}
