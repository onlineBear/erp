package org.zmqy.erp.model.mis.flow;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tFlowDtlLang")
public class FlowDtlLang {
    private String id;

    private String langId;
    public static final String LANGID = "langId";

    private String flowDtlId;
    public static final String FLOWDTLID = "flowDtlId";

    private String flowDtlName;

    private String flowDesc;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;


}