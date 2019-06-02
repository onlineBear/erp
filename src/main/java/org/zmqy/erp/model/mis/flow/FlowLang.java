package org.zmqy.erp.model.mis.flow;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tFlowLang")
public class FlowLang {
    private String id;

    private String flowId;
    public static final String FLOWID = "flowId";

    private String langId;
    public static final String LANGID = "langId";
    private String flowName;

    private String description;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;


}