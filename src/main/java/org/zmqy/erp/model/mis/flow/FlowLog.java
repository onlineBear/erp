package org.zmqy.erp.model.mis.flow;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tFlowLog")
public class FlowLog {
    private String id;

    private String userId;

    private String ip;

    private Date checkTime;

    private String operNo;

    private String flowId;

    private String billId;

    private Integer level;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}
